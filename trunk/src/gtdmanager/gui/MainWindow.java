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

    //file open and save dialogs
    JFileChooser opendialog = new JFileChooser();
    JFileChooser savedialog = new JFileChooser();

    // manager
    JManager manager = new JManager();

    // gui
    JFrame frame = new JFrame();
    JPanel panel = new JPanel(new BorderLayout(), true);
    JMenuBar menuBar = new JMenuBar();

    // list of all views
    ArrayList views = new ArrayList();
    JComponent diagramView = null;

    Object selection = null;

    public void setSelection(Object s) {
        selection = s;
        //updateViews();
    }
    public Object getSelection() {
        return selection;
    }

    public JComponent getDiagramView() {
        return diagramView;
    }

    MainWindow() {
        // create menu
        menuBar.add(new FileMenu(this));
        menuBar.add(new ProjectMenu(this));
        menuBar.add(new DiagramMenu(this));
        menuBar.add(new HelpMenu());
        panel.add(menuBar, BorderLayout.NORTH);

        // add views to array
        views.add(new TreeView(this));
        views.add(diagramView = (JComponent)new DiagramView(this));

        // add the two views to the frame via splitpane
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane((Component)views.get(0)),
                new JScrollPane((Component)views.get(1))
        );
        sp.setDividerLocation(150);
        panel.add(sp, BorderLayout.CENTER);

        // setup the mainwindow frame
        frame.getContentPane().add(panel);
        frame.setSize(800, 440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 **/
