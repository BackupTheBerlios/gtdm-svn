/* gtdmanager/gui/HelpMenu.java
 * 
 * {{{ package / imports */

package gtdmanager.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;

/* }}} */
/**
 * <p>Title: HelpMenu class</p>
 *
 * <p>Description: Menu frontend for various core functions</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ HelpMenu */
public class HelpMenu extends JMenu {

	HelpMenu() {
        super();
		setText("Hilfe");
        add(new HelpMenuAction(HelpMenuAction.manual));
        add(new HelpMenuAction(HelpMenuAction.about));
	}

}

/* }}} */
/**
 * <p>Title: HelpMenuAction class</p>
 *
 * <p>Description: Performs actions called from HelpMenu</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Tomislav Viljetić
 * @version 1.0
 * {{{ HelpMenuAction */
class HelpMenuAction extends AbstractAction {

    public static String manual = "Handbuch...";
    public static String about = "Über...";

    HelpMenuAction(String text) {
        super(text);
    }

    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
        
        if (false) {
        }
        else {
            System.out.println("HelpMenu: unhandled Action: "
                + e.getActionCommand());
        }
    }
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 * */
