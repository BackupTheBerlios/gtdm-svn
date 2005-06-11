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

    JProject project = null;

    static int padding = 16;
    static int barwidth = 32;

    // offset of the bars (only changed by getActivit*)
    private int xoffset = 0, yoffset = 0;

    DiagramView() {
        super();
        System.out.println("this is DiagramView");
    }

    public void update(JProject project) {
        System.out.println("update DiagramView: " + project.getName());

        this.project = project;
        repaint();
    }

    /*    public void updateActivity(DefaultMutableTreeNode top, JActivity act) {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode(act.getName());
        top.add(n);

        ArrayList subacts = act.getActivities();
        if (subacts != null) {
            ListIterator i = subacts.listIterator();
            while (i.hasNext()) updateActivity(n, (JActivity)i.next());
        }
    }*/

    public void paint(Graphics g) {
        if (project != null) {
            ArrayList a = project.getInstances();
            JInstance i = (JInstance)a.get(a.size() - 1);
            if (i != null) {
                System.out.println("paint instance " + i.getId() + " of "
                        + project.getName());

                xoffset = yoffset = padding;
                paintActivities(g, i.getActivities());
            }
        }
        updateUI();
    }

    private void paintActivities(Graphics g, ArrayList a) {
        ListIterator i = a.listIterator();
        while (i.hasNext()) paintActivity(g, (JActivity)i.next());
    }

    
    private void paintActivity(Graphics g, JActivity a) {
        if (a == null) return; // nothing to do

        // calculate bargeometry for activity
        int w = barwidth;
        int h = 200;
        int x = xoffset;
        int y = yoffset + w;
        yoffset += w + padding;

        System.out.println("paint activity " + a.getId());
        g.fillRect(x, y, h, w);

        g.drawString(" act " + a.getName() + " (" + a.getShortName() + ")",
            x, y);

        paintActivities(g, a.getActivities());
    }

}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 **/
