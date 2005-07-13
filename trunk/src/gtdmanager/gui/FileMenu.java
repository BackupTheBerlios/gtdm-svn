/* gtdmanager/gui/FileMenu.java
 *
 * {{{ package / imports */

package gtdmanager.gui;

import gtdmanager.core.*;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
    public FileMenu() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    FileMenu(MainWindow window) {
        super();
		setText("Datei");
        //add(new FileMenuAction(FileMenuAction.generateProject, window));
        add(new FileMenuAction(FileMenuAction.newProject, window));
        add(new FileMenuAction(FileMenuAction.openProject, window));
        add(new FileMenuAction(FileMenuAction.saveProject, window));
        add(new FileMenuAction(FileMenuAction.quit, window));
	}

    private void jbInit() throws Exception {
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

    public static String newProject = "Neues Projekt erstellen...";
    public static String openProject= "Projekt laden...";
    public static String saveProject = "Projekt speichern...";
    public static String quit = "Beenden";

    private static MainWindow parent = null;

    FileMenuAction(String text, MainWindow window) {
        super(text);
        parent = window;
    }

    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();

        if (name == newProject) {
            DialogNewProject pDlg = new DialogNewProject(this.parent, "Neues Projekt erstellen", true);
            pDlg.setLocationRelativeTo(null);
            pDlg.setModal(true);
            pDlg.show();
        }
        else if (name == openProject) {
            parent.opendialog.setDialogTitle("Projekt laden");
            int nRet = parent.opendialog.showOpenDialog(parent.frame);

            if (nRet == JFileChooser.APPROVE_OPTION) {
                String strFilename = parent.opendialog.getSelectedFile().toString();
                parent.manager.lastErrorString = "";
                parent.manager.newProject();

                boolean bRet = parent.manager.loadProject(strFilename);

                if (bRet == true) {

                    if (parent.manager.lastErrorString != "") {
                        javax.swing.JOptionPane.showMessageDialog(parent.frame,
                        "Es ist folgender Fehler aufgetreten: \"" +
                        parent.manager.lastErrorString + "\" das Projekt wird trotzdem geladen." ,
                        "Fehler beim Laden des Projekts", 1);
                    }

                    parent.updateViews();
                }
                else {
                    javax.swing.JOptionPane.showMessageDialog(parent.frame,
                    parent.manager.lastErrorString,
                    "Fehler beim Laden des Projekts", 2);
                    return;
                }
            }
        }
        else if (name == saveProject) {
            parent.savedialog.setDialogTitle("Projekt speichern");
            int nRet = parent.savedialog.showSaveDialog(parent.frame);

            if (nRet == JFileChooser.APPROVE_OPTION) {
                String strFilename = parent.savedialog.getSelectedFile().toString();
                parent.manager.lastErrorString = "";

                boolean bRet = parent.manager.saveProject(strFilename);

                if (bRet == false) {
                    javax.swing.JOptionPane.showMessageDialog(parent.frame,
                    parent.manager.lastErrorString,
                    "Fehler beim Speichern des Projekts", 2);
                    return;
                }

                if (bRet == true && parent.manager.lastErrorString != "") {
                    javax.swing.JOptionPane.showMessageDialog(parent.frame,
                    "Es ist folgender Fehler aufgetreten: \"" +
                    parent.manager.lastErrorString + "\" das Projekt wird trotzdem gespeichert." ,
                    "Fehler beim Speichern des Projekts", 1);
                }
            }
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
