/* gtdmanager/gui/TreeView
 *
 * {{{ package / imports */

package gtdmanager.gui;

import gtdmanager.core.*;
import javax.swing.JTree;
import javax.swing.JComponent;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.util.*;

/* }}} */
/**
 * <p>Title: TreeView class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ TreeView */
public class TreeView extends JTree implements View, TreeSelectionListener {

    MainWindow parent = null;
    DefaultMutableTreeNode top;

//    JProject project = null;
//    JInstance instance = null;

    TreeView(DefaultMutableTreeNode t, MainWindow parent) {
//        DefaultMutableTreeNode t = new DefaultMutableTreeNode();
        super(t);//new DefaultMutableTreeNode());
        //parent = window;
        top = t;
        this.parent = parent;
        addTreeSelectionListener(this);
    }

    public void valueChanged(TreeSelectionEvent e) {
        parent.setSelection(
            ((DefaultMutableTreeNode)
                e.getPath().getLastPathComponent()).getUserObject()
        );
        //System.out.println("selected: " + parent.getSelection());
        //System.out.println("id: "+((JActivity)parent.getSelection()).getId());
    }

    public void update(JProject project) {

        if (project == null) return; // nothing to do

        top.removeAllChildren();

        top.setUserObject(project);

        ListIterator l = project.getInstances().listIterator();
        while (l.hasNext()) {
            JInstance i = (JInstance)l.next();
            DefaultMutableTreeNode n = new DefaultMutableTreeNode(i);
            top.add(n);

            ListIterator m = i.getActivities().listIterator();
            while (m.hasNext()) updateActivity(n, (JActivity)m.next());
        }

        updateUI();
    }

    public void updateActivity(DefaultMutableTreeNode top, JActivity act) {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode(act);
        top.add(n);

        ArrayList subacts = act.getActivities();
        if (subacts != null) {
            ListIterator i = subacts.listIterator();
            while (i.hasNext()) updateActivity(n, (JActivity)i.next());
        }
    }
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 **/
