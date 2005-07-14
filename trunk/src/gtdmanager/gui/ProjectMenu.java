/* gtdmanager/gui/ProjectMenu.java
 *
 * {{{ package / imports */

package gtdmanager.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import gtdmanager.core.JActivity;
import gtdmanager.core.JDependency;
import gtdmanager.core.JInstance;
import java.util.*;
import javax.swing.DefaultListModel;
import javax.swing.DefaultComboBoxModel;

/* }}} */
/**
 * <p>Title: ProjectMenu class</p>
 *
 * <p>Description: Menu frontend for various core functions</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetiae‡
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
        add(new ProjectMenuAction(ProjectMenuAction.adminDependencies, window));

        add(new ProjectMenuAction(ProjectMenuAction.diagramSettings, window));
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
 * @author Tomislav Viljetiae‡
 * @version 1.0
 * {{{ ProjectMenuAction */
class ProjectMenuAction extends AbstractAction {

    public static String editProject = "Projekt bearbeiten...";
    public static String newInstance = "Neue Instanz erstellen...";
    public static String newActivity = "Neue Aufgabe...";
    public static String editActivity = "Aufgabe bearbeiten...";
    public static String deleteActivity = "Aufgabe loeschen";
    public static String adminDependencies = "Abhaengigkeiten verwalten...";

    public static String diagramSettings = "Diagrammeigenschaften";

    private static MainWindow parent = null;

    ProjectMenuAction(String text, MainWindow window) {
        super(text);
        parent = window;
    }

    void getAllDependencies(DefaultListModel mdlObjects, DefaultListModel mdlNames, JInstance currInst, ArrayList lstActs) {

        for (int i = 0; i < lstActs.size(); i++) {
            JActivity act = (JActivity)lstActs.get(i);

            for( int x = 0; x < act.getDependencies().size(); x++) {
                JDependency dep = (JDependency)act.getDependencies().get(x);

                mdlObjects.addElement(dep);

                String str = "Abhaengigkeit " + act.getName() + " zu " + currInst.getActivity(dep.getToActivityId());
                mdlNames.addElement((String)str);

                System.out.println("Dep " + act.getDependencies().get(x) + " gehoert zu " + act.getName());
            }

            getAllDependencies(mdlObjects, mdlNames, currInst, act.getActivities());
        }
    }

    void getAllActivities(ArrayList lstActs, DefaultListModel mdlAdd) {

        for (int i = 0; i < lstActs.size(); i++) {
            JActivity act = (JActivity)lstActs.get(i);
            mdlAdd.addElement(act);
            getAllActivities(act.getActivities(), mdlAdd);
        }
    }

    void getAllActivities(ArrayList lstActs, DefaultComboBoxModel mdlAdd) {

        for (int i = 0; i < lstActs.size(); i++) {
            JActivity act = (JActivity)lstActs.get(i);
            mdlAdd.addElement(act);
            getAllActivities(act.getActivities(), mdlAdd);
        }
    }

    boolean isActivityInList(ArrayList lstActs, JActivity actSearch) {
        for (int i = 0; i < lstActs.size(); i++) {
            JActivity actCheck = (JActivity)lstActs.get(i);

            if (actSearch.equals(actCheck) == true) {
                return true;
            }

            if (isActivityInList(actCheck.getActivities(), actSearch) == true) {
              return true;
            }
        }

        return false;
    }

    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();

