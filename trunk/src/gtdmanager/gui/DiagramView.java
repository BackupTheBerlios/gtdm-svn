/* gtdmanager/gui/DiagramView
 *
 * {{{ package / imports */

package gtdmanager.gui;

import gtdmanager.core.*;

import java.util.*;
import java.awt.*;
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
    private static int barwidth = 32;

    // diagramview offset in days since epoche
    // (the value is the 3rd may 2005 and only for testing purposes)
    private int offset = 129384;

    // offset of the bars (x is for padding only and y is for vertical
    // positioning); initialize with padding before use!
    private int xoffset = 0, yoffset = 0;

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
        xoffset = yoffset = padding;
        paintActivities(g, i.getActivities());
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
        long start = a.getStartDate().getTimeInMillis()/100/60/60/24;
        long end = a.getEndDate().getTimeInMillis()/100/60/60/24;
        
        // calculate bargeometry for activity
        int w = barwidth;                   // width of this bar
        int h = (int)(end - start);         // length (height) of bar
        int x = xoffset + (int)(start - offset);
        int y = yoffset + w;
        yoffset += w + padding;

        // draw the bar
        g.fillRect(x, y, h, w);

        // ...and the text
        g.drawString(" act " + a.getName()
            + " (" + a.getShortName() + ")"
            + start
            , x
            , y
        );

        // draw all subactivities
        paintActivities(g, a.getActivities());
    }

}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 **/
