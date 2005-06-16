/* gtdmanager/gui/DiagramView
 *
 * {{{ package / imports */

package gtdmanager.gui;

import gtdmanager.core.*;

import java.util.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;
import org.freehep.graphics2d.VectorGraphics;

/* }}} */
/**
 * <p>Title: DiagramView class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ DiagramView */
public class DiagramView extends JComponent implements View {

    // this is the default gap between componentborder and the drawings
    // (and the gap between two drawings (ie. activitybars))
    private static int padding = 16;

    // this is the default width of an activitybar
    private static int barWidth = 32;

    // diagramview offset in days since epoche
    // (the value is the 3rd may 2005 and only for testing purposes)
    private int startDate = 12935;
    private int endDate = 12975;

    // geometry of the diagramgrid and spefwidth
    private int gridX, gridY, gridW, gridH, gridStep;

    // font metrics
    private int advance, ascent; //, descent;

    // offset of the bars (x is for padding only and y is for vertical
    // positioning); initialize with padding before use!
    private int yoffset = 0; //, xoffset = 0;

    // this is the project to draw
    private JProject project = null;

    DiagramView() {
        super();
    }

    public void update(JProject project) {
        // as we "update" this component every paint() there is no explicit
        // update at this point, just keep care that we are darwing the
        // correct project and do an explicit repaint in order to show changes
        // instantly..
        this.project = project;
        repaint();
    }

    public void paint(Graphics g) {
        if (project == null) return;

        // if project is set, get the last instance here. there should be
        // really a method in the core for this..
        ArrayList a = project.getInstances();
        JInstance i = (JInstance)a.get(a.size() - 1);
        if (i == null) return;

        // TODO: we have to paint in (at least) 3 phases:
        //  1. paint frame/grid and dates (at the bottom)
        //  2. paint the activities
        //  3. paint the dependencies
        //
        // for now we only paint the activities here

        advance = g.getFontMetrics().getMaxAdvance();
        ascent = g.getFontMetrics().getMaxAscent();
        //descent = g.getFontMetrics().getMaxDescent();

        // init geometry
        gridX = -1; //padding;
        gridY = padding;
        gridW = getWidth() + 1; //- 2 * padding;
        gridH = getHeight() - 2 * padding;
        gridStep = gridW / (endDate - startDate);

        // set minimal width to ensure readability
        if (gridStep < advance/2) gridStep = advance/2;

//        g.drawString("geometry: " + gri + "x" + h + "+" + x + "+" + y, x, y);
       // g.drawString(startDate + " to " + endDate, gridX, gridY);

        paintGrid(g);

        yoffset = 0 ;//padding;
        paintActivities(g, i.getActivities());
    }

    private void paintGrid(Graphics g) {
        g.setColor(Color.gray);
        g.drawRect(gridX, gridY, gridW, gridH);
        
        for (int i = 0; i < gridW; i += gridStep) {
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis((startDate + i/gridStep)*24L*60L*60L*1000L);

            // sunday red spans from the sunday line to the monday line
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                g.setColor(Color.red);

            g.drawLine(gridX + i, gridY, gridX + i, gridY + gridH);

            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
            {
                g.setColor(Color.gray); // reset sunday red

                String str = ""
                    + (cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "")
                    + cal.get(Calendar.DAY_OF_MONTH)
                    + (cal.get(Calendar.MONTH) < 10 ? ".0" : ".")
                    + cal.get(Calendar.MONTH)
                    + "."
                    + cal.get(Calendar.YEAR);

                for (int j = 0; j < str.length(); j++)
                    g.drawString(str.substring(j, j + 1),
                            gridX + i + 2,
                            gridY + gridH + (j + 1 - 11) * ascent);
            }
        }

    }

    private void paintActivities(Graphics g, ArrayList a) {
        // iterate through all activities.
        // this method is meant to be called recursive
        // TODO: the core needs: getAllActivities(PREORDER|POSTORDER|INORDER)
        ListIterator i = a.listIterator();
        while (i.hasNext()) paintActivity(g, (JActivity)i.next());
    }
    
    private void paintActivity(Graphics g, JActivity a) {
        if (a == null) return; // nothing to do

        // our granularity is 1 day, offset is start of epoch (in UTC)
        int start = (int)(a.getStartDate().getTimeInMillis()/1000L/60L/60L/24L);
        int end = (int)(a.getEndDate().getTimeInMillis()/1000L/60L/60L/24L);
        
        // calculate bargeometry for activity
        // length of the bar:
        int w = gridStep * (end - start);
        int h = barWidth;                   // width of this bar
        int x = gridX + (start - startDate) * gridStep;
        int y = gridY + yoffset + h;
        yoffset += h + padding;

        // clamp bars
        //if (y + h > gridY + gridH) h -= 1;

        // draw the bar
        g.setColor(Color.black);
        g.fillRect(x, y, w, h);

        // ...and the text
        g.setColor(Color.white);
        g.drawString(""
            + "[" + a.getId() + "] "
            + a.getShortName()
            + " (" + (end - start) + " days)"
            , x + padding/2
            , y + h/2
        );

        ListIterator i = a.getDependencies().listIterator();
        int j = 0;
        while (i.hasNext()) {
            JDependency dep = (JDependency)i.next();

            g.drawString("["+dep.getToActivityId()+"]",
                    x + 2*padding + j * advance,
                    y + h * 9/10);
        }


        // draw all subactivities
        paintActivities(g, a.getActivities());
    }

}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 **/
