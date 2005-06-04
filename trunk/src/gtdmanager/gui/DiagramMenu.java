/* gtdmanager/gui/DiagramMenu.java
 * 
 * {{{ package / imports */

package gtdmanager.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;

/* }}} */
/**
 * <p>Title: DiagramMenu class</p>
 *
 * <p>Description: Menu frontend for various core functions</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ DiagramMenu */
public class DiagramMenu extends JMenu {

	DiagramMenu() {
        super();
		setText("Diagramm");
        add(new DiagramMenuAction(DiagramMenuAction.showGanttDiagram));
        add(new DiagramMenuAction(DiagramMenuAction.showTermindriftDiagram));
        add(new DiagramMenuAction(DiagramMenuAction.exportDiagram));
        add(new DiagramMenuAction(DiagramMenuAction.diagramProperties));
	}

}

/* }}} */
/**
 * <p>Title: DiagramMenuAction class</p>
 *
 * <p>Description: Performs actions called from DiagramMenu</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ DiagramMenuAction */
class DiagramMenuAction extends AbstractAction {

    public static String showGanttDiagram = "Ganttdiagram zeigen";
    public static String showTermindriftDiagram = "Termindriftdiagramm zeigen";
    public static String exportDiagram = "Diagram exportieren";
    public static String diagramProperties = "Diagrameigenschaften";

    DiagramMenuAction(String text) {
        super(text);
    }

    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
        
        if (false) {
        }
        else {
            System.out.println("DiagramMenu: unhandled Action: "
                + e.getActionCommand());
        }
    }
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 * */
