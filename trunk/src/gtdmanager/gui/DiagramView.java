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
public class DiagramView extends JComponent
        implements View, MouseListener, MouseMotionListener {

    /* Variables {{{ */
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
    private Point gridStep = null;
    private Rectangle gridRect = null;

    // line thickness for various (non-grid) lines
    private int lineWidth = 5;

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
    private JInstance initialInstance = null;
    private JInstance currentInstance = null;

    // table of all geometries of drawn activities for drawong dependencies.
    // The keys are the activities themself and the value is the activities'
    // geometry (as Rectangle)
    private HashMap actRects = new HashMap();

    // if this one is set to true, all DiagramViews are showing Gantt diagrams
    // else Termindrift diagrams
    // NOTE: this one is not optimal solved, but required for the menu and
    //   view structure.
    // maybe-TODO: extend the interfaces Menu and View to handle such stuff.
    static public boolean showGantt = true;

    /* Variables }}} */

    /* Constructors {{{ */
    DiagramView() {
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    /* Constructors }}} */

    /* MouseListener/MouseMotionListener Implementation {{{ */
    public void mouseMoved (MouseEvent e) {
        mouseLastX = e.getX();
        mouseLastY = e.getY();
    }

    public void mouseDragged (MouseEvent e) {
        if (project != null) {
            if (showGantt) {
                int xstep = (e.getX() - mouseLastX)/gridStep.x;
                if (xstep != 0) {
                    startDate -= xstep;
                    endDate -= xstep;
                    repaint();

                    mouseLastX = e.getX();
                }
            }
        }
        // if nothing matches, handle this as a simple move
        else mouseMoved(e);
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
    /* MouseListener/MouseMotionListener Implementation }}} */

    /* View Implementation {{{ */
    public void update(JProject project) {

        if (project == null) return; // nothing to do

        // if project is set, get the first/last instance here. there should be
        // really a method in the core for this..
        this.project = project;
        initialInstance = project.getInstance(0);
        currentInstance = (JInstance)project.getInstances().get(
                    project.getInstances().size() - 1);

        // calculate initial diagram frame
        if (startDate == 0 && endDate == 0) {
            startDate = (int)(currentInstance.getStartDate().getTimeInMillis()
                    /1000L/60L/60L/24L);
            // TODO: this one should be configurable
            endDate = startDate + 35;   // show 35 days
        }

        repaint();
    }
    /* View Implementation }}} */

    /* JComponent Overloading (paint) {{{ */
    public void paint(Graphics not2Dg) {
        if (project == null) return;

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

        // clear all geometries
        actRects.clear();

        if (showGantt) {
            ganttPaintGrid(g);
        
            // set initial y position
            yActOffset = gridRect.y + padding;

            ganttPaintActivities(g, currentInstance.getActivities());

            ganttPaintDependencies(g, currentInstance.getActivities());
        }
        else {
            // init geometry
            gridRect = new Rectangle(
                padding + 2 * advance,
                padding + ascent,
                getWidth() + 1 - padding - 2 * advance,
                getHeight() + 1 - padding - ascent
            );
            gridStep = new Point(
                gridRect.width / (endDate - startDate),
                gridRect.height / (endDate - startDate)
            );

            // set minimal width to ensure readability
            //if (gridStep.x < advance/2) gridStep.x = advance/2;

            // draw message (outside the clipping area)
            g.setColor(Color.gray);
            g.drawString(message, gridRect.x + padding, gridRect.y
                    + gridRect.height + ascent);
            

            tdriftPaintGrid(g);
            tdriftPaintInitialActivities(g, initialInstance.getActivities());
            tdriftPaintCurrentActivities(g, currentInstance.getActivities());
        
            // todayline
            int today = (int)((new Date()).getTime()/1000L/60L/60L/24L);
            g.setColor(Color.cyan);
            int lineHalfWidth = (int)Math.floor((double)Math.abs(lineWidth)/2);
            for (int i = -lineHalfWidth; i < lineHalfWidth; i++)
                g.drawLine(
                    gridRect.x + (today - startDate) * gridStep.x + i,
                    gridRect.y,
                    gridRect.x + (today - startDate) * gridStep.x + i,
                    gridRect.y + gridRect.height
                );
        }
    }
    /* JComponent Overloading }}} */

    /* Termindrift Paint {{{ */

    /* tdriftPaintGrid {{{ */
    private void tdriftPaintGrid(Graphics2D g) {
        // grid
        for (int x = 0, y = 0; x < gridRect.width || y < gridRect.height;
                x += gridStep.x, y += gridStep.y)
        {
            if (x == 0)
                g.setColor(Color.gray);
            else
                g.setColor(Color.lightGray);
            // vertical
            g.drawLine(gridRect.x + x, gridRect.y + 1,
                    gridRect.x + x, gridRect.y + gridRect.height);
            // horozontal
            g.drawLine(gridRect.x + 1, gridRect.y + y,
                    gridRect.x + gridRect.width, gridRect.y + y);

            // diagonal
            g.setColor(Color.gray);
            g.drawLine(gridRect.x + x, gridRect.y + y,
                    gridRect.x + x + gridStep.x, gridRect.y + y + gridStep.y);
        }
    }
    /* tdriftPaintGrid }}} */

    /* tdriftPaintInitialActivities {{{ */
    private void tdriftPaintInitialActivities(Graphics2D g, ArrayList a) {
        ListIterator i = a.listIterator();
        while (i.hasNext()) tdriftPaintInitialActivity(g, (JActivity)i.next());
    }
    /* tdriftPaintInitialActivities }}} */

    /* tdriftPaintInitialActivitiy {{{ */
    private void tdriftPaintInitialActivity(Graphics2D g, JActivity a) {
        int end = (int)(a.getEndDate().getTimeInMillis()/1000L/60L/60L/24L);
        
        // calculate geometry for activity
        Point p = new Point(
            gridRect.x + (end - startDate) * gridStep.x,
            gridRect.y - ascent
        );

        // store "geometry"
        //actRects.put(a, p);

        g.setColor(Color.black);
        g.drawString(a.getShortName(), p.x, p.y);
        g.setColor(Color.gray);
        g.drawLine(p.x, p.y, p.x, gridRect.y);

        // NOTE: we don't go deeper than one level
    }
    /* tdriftPaintInitialActivitiy }}} */

    /* tdriftPaintCurrentActivities {{{ */
    private void tdriftPaintCurrentActivities(Graphics2D g, ArrayList a) {
        ListIterator i = a.listIterator();
        while (i.hasNext()) tdriftPaintCurrentActivity(g, (JActivity)i.next());
    }
    /* tdriftPaintCurrentActivities }}} */

    /* tdriftPaintCurrentActivitiy {{{ */
    private void tdriftPaintCurrentActivity(Graphics2D g, JActivity a) {
        int end = (int)(a.getEndDate().getTimeInMillis()/1000L/60L/60L/24L);
        int today = (int)((new Date()).getTime()/1000L/60L/60L/24L);
        
        // calculate geometry for activity
        Rectangle r = new Rectangle(
            gridRect.x - a.getShortName().length() * advance / 2,
            gridRect.y + (end - startDate) * gridStep.y,
            gridRect.x + ((today > end ? end : today) - startDate) * gridStep.x,
            0
        );

        // store "geometry"
        //actRects.put(a, p);

        g.setColor(Color.black);
        g.drawString(a.getShortName(), r.x, r.y);
        g.setColor(Color.gray);
        g.drawLine(r.x, r.y, r.width, r.y);

        // NOTE: we don't go deeper than one level
    }
    /* tdriftPaintCurrentActivitiy }}} */
    /* Termindrift Paint }}} */

    /* Gantt Paint Routines {{{ */
    private void ganttPaintGrid(Graphics2D g) {
        // init geometry
        gridRect = new Rectangle(
            -1,
            padding,
            getWidth() + 1,
            getHeight() - 2 * padding
        );
        gridStep = new Point(
            gridRect.width / (endDate - startDate),
            0 // gridRect.height / (endDate - startDate)
        );

        // set minimal width to ensure readability
        if (gridStep.x < advance/2) gridStep.x = advance/2;
        // draw message (outside the clipping area)
        g.setColor(Color.gray);
        g.drawString(message, gridRect.x + padding, gridRect.y
                + gridRect.height + ascent);
        
        // set clip rect, so that nothing can be drawn outside
        g.clip(gridRect);
        
        // grid borders
        g.setColor(Color.gray);
        g.drawRect(gridRect.x, gridRect.y, gridRect.width, gridRect.height - 1);
        
        // day grid
        for (int i = 0; i < gridRect.width; i += gridStep.x) {
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis((startDate + i/gridStep.x)*24L*60L*60L*1000L);

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

    private void ganttPaintActivities(Graphics2D g, ArrayList a) {
        // iterate through all activities.
        // this method is meant to be called recursive
        // TODO: the core needs: getAllActivities(PREORDER|POSTORDER|INORDER)
        ListIterator i = a.listIterator();
        while (i.hasNext()) ganttPaintActivity(g, (JActivity)i.next());
    }

    private Rectangle ganttPaintActivity(Graphics2D g, JActivity a) {
        // check if there a is an activity, else we have nothing to do here..
        if (a == null) return null;

        // our granularity is 1 day, offset is start of epoch (in UTC)
        int start = (int)(a.getStartDate().getTimeInMillis()/1000L/60L/60L/24L);
        int end = (int)(a.getEndDate().getTimeInMillis()/1000L/60L/60L/24L);
        
        // calculate geometry for activity bar
        Rectangle r = new Rectangle(
            gridRect.x + (start - startDate) * gridStep.x,
            yActOffset,
            gridStep.x * (end - start),               // length
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
        ganttPaintActivities(g, a.getActivities());

        return r;
    }

    
    private void ganttPaintDependencies(Graphics2D g, ArrayList a) {
        // iterate through all activities.
        // this method is meant to be called recursive
        // TODO: the core needs: getAllActivities(PREORDER|POSTORDER|INORDER)
        ListIterator i = a.listIterator();
        while (i.hasNext()) ganttPaintDependencies(g, (JActivity)i.next());
    }
    
    private void ganttPaintDependencies(Graphics2D g, JActivity from) {
        Rectangle fromRect = (Rectangle)actRects.get(from);

        g.setColor(Color.darkGray);
        g.drawRect(fromRect.x, fromRect.y, fromRect.width, fromRect.height);

        ListIterator i = from.getDependencies().listIterator();
        while (i.hasNext()) {
            JDependency d = (JDependency)i.next();
            JActivity to = currentInstance.getActivity(d.getToActivityId());
            Rectangle toRect = (Rectangle)actRects.get(to);

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
    /* Gantt Paint Routines }}} */

}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 **/
