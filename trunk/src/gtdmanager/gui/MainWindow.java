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
    JPanel panel = new JPanel(new GridBagLayout(), true);
    JPanel panelForViews = new JPanel(new GridBagLayout(), true);

    JMenuBar menuBar = new JMenuBar();

    ArrayList views = new ArrayList();

    MainWindow() {
        // initialize gui
        //fileMenu.setMnemonic(KeyEvent.VK_D);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;

        menuBar.add(new FileMenu(this));
        menuBar.add(new ProjectMenu());
        menuBar.add(new DiagramMenu());
        menuBar.add(new HelpMenu());
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.gridheight = 1;
        panel.add(menuBar, c);

        views.add(new TreeView(new javax.swing.tree.DefaultMutableTreeNode()));
        views.add(new DiagramView());
      //views.add(new TreeView(new javax.swing.tree.DefaultMutableTreeNode()));
        ListIterator it = views.listIterator();
        c.weighty = 1.0;
        c.gridheight = GridBagConstraints.REMAINDER;
        c.gridwidth = 1;
        float i = 0.1f;
        while (it.hasNext()) {
            c.weightx = i; i += 0.9f;
            JComponent jc = (JComponent)it.next();
            panelForViews.add(jc, c); //, -1);//, BorderLayout.WEST);
        }
        panel.add(panelForViews, c);

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
