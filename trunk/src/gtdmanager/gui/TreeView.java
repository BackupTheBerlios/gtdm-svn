/* gtdmanager/gui/TreeView
 *
 * {{{ package / imports */

package gtdmanager.gui;

import gtdmanager.core.*;
import javax.swing.JTree;
import javax.swing.JComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
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
public class TreeView extends JTree implements View {

    static DefaultMutableTreeNode top = new DefaultMutableTreeNode();

    TreeView() {
        super(top);//new DefaultMutableTreeNode());
        //parent = window;
    }

    public void update(JProject project) {
        System.out.println("update TreeView: " + project.getName());
        top.setUserObject(project.getName());

        ListIterator l = project.getInstances().listIterator();;
        while (l.hasNext()) {
            JInstance i = (JInstance)l.next();
            DefaultMutableTreeNode n = new DefaultMutableTreeNode(i.getName());
            top.add(n);

            ListIterator m = i.getActivities().listIterator();
            while (m.hasNext()) updateActivity(n, (JActivity)m.next());
        }
        
        updateUI();
    }

    public void updateActivity(DefaultMutableTreeNode top, JActivity act) {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode(act.getName());
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
