/* gtdmanager/gui/View.java
 *
 * {{{ package / imports */

package gtdmanager.gui;

import gtdmanager.core.*;
import javax.swing.JComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/* }}} */
/**
 * <p>Title: View interface</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ View */
public interface View {
    void update(JProject project);
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 **/
