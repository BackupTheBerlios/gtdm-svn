/* gtdmanager/gui/ProjectMenu.java
 * 
 * {{{ package / imports */

package gtdmanager.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;

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

	ProjectMenu() {
        super();
		setText("Projekt");
        add(new ProjectMenuAction(ProjectMenuAction.editProject));
        add(new ProjectMenuAction(ProjectMenuAction.newInstance));
        add(new ProjectMenuAction(ProjectMenuAction.editInstance));
        add(new ProjectMenuAction(ProjectMenuAction.deleteInstance));
        add(new ProjectMenuAction(ProjectMenuAction.newActivity));
        add(new ProjectMenuAction(ProjectMenuAction.editActivity));
        add(new ProjectMenuAction(ProjectMenuAction.deleteActivity));
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
    public static String newInstance = "Neues Projekt";
    public static String editInstance = "Neue Instanz";
    public static String deleteInstance = "Instanz löschen";
    public static String newActivity = "Neue Aktivität";
    public static String editActivity = "Aktivität bearbeiten...";
    public static String deleteActivity = "Aktivität löschen";

    ProjectMenuAction(String text) {
        super(text);
    }

    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
        
        if (false) {
        }
        else {
            System.out.println("ProjectMenu: unhandled Action: "
                + e.getActionCommand());
        }
    }
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 * */
