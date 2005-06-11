/* gtdmanager/gui/MainWindow
 *
 * {{{ package / imports */

package gtdmanager.gui;

import gtdmanager.core.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

/* }}} */
/**
 * <p>Title: MainWindow class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ MainWindow */
public class MainWindow {

    // manager
    JManager manager = new JManager();

    // gui
    JFrame frame = new JFrame();
    JPanel panel = new JPanel(new BorderLayout(), true);
//    JPanel panelForViews = new JPanel(new GridBagLayout(), true);
    JMenuBar menuBar = new JMenuBar();

    ArrayList views = new ArrayList();

    MainWindow() {
        // initialize gui
        //fileMenu.setMnemonic(KeyEvent.VK_D);
        /*GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;*/

        // create menu
        menuBar.add(new FileMenu(this));
        menuBar.add(new ProjectMenu());
        menuBar.add(new DiagramMenu());
        menuBar.add(new HelpMenu());
        panel.add(menuBar, BorderLayout.NORTH);

        // add views to array
        views.add(new TreeView(new javax.swing.tree.DefaultMutableTreeNode()));
        views.add(new DiagramView());

        // add the two views to the frame via splitpane
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                (Component)views.get(0),
                (Component)views.get(1)
        );
        sp.setDividerLocation(150);
        panel.add(sp, BorderLayout.CENTER);

        // setup the mainwindow frame
        frame.getContentPane().add(panel);
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }

    public JManager getManager() {
        return manager;
    }

    public void updateViews() {
        ListIterator i = views.listIterator();
        while (i.hasNext()) {
            View v = (View)i.next();
            v.update(manager.getProject());
        }
    }
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 **/
