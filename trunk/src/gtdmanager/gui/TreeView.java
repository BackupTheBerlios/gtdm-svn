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
    //DefaultMutableTreeNode top;

//    JProject project = null;
//    JInstance instance = null;

    TreeView(MainWindow parent) {
//        DefaultMutableTreeNode t = new DefaultMutableTreeNode();
        //super(t);//new DefaultMutableTreeNode());
        //parent = window;
        //top = t;
        //setModel(new DefaultTreeModel(
        //            top = new DefaultMutableTreeNode(), false));
        setModel(null); // initialize empty tree
        //setSelectionModel(EmptySelectionModel.sharedInstance());
        //selectionModel.addTreeSelectionListener(selectionRedirector);
        //setCellRenderer(new DefaultTreeCellRenderer());
        //updateUI();
        //this.userObject = top = new DefaultMutableTreeNode();
        //this.allowsChildren = true;
        this.parent = parent;
        addTreeSelectionListener(this);
    }

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode n = (DefaultMutableTreeNode)
            e.getPath().getLastPathComponent();
        if (n != null) {
            parent.setSelection(n.getUserObject());
            // highlight dependencies:
            parent.getDiagramView().repaint();
        }
        //System.out.println("selected: " + parent.getSelection());
        //System.out.println("id: "+((JActivity)parent.getSelection()).getId());
    }

    public void update(JProject project) {

        if (project == null) return; // nothing to do

        DefaultMutableTreeNode top = new DefaultMutableTreeNode();
        setModel(new DefaultTreeModel(top, false));
        //top.removeAllChildren();
        top.setUserObject(project);

        ListIterator l = project.getInstances().listIterator();
        while (l.hasNext()) {
            JInstance i = (JInstance)l.next();
            DefaultMutableTreeNode n = new DefaultMutableTreeNode(i);
            top.add(n);
        
            //System.out.println("TreeView: " + i);

            ListIterator m = i.getActivities().listIterator();
            while (m.hasNext()) updateActivity(n, (JActivity)m.next());
        }

        // expand complete tree
        int row = 0; 
        while (row < getRowCount())  expandRow(row++);

        //updateUI();
    }

    private void updateActivity(DefaultMutableTreeNode top, JActivity act) {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode(act);
        top.add(n);

        //System.out.println("TreeView:   " + act);

        /*try
        {
            if (((JActivity)parent.getSelection()).getId() == act.getId())
            {
                TreePath p[] = n.getPath();
                for (int i = 0; i < p.length; i++)
                    expandPath(p[i]);
            }
        } catch (Exception e) {}*/

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
