/* gtdmanager/gui/DiagramView
 *
 * {{{ package / imports */

package gtdmanager.gui;

import gtdmanager.core.*;

import java.lang.Math;
import java.text.*;
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
    implements View, MouseListener, MouseMotionListener,
               MouseWheelListener
                   
{

    /* Variables {{{ */

    // varios messages the dialog spits out
    private static String msgNone = "";
    private static String msgDrag = "Mit der Maus Sichtbereich verschieben";
    private static String msgNoProject = "Kein Projekt geladen";
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
    private int today;  // reset evry paint()

    // reset every paint (days)
    private int instanceStartDate, instanceEndDate;

    private int showDays = 48;
    private int minShowDays = 2;    // sollte nicht < 2 sein
    private int maxShowDays = 360;

    private Calendar gridStart = null;

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

    // reset every paint
    private Graphics2D g;

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


    MainWindow parent = null;

    /* Variables }}} */

    /* Constructors {{{ */
    DiagramView(MainWindow parent) {
        super();
        this.parent = parent;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }
    /* Constructors }}} */

    // stringify - always return correct string (TM) {{{
    private String stringify(Calendar cal) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(cal.getTime());
    }
    private String stringify(int day) { 
        return stringify(day2cal(day));
    }
    //}}}
    private void drawVert(int x, int y, String str) { //{{{
        for (int j = 0; j < str.length(); j++) {
            g.drawString(str.substring(j, j + 1), x, y + j * ascent);
            g.drawString(str.substring(j, j + 1), x + 1, y + j * ascent);
        }
    } //}}}
    private void drawHoriz(int x, int y, String str) { //{{{
        g.drawString(str, x, y);
        g.drawString(str, x + 1, y);
    } //}}}

    // day2cal/cal2day {{{
    private Calendar day2cal(int day)
    {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTimeInMillis(day * 1000L * 60L * 60L * 24L);
            return cal;
    }

    private int cal2day(Calendar cal)
    {
        return (int)(cal.getTimeInMillis() / 1000L / 60L / 60L / 24L);
    }
    //}}}
    
    // MouseListener/MouseMotionListener/MouseWheelListener Implementation {{{
    public void mouseMoved (MouseEvent e) {
        mouseLastX = e.getX();
        mouseLastY = e.getY();
    }

    public void mouseDragged (MouseEvent e) {
        if (project != null) {
            int xstep = (e.getX() - mouseLastX)/gridStep.x;
            if (xstep != 0) {
                startDate -= xstep;
                endDate -= xstep;
                repaint();

                mouseLastX = e.getX();

                // now the user should know :-)
                msgDrag = "";
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


    public void mouseWheelMoved (MouseWheelEvent e) {
        showDays += e.getWheelRotation();
        if (showDays < minShowDays) showDays = minShowDays;
        if (showDays > maxShowDays) showDays = maxShowDays;

        message = "Er werden nun etwa "
            + showDays + " Tage im Diagramm gezeigt.";

        repaint();
    }
    
    //}}}

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
            startDate = cal2day(currentInstance.getStartDate());
            gridStart = currentInstance.getStartDate();
            // TODO: this one should be configurable
            endDate = startDate + showDays;   // show 35 days
        }

        repaint();
    }
    /* View Implementation }}} */

    /* JComponent Overloading (paint) {{{ */
    public void paint(Graphics not2Dg) {
        g = (Graphics2D)not2Dg;

        if (project == null)
        {
            g.setColor(Color.gray);
            g.drawString(msgNoProject,
                (getWidth() - g.getFontMetrics().stringWidth(msgNoProject)) / 2,
                (getHeight() - ascent) /2
            );
            return;
        }

        advance = g.getFontMetrics().getMaxAdvance();
        ascent = g.getFontMetrics().getMaxAscent();
        //descent = g.getFontMetrics().getMaxDescent();

        today = cal2day(Calendar.getInstance());
        instanceStartDate = cal2day(currentInstance.getStartDate());
        instanceEndDate = cal2day(currentInstance.getEndDate());

        // update date frame
        endDate = startDate + showDays;


        // clear all geometries
        actRects.clear();

        if (showGantt) {
            ganttPaintGrid(g);
        
            // set initial y position
            yActOffset = gridRect.y + padding;

            ganttPaintActivities(currentInstance.getActivities());

            ganttPaintDependencies(currentInstance.getActivities());
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
            tdriftPaintActivities(g);
        }
    }
    /* JComponent Overloading }}} */

    // TERMINDRIFT
    /* Termindrift Paint {{{ */

    /* tdriftPaintGrid {{{ */
    private void tdriftPaintGrid(Graphics2D g) {
        for (int x = 0, y = 0;  //-10 * gridStep.x, y = - 10 * gridStep.y;
                x < gridRect.width || y < gridRect.height;
                x += gridStep.x, y += gridStep.y)
        {
            int day = startDate + x/gridStep.x;

            // gray out, days out of project
            if (day < instanceStartDate || day > instanceEndDate)
            {
                g.setColor(new Color(0xaaaaaa));

                // vertical {{{
                {
                    int xp[] = {
                        gridRect.x + x + 1,
                        gridRect.x + x + gridStep.x,
                        gridRect.x + x + gridStep.x,
                        gridRect.x + x + 1
                    };
                    int yp[] = {
                        gridRect.y,
                        gridRect.y,
                        gridRect.y + y + gridStep.y + 1,
                        gridRect.y + y + 1
                    };
                    g.fillPolygon(xp, yp, 4);
                }
                // }}}
                // horizontal {{{
                {
                    int yp[] = {
                        gridRect.y + y + 1,
                        gridRect.y + y + gridStep.y,
                        gridRect.y + y + gridStep.y,
                        gridRect.y + y + 1
                    };
                    int xp[] = {
                        gridRect.x,
                        gridRect.x,
                        gridRect.x + x + gridStep.x + 1,
                        gridRect.x + x + 1
                    };
                    g.fillPolygon(xp, yp, 4);
                }
                //}}}
            }

            if (x != 0)
                if (day < instanceStartDate || day > instanceEndDate)
                    g.setColor(new Color(0xa0a0a0));
                else
                    g.setColor(new Color(0xbbbbbb));
            else
                g.setColor(new Color(0x888888));


            // vertical daylines
            g.drawLine(gridRect.x+x, gridRect.y+y, gridRect.x+x, gridRect.y);
            // horizontal daylines
            g.drawLine(gridRect.x+x, gridRect.y+y, gridRect.x, gridRect.y+y);
                //gridRect.y + gridRect.height);
            // horozontal
            //g.drawLine(gridRect.x + 1, gridRect.y + y,
            //        gridRect.x + gridRect.width, gridRect.y + y);

            // diagonal
            g.setColor(new Color(0x888888));
            g.drawLine(gridRect.x + x, gridRect.y + y,
                    gridRect.x + x + gridStep.x, gridRect.y + y + gridStep.y);

        }
        
        // todayline {{{
        if (today >= startDate) {
            g.setColor(new Color(0xf0c080));
            int lineHalfWidth = (int)Math.floor((double)Math.abs(lineWidth)/2);
            for (int i = -lineHalfWidth; i < lineHalfWidth; i++)
                g.drawLine(
                    gridRect.x + (today - startDate) * gridStep.x + i,
                    gridRect.y,
                    gridRect.x + (today - startDate) * gridStep.x + i,
                    gridRect.y + gridRect.height
                );
        }
        // }}}
    }
    /* tdriftPaintGrid }}} */

    private void tdriftPaintActivities(Graphics2D g) //{{{
    {
        // get interator over all current activities
        ListIterator aIt = currentInstance.getActivities().listIterator();

        while (aIt.hasNext())
        {
            int aid = ((JActivity)aIt.next()).getId();

            g.setColor(Color.black);

            // draw shortName at y = endDate
            {
                JActivity a = currentInstance.getActivity(aid);
                int x = gridRect.x
                    + (cal2day(a.getEndDate()) - startDate) * gridStep.x;
                int y = gridRect.y - ascent;

                if (x >= gridRect.x)
                {
                    g.drawString(a.getShortName(), x - 2, y);
                    g.drawLine(x, y, x, y + ascent);
                }
            }

            int x0 = 0, y0 = 0, x, y;
            int iCrtDay, aEndDay = 0;

            String shortName = null;
            int shortNameX = 0, shortNameY = 0;

            // get iterator over all instances
            ListIterator iIt = project.getInstances().listIterator();
            while (iIt.hasNext())
            {
                JInstance i = (JInstance)iIt.next();
                JActivity a = i.getActivity(aid);

                // skip if activity is non-existent in this instance
                if (a == null) continue;
            
                g.setColor(new Color(a.getColor()));
                
                iCrtDay = cal2day(i.getCreationDate());
                aEndDay = cal2day(a.getEndDate());

                // initialize first point, if not already done
                if (x0 + y0 == 0)
                {
                    x0 = gridRect.x;
                    y0 = gridRect.y + (aEndDay - startDate) * gridStep.y;
                }

                // initialize second point
                x = gridRect.x + (iCrtDay - startDate) * gridStep.x;
                y = y0;

                // draw horizontal line
                if (x > gridRect.x)
                    g.drawLine( Math.max(x0, gridRect.x),
                                Math.max(y0, gridRect.y),
                                Math.max(x, gridRect.x),
                                Math.max(y, gridRect.y) );

                x0 = x;
                // y0 = y;

                // x = x0;
                y = gridRect.y + (aEndDay - startDate) * gridStep.y;
                
                // draw vertical line
                if (x > gridRect.x)
                    g.drawLine( Math.max(x0, gridRect.x),
                                Math.max(y0, gridRect.y),
                                Math.max(x, gridRect.x),
                                Math.max(y, gridRect.y) );

                // set first point for next line
                // x0 = x;
                y0 = y;

                // get potential shortName offset
                if (x <= gridRect.x || shortNameX + shortNameY == 0)
                {
                    shortName = a.getShortName();
                    shortNameX = gridRect.x
                        - g.getFontMetrics().stringWidth(shortName) - 1;
                    shortNameY = y0 - 2;
                }

            }

            // the + 1 ensures, that the line is drawn if today or endDay
            // is the left-most day in the diagram
            x = gridRect.x
                + (Math.min(today, aEndDay) - startDate) * gridStep.x + 1;
            y = y0;

            if (x > gridRect.x)
            {
                // draw final horizontal line
                g.drawLine( Math.max(x0, gridRect.x),
                            Math.max(y0, gridRect.y),
                            Math.max(x, gridRect.x),
                            Math.max(y, gridRect.y) );

                // draw shortName (if any)
                if (shortName != null)
                    g.drawString(shortName, shortNameX, shortNameY);

                // draw "extra" line, to the shortName
                g.drawLine(shortNameX, shortNameY + 2,
                        shortNameX
                            + g.getFontMetrics().stringWidth(shortName),
                        shortNameY + 2);
            }

        }
    }
    //}}}

    /* Termindrift Paint }}} */

    // GANTT
    /* Gantt Paint Routines {{{ */
    private void ganttPaintGrid(Graphics2D g) { //{{{
        // init geometry
        gridRect = new Rectangle(
            -1,
            padding,
            getWidth() + 1,
            //getHeight() - 2 * padding

            // i'm really sorry here, but for schedules' sake:
            1024*1024*1024
        );
        gridStep = new Point(
            gridRect.width / (endDate - startDate),
            0 // gridRect.height / (endDate - startDate)
        );

        // set minimal width to ensure readability
        if (gridStep.x < advance/2)
        {
            gridStep.x = advance/2;
            //showDays = gridRect.width / gridStep.x;
        }

        // draw message (outside the clipping area)
        g.setColor(Color.gray);
        g.drawString(message, gridRect.x + 2, gridRect.y - 2);
        
        // set clip rect, so that nothing can be drawn outside
        g.clip(gridRect);
        
        // grid borders
        g.setColor(Color.gray);
        g.drawRect(gridRect.x, gridRect.y, gridRect.width, gridRect.height - 1);
        
        // day grid
        for (int i = 0; i < gridRect.width; i += gridStep.x) {
            int day = startDate + i/gridStep.x;
            Calendar cal = day2cal(day);

            int x = gridRect.x + i;
            int y = gridRect.y;
            int h = gridRect.height - 2;
            int w = gridStep.x - 1;
            int strx = x + w / 2 - g.getFontMetrics().charWidth('0') / 2;

            g.setColor(new Color(0x999999));
            g.drawLine(x, y, x, y + h);

            boolean inProjectFrame = true;

            // gray out days out of project
            if (day < cal2day(currentInstance.getStartDate())
                    || day > cal2day(currentInstance.getEndDate())) {
                g.setColor(new Color(0xaaaaaa));
                g.fillRect(x + 1, y + 1, w, h);
                inProjectFrame = false; // grayed out
            }

            switch (cal.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    if (!inProjectFrame) break;
                    g.setColor(new Color(0xf0b0c0)); //Color.red);
                    g.fillRect(x + 1, y + 1, w, h);
                    break;

                case Calendar.SATURDAY:
                    if (!inProjectFrame) break;
                    g.setColor(new Color(0xf0d0e0)); //Color.red);
                    g.fillRect(x + 1, y + 1, w, h);
                    break;

                case Calendar.MONDAY:
                    g.setColor(new Color(0x999999));
                    drawVert(strx,
                        y + getHeight() - (stringify(day).length()+1) * ascent,
                        stringify(day));
                    break;
                    
                default:
                    //g.setColor(new Color(0xc0c0c0));
                    break;
            }

            if (day == today) {
                g.setColor(new Color(0xf0b080));
                g.fillRect(x + 1, y + 1, w, h);

                g.setColor(new Color(0xd08060));
                drawVert(strx,
                        y + getHeight() - (stringify(day).length()+1) * ascent,
                        stringify(day));
            }

            // print date at:
            // StartDate {{{
            if (day == cal2day(currentInstance.getStartDate())) {
                //currentInstance.getStartDate().add(Calendar.DATE, 1);
                //if (cal.before(currentInstance.getStartDate())) {
                    g.setColor(new Color(0x777777));
                    drawVert(strx,
                        y + getHeight() - (stringify(day).length()+1) * ascent,
                        stringify(day));
                //}
                //currentInstance.getStartDate().add(Calendar.DATE, -1);
            }
            // }}}
            // EndDate {{{
            //if (cal.before(currentInstance.getEndDate())) {
            if (day == cal2day(currentInstance.getEndDate())) {
                //currentInstance.getEndDate().add(Calendar.DATE, -1);
                //if (cal.after(currentInstance.getEndDate())) {
                    g.setColor(new Color(0x777777));
                    drawVert(strx,
                        y + getHeight() - (stringify(day).length()+1) * ascent,
                        stringify(day));
                //}
                //currentInstance.getEndDate().add(Calendar.DATE, 1);
            }
            // }}}

        }

        // print note:
        // project is later {{{
        if (cal2day(currentInstance.getStartDate())
                > endDate) {
                g.setColor(Color.red);
                String s = "Projektbeginn: "
                    + stringify(currentInstance.getStartDate())
                    + " ->";
                drawHoriz(
                    gridRect.x + gridRect.width - s.length() * advance/3
                    , 324
                    , s
                );
        }
        // }}}
        // project is earlier {{{
        if (cal2day(currentInstance.getEndDate())
                < startDate) {
                g.setColor(Color.red);
                String s = "<- Projektende: "
                    + stringify(currentInstance.getStartDate());
                drawHoriz(
                    gridRect.x + advance/3
                    , 324
                    , s
                );
        }
        // }}}

    }//}}}

    private void ganttPaintActivities(ArrayList a) { //{{{
        // iterate through all activities.
        // this method is meant to be called recursive
        // TODO: the core needs: getAllActivities(PREORDER|POSTORDER|INORDER)
        ListIterator i = a.listIterator();
        while (i.hasNext()) ganttPaintActivity((JActivity)i.next());
    }//}}}

    private Rectangle ganttPaintActivity(JActivity a) {//{{{
        // check if there a is an activity, else we have nothing to do here..
        if (a == null) return null;

        // our granularity is 1 day, offset is start of epoch (in UTC)
        int start = cal2day(a.getStartDate());
        int end = cal2day(a.getEndDate());
        
        // calculate geometry for activity bar
        //System.out.println(a.getShortName() + " " + (start - startDate));
        Rectangle r = new Rectangle(
            //gridRect.x + (start - startDate) * gridStep.x,
            //toX(a.getStartDate()),
            gridRect.x
                + (cal2day(a.getStartDate()) - startDate)
                * gridStep.x,
            yActOffset,
            // +1: end day ist inclusive:
            gridStep.x * (end - start + 1),               // length
            barWidth
        );

        // store geometry
        actRects.put(a, r);

        // set y position for next activity
        yActOffset = r.y + r.height + padding;

        // draw activity if visible
        if (gridRect.intersects(r)) {
            // draw the bar
            g.setColor(new Color(a.getColor()));
            g.fill(r);  //Rect(r.x, r.y, r.width, r.height);

            // ...and the text
            g.setColor(Color.black);
            g.drawString(a.getShortName()
                , r.x + (r.width 
                    - g.getFontMetrics().stringWidth(a.getShortName())) / 2
                , r.y + (barWidth + ascent) / 2 + 1
            );
            g.drawString(a.getShortName()
                , r.x + (r.width 
                    - g.getFontMetrics().stringWidth(a.getShortName())) / 2 + 1
                , r.y + (barWidth + ascent) / 2 + 1
            );
            g.setColor(Color.white);
            g.drawString(a.getShortName()
                , r.x + (r.width 
                    - g.getFontMetrics().stringWidth(a.getShortName())) / 2
                , r.y + (barWidth + ascent) / 2
            );
        }

        // draw all subactivities
        ganttPaintActivities(a.getActivities());

        return r;
    }//}}}

    
    private void ganttPaintDependencies(ArrayList a) {//{{{
        // iterate through all activities.
        // this method is meant to be called recursive
        // TODO: the core needs: getAllActivities(PREORDER|POSTORDER|INORDER)
        ListIterator i = a.listIterator();
        while (i.hasNext()) ganttPaintDependencies((JActivity)i.next());
    }//}}}
    
    private void ganttPaintDependencies(JActivity from) {//{{{
        Rectangle fromRect = (Rectangle)actRects.get(from);

        g.setColor(Color.darkGray);
        g.drawRect(fromRect.x, fromRect.y, fromRect.width, fromRect.height);

        ListIterator i = from.getDependencies().listIterator();
        while (i.hasNext()) {
            JDependency d = (JDependency)i.next();
            JActivity to = currentInstance.getActivity(d.getToActivityId());
            Rectangle toRect = (Rectangle)actRects.get(to);

            // short names
            Point f, t; // from/to point
            int ud = 8; // unit divisor
            int u = barWidth / ud; // unit
            int uh = u + barWidth / 2; // unit height

            /*try {
                if (((JActivity)parent.getSelection()).getId() == from.getId())
                    g.setColor(new Color(0xFFFFFF));
            } catch (Exception e) {
                g.setColor(new Color(0x424242));
            }*/
            
            if (d.getDependencyType() == JDependency.BEGINBEGIN) {//{{{
                    f = new Point(
                        fromRect.x - 1,
                        fromRect.y + fromRect.height / 2
                    );
                    t = new Point(toRect.x - 1, toRect.y + toRect.height / 2);

                    if (f.x < t.x)
                    {
                        g.drawLine(f.x, f.y, f.x - u, f.y);
                        g.drawLine(f.x - u, f.y, f.x - u, t.y);
                        g.drawLine(f.x - u, t.y, t.x, t.y);
                    }
                    else
                    {
                        g.drawLine(f.x, f.y, t.x - u, f.y);
                        g.drawLine(t.x - u, f.y, t.x - u, t.y);
                        g.drawLine(t.x - u, t.y, t.x, t.y);
                    }
            }//}}}
            else if (d.getDependencyType() == JDependency.BEGINEND) {//{{{
                    f = new Point(fromRect.x - 1,
                        fromRect.y + fromRect.height / 2);
                    t = new Point(
                        toRect.x + toRect.width + 1,
                        toRect.y + toRect.height / 2);

                    if (f.x < t.x)
                    {
                        g.drawLine(f.x, f.y, f.x - u, f.y);
                        if (f.y < t.x)
                        {
                            g.drawLine(f.x - u, f.y, f.x - u, f.y + uh + u);
                            g.drawLine(f.x-u, f.y + uh + u, t.x + u, f.y+uh+u);
                            g.drawLine(t.x + u, f.y + uh + u, t.x + u, t.y);
                        }
                        else
                        {
                            g.drawLine(f.x - u, f.y, f.x - u, f.y - uh - u);
                            g.drawLine(f.x-u, f.y - uh - u, t.x + u, f.y-uh-u);
                            g.drawLine(t.x + u, f.y - uh - u, t.x + u, t.y);
                        }
                        g.drawLine(t.x + u, t.y, t.x, t.y);
                    }
                    else
                    {
                        g.drawLine(f.x, f.y, t.x + u, f.y);
                        g.drawLine(t.x + u, f.y, t.x + u, t.y);
                        g.drawLine(t.x + u, t.y, t.x, t.y);
                    }
            }//}}}
            else if (d.getDependencyType() == JDependency.ENDBEGIN) {//{{{
                    f = new Point(fromRect.x + fromRect.width + 1,
                        fromRect.y + fromRect.height / 2 );
                    t = new Point(toRect.x - 1, toRect.y + toRect.height / 2 );

                    if (f.x < t.x)
                    {
                        g.drawLine(f.x, f.y, t.x - u, f.y);
                        g.drawLine(t.x - u, f.y, t.x - u, t.y);
                        g.drawLine(t.x - u, t.y, t.x, t.y);
                    }
                    else
                    {
                        g.drawLine(f.x, f.y, f.x + u, f.y);
                        if (f.y < t.y)
                        {
                            g.drawLine(f.x + u, f.y, f.x + u, f.y + uh);
                            g.drawLine(f.x + u, f.y + uh, t.x - u, f.y + uh);
                            g.drawLine(t.x - u, f.y + uh, t.x - u, t.y);
                        }
                        else
                        {
                            g.drawLine(f.x + u, f.y, f.x + u, f.y - uh);
                            g.drawLine(f.x + u, f.y - uh, t.x - u, f.y - uh);
                            g.drawLine(t.x - u, f.y - uh, t.x - u, t.y);
                        }
                        g.drawLine(t.x - u, t.y, t.x, t.y);
                    }
            }//}}}
            else if (d.getDependencyType() == JDependency.ENDEND) {//{{{
                    f = new Point(fromRect.x + fromRect.width + 1,
                        fromRect.y + fromRect.height / 2 );
                    t = new Point(toRect.x + toRect.width + 1,
                        toRect.y + toRect.height / 2 );

                    if (f.x < t.x)
                    {
                        g.drawLine(f.x, f.y, t.x + u, f.y);
                        g.drawLine(t.x + u, f.y, t.x + u, t.y);
                        g.drawLine(t.x + u, t.y, t.x, t.y);
                    }
                    else
                    {
                        g.drawLine(f.x, f.y, f.x + u, f.y);
                        g.drawLine(f.x + u, f.y, f.x + u, t.y);
                        g.drawLine(f.x + u, t.y, t.x, t.y);
                    }
            } //}}}
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

                f = t = null;
            }
        }
    }//}}}
    /* Gantt Paint Routines }}} */

}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 **/
