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
    JPanel panelForViews = new JPanel(new BorderLayout(), true);
    JMenuBar menuBar = new JMenuBar();

    ArrayList views = new ArrayList();

    MainWindow() {
        // initialize gui
        //fileMenu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(new FileMenu(this));
        menuBar.add(new ProjectMenu());
        menuBar.add(new DiagramMenu());
        menuBar.add(new HelpMenu());
        panel.add(menuBar, BorderLayout.NORTH);

        views.add(new TreeView());
        ListIterator i = views.listIterator();
        while (i.hasNext()) {
            JComponent v = (JComponent)i.next();
            panelForViews.add(v, BorderLayout.WEST);
        }
        panel.add(panelForViews, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setSize(800, 300);
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
