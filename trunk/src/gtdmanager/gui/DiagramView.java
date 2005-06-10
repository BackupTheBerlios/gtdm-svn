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

    JInstance instance;

    DiagramView() {
        super();
        System.out.println("this is DiagramView");
    }

    public void update(JProject project) {

        instance = project.getInstance(0);

        System.out.println("update DiagramView: " + project.getName());
        
        updateUI();
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
        //paintActivities(g, instance.getActivities());
    }

    private void paintActivities(Graphics g, ArrayList a) {
        if (a != null) {
            ListIterator i = a.listIterator();
            while (i.hasNext()) paintActivity(g, (JActivity)i.next());
        }
    }

    
    private void paintActivity(Graphics g, JActivity a) {
        g.drawString(" Hello, Pink Panther! ", 30,30);
    }

}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 **/
