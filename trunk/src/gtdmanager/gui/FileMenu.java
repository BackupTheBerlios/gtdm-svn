/* gtdmanager/gui/FileMenu.java
 * 
 * {{{ package / imports */

package gtdmanager.gui;

import gtdmanager.core.*;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;

/* }}} */
/**
 * <p>Title: FileMenu class</p>
 *
 * <p>Description: Menu frontend for various core functions</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ FileMenu */
public class FileMenu extends JMenu {

	FileMenu(MainWindow window) {
        super();
		setText("Datei");
        add(new FileMenuAction(FileMenuAction.generateProject, window));
        add(new FileMenuAction(FileMenuAction.newProject, window));
        add(new FileMenuAction(FileMenuAction.openProject, window));
        add(new FileMenuAction(FileMenuAction.saveProject, window));
        add(new FileMenuAction(FileMenuAction.deleteProject, window));
        add(new FileMenuAction(FileMenuAction.quit, window));
	}

}

/* }}} */
/**
 * <p>Title: FileMenuAction class</p>
 *
 * <p>Description: Performs actions called from FileMenu</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ FileMenuAction */
class FileMenuAction extends AbstractAction {

    public static String generateProject = "Generiere Testprojekt";
    public static String newProject = "Neues Projekt...";
    public static String openProject= "Öffne Projekt...";
    public static String saveProject = "Projekt Speichern...";
    public static String deleteProject = "Lösche Projekt";
    public static String quit = "GTDManager beenden";

    private static MainWindow parent = null;

    FileMenuAction(String text, MainWindow window) {
        super(text);
        parent = window;
    }

    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
        
        if (name == generateProject) {
            parent.getManager().generateSampleProject();
            System.out.println("project: "
                    + parent.getManager().getProject().getName());
            //JProject project = man.getProject();
            //System.out.println("Ende");

            parent.updateViews();
        }
        else if (name == quit) {
            System.exit(0);
        }
        else {
            System.out.println("FileMenu: unhandled Action: "
                + e.getActionCommand());
        }
    }
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 * */