        if (name == editProject) {

            if (parent.manager.getProject() == null) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie haben noch kein Projekt geladen oder erstellt.",
                "Kein bestehendes Projekt vorhanden", 2);
                return;
            }

            DialogEditProject pDlg = new DialogEditProject(this.parent, "Projekt bearbeiten", true);
            pDlg.setLocationRelativeTo(null);
            pDlg.setResizable(false);
            pDlg.setModal(true);
            pDlg.show();
        }
        else if (name == newInstance) {

            if (parent.manager.getProject() == null || parent.manager.getProject().getInstances().size() == 0) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie haben noch kein Projekt geladen oder erstellt.",
                "Kein bestehendes Projekt vorhanden", 2);
                return;
            }

            DialogNewInstance pDlg = new DialogNewInstance(this.parent, "Neue Instanz erstellen", true);

            int nIndex = parent.manager.getProject().getInstances().size()-1;
            JInstance currentInstance = (JInstance)parent.manager.getProject().getInstances().get(nIndex);

            pDlg.currentInstance = currentInstance;
            pDlg.setLocationRelativeTo(null);
            pDlg.setResizable(false);
            pDlg.setModal(true);
            pDlg.show();
        }
        else if (name == newActivity) {

            if (parent.manager.getProject() == null || parent.manager.getProject().getInstances().size() == 0) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie haben noch kein Projekt geladen oder erstellt.",
                "Kein bestehendes Projekt vorhanden", 2);
                return;
            }

            DialogNewActivity pDlg = new DialogNewActivity(this.parent, "Neue Aufgabe erstellen", true);

            int nIndex = parent.manager.getProject().getInstances().size()-1;
            JInstance currentInstance = (JInstance)parent.manager.getProject().getInstances().get(nIndex);

            pDlg.currentInstance = currentInstance;
            pDlg.mdlInsertAfter.addElement(currentInstance);
            getAllActivities(currentInstance.getActivities(), pDlg.mdlInsertAfter);

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
                  "Sie muessen eine Aufgabe aus der letzten Instanz \"" +
                  currentInstance.getName() + "\" markieren.",
                  "Falsche Aufgabe markiert", 2);
                  return;
                }
            }

            pDlg.setLocationRelativeTo(null);
            pDlg.setResizable(false);
            pDlg.setModal(true);
            pDlg.show();
        }
        else if (name == editActivity) {

            if (parent.manager.getProject() == null || parent.manager.getProject().getInstances().size() == 0) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie haben noch kein Projekt geladen oder erstellt.",
                "Kein bestehendes Projekt vorhanden", 2);
                return;
            }

            if (parent.getSelection() == null || parent.getSelection().getClass() != JActivity.class) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Bitte markieren Sie eine Aufgabe im Baum auf der linken Seite.",
                "Keine Aufgabe markiert", 2);
                return;
            }

            int nIndex = parent.manager.getProject().getInstances().size()-1;
            JInstance currentInstance = (JInstance)parent.manager.getProject().getInstances().get(nIndex);
            JActivity currentActivity = (JActivity)parent.getSelection();

            boolean bRet = isActivityInList(currentInstance.getActivities(), currentActivity);

            if (bRet == false) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie muessen eine Aufgabe aus der letzten Instanz \"" +
                currentInstance.getName() + "\" markieren.",
                "Falsche Aufgabe markiert", 2);
                return;
            }

            DialogEditActivity pDlg = new DialogEditActivity(this.parent, "Aufgabe bearbeiten", true);
            pDlg.currentActivity = currentActivity;
            pDlg.setLocationRelativeTo(null);
            pDlg.setResizable(false);
            pDlg.setModal(true);
            pDlg.show();
        }
        else if (name == deleteActivity) {

            if (parent.manager.getProject() == null || parent.manager.getProject().getInstances().size() == 0) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie haben noch kein Projekt geladen oder erstellt.", "Kein bestehendes Projekt vorhanden", 2);
                return;
            }

            if (parent.getSelection() == null || parent.getSelection().getClass() != JActivity.class) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Bitte markieren Sie eine Aufgabe im Baum auf der linken Seite.",
                "Keine Aufgabe markiert", 2);
                return;
            }

            int nIndex = parent.manager.getProject().getInstances().size()-1;
            JInstance currentInstance = (JInstance)parent.manager.getProject().getInstances().get(nIndex);
            JActivity currentActivity = (JActivity)parent.getSelection();

            boolean bRet = isActivityInList(currentInstance.getActivities(), currentActivity);

            if (bRet == false) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie muessen eine Aufgabe aus der letzten Instanz \"" +
                currentInstance.getName() + "\" markieren.",
                "Falsche Aufgabe markiert", 2);
                return;
            }

            String [] optionen = {"Ja", "Nein"};
            int wahl = javax.swing.JOptionPane.showOptionDialog(
                       parent.frame,  "Sind Sie sicher, dass Sie die Aufgabe" +
                       " \"" + currentActivity.getName() + "\" loeschen moechten?",
                       "Aufgabe loeschen",
                       javax.swing.JOptionPane.YES_NO_OPTION,
                       javax.swing.JOptionPane.QUESTION_MESSAGE,
                       null,
                       optionen, optionen[0]);

            if (wahl == javax.swing.JOptionPane.YES_OPTION) {

              JActivity checkAct = currentInstance.getActivity(currentActivity.getId());
              currentInstance.deleteDependencies(checkAct.getId());

              boolean bRetDel = currentInstance.deleteActivity(checkAct.getId());

              if (bRetDel == false) {
                  javax.swing.JOptionPane.showMessageDialog(parent.frame,
                  "Die Aufgabe konnte nicht geloescht werden.",
                  "Fehler beim Loeschen", 0);
                  return;
              }

              parent.updateViews();
            }
        }
        else if (name == adminDependencies) {

            if (parent.manager.getProject() == null || parent.manager.getProject().getInstances().size() == 0) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie haben noch kein Projekt geladen oder erstellt.",
                "Kein bestehendes Projekt vorhanden", 2);
                return;
            }

            int nIndex = parent.manager.getProject().getInstances().size()-1;
            JInstance currentInstance = (JInstance)parent.manager.getProject().getInstances().get(nIndex);

            if (currentInstance.getActivities().size() < 2) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie muessen mindestens zwei Aufgaben zur aktuellen Instanz hinzugefuegt haben, um Abhaengigkeiten zu verwalten.",
                "Nicht genuegend Aufgaben vrohanden", 2);
                return;
            }

            DialogAdminDependency pDlg = new DialogAdminDependency(this.parent, "Abhaengigkeiten verwalten", true);

            getAllActivities(currentInstance.getActivities(), pDlg.mdlActivitiesFrom);
            getAllActivities(currentInstance.getActivities(), pDlg.mdlActivitiesTo);
            getAllDependencies(pDlg.mdlDependenciesObjects, pDlg.mdlDependenciesNames, currentInstance, currentInstance.getActivities());

            pDlg.currentInstance = currentInstance;
            pDlg.setLocationRelativeTo(null);
            pDlg.setResizable(false);
            pDlg.setModal(true);
            pDlg.show();
        }
        else if (name == diagramSettings) {

            if (parent.manager.getProject() == null || parent.manager.getProject().getInstances().size() == 0) {
                javax.swing.JOptionPane.showMessageDialog(parent.frame,
                "Sie haben noch kein Projekt geladen oder erstellt.",
                "Kein bestehendes Projekt vorhanden", 2);
                return;
            }

            DialogViewSettings pDlg = new DialogViewSettings(this.parent, "Diagrammeinstellungen", true);
            pDlg.setLocationRelativeTo(null);
            pDlg.setModal(true);
            pDlg.setResizable(false);
            pDlg.show();
        }
    }
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 * */
