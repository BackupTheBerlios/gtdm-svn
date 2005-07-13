/* gtdmanager/gui/ProjectMenu.java
 *
 * {{{ package / imports */

package gtdmanager.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import gtdmanager.core.JActivity;
import gtdmanager.core.JInstance;
import java.util.*;

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
    public ProjectMenu() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ProjectMenu(MainWindow window) {
        super();
		setText("Projekt");
        add(new ProjectMenuAction(ProjectMenuAction.editProject, window));
        add(new ProjectMenuAction(ProjectMenuAction.newInstance, window));
        add(new ProjectMenuAction(ProjectMenuAction.newActivity, window));
        add(new ProjectMenuAction(ProjectMenuAction.editActivity, window));
        add(new ProjectMenuAction(ProjectMenuAction.deleteActivity, window));
	}

    private void jbInit() throws Exception {
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

            if (parent.manager.getProject() == null || parent.manager.getProject().getInstances().size() == 0) {
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

            if (parent.manager.getProject() == null || parent.manager.getProject().getInstances().size() == 0) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie haben noch kein Projekt geladen oder erstellt.", "Kein bestehendes Projekt vorhanden", 2);
                return;
            }

            DialogNewActivity pDlg = new DialogNewActivity(this.parent, "Neue Aufgabe erstellen", true);

            int nIndex = parent.manager.getProject().getInstances().size()-1;
            JInstance currentInstance = (JInstance)parent.manager.getProject().getInstances().get(nIndex);

            pDlg.currentInstance = currentInstance;
            pDlg.mdlInsertAfter.addElement(currentInstance);

            for (int i = 0; i < currentInstance.getActivities().size(); i++) {
              pDlg.mdlInsertAfter.addElement(currentInstance.getActivities().get(i));
              pDlg.mdlActivities.addElement(currentInstance.getActivities().get(i));
            }

            if (parent.getSelection() != null) {

              if (pDlg.mdlInsertAfter.getIndexOf(parent.getSelection()) > -1) {
                  if (parent.getSelection().getClass() == JActivity.class) {
                    pDlg.parentActivity = (JActivity)parent.getSelection();
                    pDlg.mdlInsertAfter.setSelectedItem(parent.getSelection());
                  }
                  else {
                      pDlg.mdlInsertAfter.setSelectedItem(parent.getSelection());
                  }
              }
              else {
                    javax.swing.JOptionPane.showMessageDialog(parent.frame,
                    "Sie m�ssen eine Aufgabe aus der letzten Instanz markieren.", "Falsche Aufgabe markiert", 2);
                    return;
                }
            }

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
