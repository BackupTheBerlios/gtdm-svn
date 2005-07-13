/* gtdmanager/gui/DiagramMenu.java
 * 
 * {{{ package / imports */

package gtdmanager.gui;

import java.awt.event.*;
import javax.swing.*;
//import javax.swing.JMenu;
//import javax.swing.JMenuItem;
import gtdmanager.gui.DiagramView;
import gtdmanager.gui.MainWindow;

import org.freehep.graphics2d.VectorGraphics;
import org.freehep.util.export.ExportDialog;

import org.freehep.graphicsio.jpg.JPGExportFileType;
import org.freehep.graphicsio.ps.EPSExportFileType;

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

    MainWindow parent;
    JMenuItem gantt, tdrift;

    public MainWindow getParentWindow() {
        return parent;
    }
    
	DiagramMenu(MainWindow parent) {
        super();
		setText("Diagramm");
        gantt = add(new DiagramMenuAction(
                    DiagramMenuAction.showGanttDiagram, this));
        tdrift = add(new DiagramMenuAction(
                    DiagramMenuAction.showTermindriftDiagram, this));
        add(new DiagramMenuAction(DiagramMenuAction.exportDiagram, this));
        add(new DiagramMenuAction(DiagramMenuAction.diagramProperties, this));

        this.parent = parent;

        update();
    }

    public void update() {
        gantt.setEnabled(!DiagramView.showGantt);
        tdrift.setEnabled(DiagramView.showGantt);

        parent.updateViews();
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

    private DiagramMenu parent;

    DiagramMenuAction(String text, DiagramMenu parent) {
        super(text);
        this.parent = parent;
    }

    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
        
        if (e.getActionCommand() == showGanttDiagram) {
            DiagramView.showGantt = true;

        }
        else if (e.getActionCommand() == showTermindriftDiagram) {
            DiagramView.showGantt = false;
        }
        else if (e.getActionCommand() == exportDiagram) {
            //JMenuItem exportItem = new JMenuItem( "Export..." );
            //exportItem.addActionListener( new ActionListener() {
            //public void actionPerformed( ActionEvent e ) {
            ExportDialog export = new ExportDialog();
            export.addExportFileType(new JPGExportFileType());
            export.addExportFileType(new EPSExportFileType());
            export.showExportDialog(parent.getParent(),
                "Diagramm exportieren als...",
                parent.getParentWindow().getDiagramView(),
                "export" );
            System.out.println("*** Alles ueber dieser Zeile sind keine"
                    + " Fehler, dass gehoert so :-) ***");
            //}
            //});
        }
        else {
            System.out.println("DiagramMenu: unhandled Action: "
                + e.getActionCommand());
        }

        parent.update();
    }
}

/* }}}
 *
 * vim:ts=4:sts=4:sw=4:et:fdm=marker
 * */
