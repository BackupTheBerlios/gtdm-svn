/* gtdmanager/gui/DiagramView
 *
 * {{{ package / imports */

package gtdmanager.gui;

import gtdmanager.core.*;

import java.lang.Math;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
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
public class DiagramView extends JComponent implements View,
        MouseListener, MouseMotionListener {

    // varios messages the dialog spits out
    private static String msgNone = "";
    private static String msgDrag = "Mit der Maus Sichtbereich verschieben";
    // the drawn message
    private String message = msgNone;

    // this is the default gap between componentborder and the drawings
    // (and the gap between two drawings (ie. activitybars))
    private static int padding = 16;

    // this is the default width of an activitybar
    private static int barWidth = 32;

    // diagramview offset in days since epoche
    // (the value is the 3rd may 2005 and only for testing purposes)
    private int startDate = 0; //12935;
    private int endDate = 0; //2975;

    // geometry of the diagramgrid and spefwidth
//    private int gridX, gridY, gridW, gridH, 
    private int gridStep;
    private Rectangle gridRect = null;

    // font metrics
    private int advance, ascent; //, descent;

    // offset of the bars (x is for padding only and y is for vertical
    // positioning); initialize with padding before use!
    private int yOffset = 0; //, xoffset = 0;
    private int yActOffset = 0; //, xoffset = 0;

    // mouse position
    static int mouseLastX = 0;
    static int mouseLastY = 0;

    // this is the project to draw
    private JProject project = null;
    private JInstance instance = null;

    // table of all geometries of drawn activities for drawong dependencies.
    // The keys are the activities themself and the value is the activities'
    // geometry (as Rectangle)
    private HashMap actRects = new HashMap();

    DiagramView() {
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /* MouseListener/MouseMotionListener implementation {{{ */
    public void mouseMoved (MouseEvent e) {
        mouseLastX = e.getX();
        mouseLastY = e.getY();
    }

    public void mouseDragged (MouseEvent e) {
        int xstep = (e.getX() - mouseLastX)/gridStep;
        if (xstep != 0) {
            startDate -= xstep;
            endDate -= xstep;
            repaint();

            mouseLastX = e.getX();
        }
    }

    public void mousePressed (MouseEvent e) {
    }

    public void mouseReleased (MouseEvent e) {
    }

    public void mouseEntered (MouseEvent e) {
        message = msgDrag;
        repaint();
    }

    public void mouseExited (MouseEvent e) {
        message = msgNone;
        repaint();
    }

    public void mouseClicked (MouseEvent e) {
    }
    /* }}} */

    public void update(JProject project) {
        // as we "update" this component every paint() there is no explicit
        // update at this point, just keep care that we are darwing the
        // correct project and do an explicit repaint in order to show changes
        // instantly..
        this.project = project;
        this.instance = project.getInstance(0);

        // calculate initial diagram frame
        if (startDate == 0 && endDate == 0) {
            startDate = (int)(instance.getStartDate().getTimeInMillis()
                    /1000L/60L/60L/24L);
            // TODO: this one should be configurable
            endDate = startDate + 40;
        }

        repaint();
    }

    public void paint(Graphics not2Dg) {
        if (project == null) return;

        // if project is set, get the last instance here. there should be
        // really a method in the core for this..
        ArrayList a = project.getInstances();
        JInstance i = (JInstance)a.get(a.size() - 1);
        if (i == null) return;

        Graphics2D g = (Graphics2D)not2Dg;

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
        gridRect = new Rectangle(
            -1,
            padding,
            getWidth() + 1,
            getHeight() - 2 * padding
        );
        gridStep = gridRect.width / (endDate - startDate);

        // set minimal width to ensure readability
        if (gridStep < advance/2) gridStep = advance/2;

        // draw message
        g.setColor(Color.gray);
        g.drawString(message, gridRect.x + padding, gridRect.y
                + gridRect.height + ascent);
        
        // set clip rect, so that nothing is drawn outside
        g.clip(gridRect);

        paintGrid(g);

        // set initial y position
        yActOffset = gridRect.y + padding;

        actRects.clear();
        paintActivities(g, instance.getActivities());

        paintDependencies(g, instance.getActivities());
    }

    private void paintGrid(Graphics2D g) {
        g.setColor(Color.gray);
        g.drawRect(gridRect.x, gridRect.y, gridRect.width, gridRect.height - 1);
        
        for (int i = 0; i < gridRect.width; i += gridStep) {
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis((startDate + i/gridStep)*24L*60L*60L*1000L);

            // sunday red spans from the sunday line to the monday line
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                g.setColor(Color.red);

            g.drawLine(gridRect.x + i, gridRect.y, gridRect.x + i,
                    gridRect.y + gridRect.height);

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
                            gridRect.x + i + 2,
                            gridRect.y + gridRect.height
                                + (j + 1 - 11) * ascent);
            }
        }

    }

    private void paintActivities(Graphics2D g, ArrayList a) {
        // iterate through all activities.
        // this method is meant to be called recursive
        // TODO: the core needs: getAllActivities(PREORDER|POSTORDER|INORDER)
        ListIterator i = a.listIterator();
        while (i.hasNext()) paintActivity(g, (JActivity)i.next());
    }

    private Rectangle paintActivity(Graphics2D g, JActivity a) {
        // check if there a is an activity, else we have nothing to do here..
        if (a == null) return null;

        // our granularity is 1 day, offset is start of epoch (in UTC)
        int start = (int)(a.getStartDate().getTimeInMillis()/1000L/60L/60L/24L);
        int end = (int)(a.getEndDate().getTimeInMillis()/1000L/60L/60L/24L);
        
        // calculate geometry for activity bar
        Rectangle r = new Rectangle(
            gridRect.x + (start - startDate) * gridStep,
            yActOffset,
            gridStep * (end - start),               // length
            barWidth
        );

        // store geometry
        actRects.put(a, r);

        // set y position for next activity
        yActOffset = r.y + r.height + padding;

        // draw activity if visible
        if (gridRect.intersects(r)) {
            // draw the bar
            g.setColor(Color.black);
            g.fill(r);  //Rect(r.x, r.y, r.width, r.height);

            // ...and the text
            g.setColor(Color.white);
            g.drawString(""
                + a.getShortName()
                , r.x + (r.width - a.getShortName().length() * advance / 2) / 2
                , r.y + (barWidth + ascent) / 2
            );
        }

        // draw all subactivities
        paintActivities(g, a.getActivities());

        return r;
    }

    
    private void paintDependencies(Graphics2D g, ArrayList a) {
        // iterate through all activities.
        // this method is meant to be called recursive
        // TODO: the core needs: getAllActivities(PREORDER|POSTORDER|INORDER)
        ListIterator i = a.listIterator();
        while (i.hasNext()) paintDependencies(g, (JActivity)i.next());
    }
    
    private void paintDependencies(Graphics2D g, JActivity from) {
        Rectangle fromRect = (Rectangle)actRects.get(from);

        g.setColor(Color.darkGray);
        g.drawRect(fromRect.x, fromRect.y, fromRect.width, fromRect.height);

        ListIterator i = from.getDependencies().listIterator();
        while (i.hasNext()) {
            JDependency d = (JDependency)i.next();
            JActivity to = instance.getActivity(d.getToActivityId());
            Rectangle toRect = (Rectangle)actRects.get(to);

            int lineWidth = 5;

            // TODO: right now we make straight lines, but they should not
            // intercept with activities..

            int lineHalfWidth = (int)Math.floor((double)Math.abs(lineWidth)/2);
            if (d.getDependencyType() == JDependency.BEGINBEGIN)
                for (int j = -lineHalfWidth; j <= lineHalfWidth; j++)
                    g.drawLine(
                        fromRect.x - 1,
                        fromRect.y + fromRect.height / 2 + j,
                        toRect.x - 1,
                        toRect.y + toRect.height / 2 + j
                    );
            else if (d.getDependencyType() == JDependency.BEGINEND)
                for (int j = -lineHalfWidth; j <= lineHalfWidth; j++)
                    g.drawLine(
                        fromRect.x - 1,
                        fromRect.y + fromRect.height / 2 + j,
                        toRect.x + toRect.width + 1,
                        toRect.y + toRect.height / 2 + j
                    );
            else if (d.getDependencyType() == JDependency.ENDBEGIN)
                for (int j = -lineHalfWidth; j <= lineHalfWidth; j++)
                    g.drawLine(
                        fromRect.x + fromRect.width + 1,
                        fromRect.y + fromRect.height / 2 + j,
                        toRect.x - 1,
                        toRect.y + toRect.height / 2 + j
                    );
            else if (d.getDependencyType() == JDependency.ENDEND)
                for (int j = -lineHalfWidth; j <= lineHalfWidth; j++)
                    g.drawLine(
                        fromRect.x + fromRect.width + 1,
                        fromRect.y + fromRect.height / 2 + j,
                        toRect.x + toRect.width + 1,
                        toRect.y + toRect.height / 2 + j
                    );
            else {
                g.setColor(Color.red);
                g.drawString("ungültige Abhängigkeit"
                    + " " + d.getId()
                    + " (type=" + d.getDependencyType() + ")"
                    + " zu "
                    + to.getShortName()
                    , fromRect.x
                    , fromRect.y + fromRect.height + (d.getId()+1) * ascent
                );
            }

        }
    }
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 **/
