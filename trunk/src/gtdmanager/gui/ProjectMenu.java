/* gtdmanager/gui/ProjectMenu.java
 *
 * {{{ package / imports */

package gtdmanager.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import gtdmanager.core.JInstance;

/* }}} */
/**
 * <p>Title: ProjectMenu class</p>
 *
 * <p>Description: Menu frontend for various core functions</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ ProjectMenu */
public class ProjectMenu extends JMenu {

	ProjectMenu(MainWindow window) {
        super();
		setText("Projekt");
        add(new ProjectMenuAction(ProjectMenuAction.editProject, window));
        add(new ProjectMenuAction(ProjectMenuAction.newInstance, window));
        add(new ProjectMenuAction(ProjectMenuAction.deleteInstance, window));
        add(new ProjectMenuAction(ProjectMenuAction.newActivity, window));
        add(new ProjectMenuAction(ProjectMenuAction.editActivity, window));
        add(new ProjectMenuAction(ProjectMenuAction.deleteActivity, window));
	}

}

/* }}} */
/**
 * <p>Title: ProjectMenuAction class</p>
 *
 * <p>Description: Performs actions called from ProjectMenu</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ ProjectMenuAction */
class ProjectMenuAction extends AbstractAction {

    public static String editProject = "Projekt bearbeiten...";
    public static String newInstance = "Neue Instanz erstellen...";
    public static String deleteInstance = "Instanz loeschen";
    public static String newActivity = "Neue Aktivitaet...";
    public static String editActivity = "Aktivitaet bearbeiten...";
    public static String deleteActivity = "Aktivitaet loeschen";

    private static MainWindow parent = null;

    ProjectMenuAction(String text, MainWindow window) {
        super(text);
        parent = window;
    }

    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();

        if (name == editProject) {

            if (parent.manager.getProject() == null) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie haben noch kein Projekt geladen oder erstellt.", "Kein bestehendes Projekt vorhanden", 2);
                return;
            }

            DialogEditProject pDlg = new DialogEditProject(this.parent, "Projekt bearbeiten", true);
            pDlg.setLocationRelativeTo(null);
            pDlg.setModal(true);
            pDlg.show();
        }
        else if (name == newInstance) {

            if (parent.manager.getProject() == null || parent.manager.getProject().getInstance(0) == null) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie haben noch kein Projekt geladen oder erstellt.", "Kein bestehendes Projekt vorhanden", 2);
                return;
            }

            DialogNewInstance pDlg = new DialogNewInstance(this.parent, "Neue Instanz erstellen", true);
            pDlg.setLocationRelativeTo(null);
            pDlg.setModal(true);
            pDlg.show();
        }
        else if (name == newActivity) {

            if (parent.manager.getProject() == null || parent.manager.getProject().getInstance(0) == null) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie haben noch kein Projekt geladen oder erstellt.", "Kein bestehendes Projekt vorhanden", 2);
                return;
            }

            DialogNewActivity pDlg = new DialogNewActivity(this.parent, "Neue Aktivitaet erstellen", true);
            pDlg.setLocationRelativeTo(null);
            pDlg.setModal(true);
            pDlg.show();
        }
    }
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 * */
