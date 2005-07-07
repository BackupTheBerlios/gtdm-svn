package gtdmanager.gui;

import java.awt.*;

import gtdmanager.core.*;

import javax.swing.*;
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DialogNewProject extends JDialog {
    MainWindow mainwindow = null;

    public DialogNewProject() throws HeadlessException {
        super();

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewProject(MainWindow owner) throws HeadlessException {
        super(owner.frame);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewProject(MainWindow owner, boolean modal) throws
            HeadlessException {
        super(owner.frame, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewProject(MainWindow owner, String title) throws HeadlessException {
        super(owner.frame, title);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewProject(MainWindow owner, String title, boolean modal) throws
            HeadlessException {
        super(owner.frame, title, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewProject(MainWindow owner, String title, boolean modal,
                            GraphicsConfiguration gc) {
        super(owner.frame, title, modal, gc);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewProject(Dialog owner) throws HeadlessException {
        super(owner);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewProject(Dialog owner, boolean modal) throws
            HeadlessException {
        super(owner, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewProject(Dialog owner, String title) throws
            HeadlessException {
        super(owner, title);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewProject(Dialog owner, String title, boolean modal) throws
            HeadlessException {
        super(owner, title, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewProject(Dialog owner, String title, boolean modal,
                            GraphicsConfiguration gc) throws HeadlessException {
        super(owner, title, modal, gc);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jContentPane.setLayout(xYLayout1);
        this.setContentPane(jContentPane);
        this.setSize(386, 192);
        xYLayout1.setWidth(386);
        xYLayout1.setHeight(192);
        txtName.setText("");
        lblName.setAlignmentX((float) 0.5);
        lblName.setMaximumSize(new Dimension(34, 21));
        lblName.setMinimumSize(new Dimension(34, 21));
        lblName.setPreferredSize(new Dimension(34, 21));
        lblName.setText("Projektname:");
        lblAuthor.setAlignmentX((float) 0.5);
        lblAuthor.setMaximumSize(new Dimension(34, 21));
        lblAuthor.setMinimumSize(new Dimension(34, 21));
        lblAuthor.setPreferredSize(new Dimension(34, 21));
        lblAuthor.setText("Autor:");
        txtVersion.setText("");
        lblVersion.setAlignmentX((float) 0.5);
        lblVersion.setMaximumSize(new Dimension(34, 21));
        lblVersion.setMinimumSize(new Dimension(34, 21));
        lblVersion.setPreferredSize(new Dimension(34, 21));
        lblVersion.setText("Version:");
        btnCreate.setText("Projekt erstellen");
        btnCreate.addActionListener(new
                                    DialogNewProject_btnCreate_actionAdapter(this));
        btnCancel.setText("Abbrechen");
        btnCancel.addActionListener(new
                                    DialogNewProject_btnCancel_actionAdapter(this));
        jContentPane.add(txtName, new XYConstraints(104, 15, 124, -1));
        jContentPane.add(txtVersion, new XYConstraints(104, 77, 73, -1));
        jContentPane.add(lblVersion, new XYConstraints(11, 77, 86, -1));
        jContentPane.add(lblAuthor, new XYConstraints(11, 45, 86, -1));
        jContentPane.add(lblName, new XYConstraints(11, 15, 86, -1));
        jContentPane.add(txtAuthor, new XYConstraints(104, 46, 165, -1));
        jContentPane.add(btnCreate, new XYConstraints(13, 126, -1, -1));
        jContentPane.add(btnCancel, new XYConstraints(267, 126, -1, -1));
    }

    JPanel jContentPane = new JPanel();
    XYLayout xYLayout1 = new XYLayout();
    JTextField txtName = new JTextField();
    JLabel lblName = new JLabel();
    JTextField txtAuthor = new JTextField();
    JLabel lblAuthor = new JLabel();
    JTextField txtVersion = new JTextField();
    JLabel lblVersion = new JLabel();
    JButton btnCreate = new JButton();
    JButton btnCancel = new JButton();

    public void btnCancel_actionPerformed(ActionEvent e) {
        dispose();
    }

    public void btnCreate_actionPerformed(ActionEvent e) {
        String strName, strAuthor, strVersion;

        strName = txtName.getText();
        strAuthor = txtAuthor.getText();
        strVersion = txtVersion.getText();

        if (strName.length() == 0 || strAuthor.length() == 0
            || strVersion.length() == 0) {

            javax.swing.JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus.", "Falsche Eingabe", 1);
            return;
        }

        JManager manager = mainwindow.getManager() ;
        manager.newProject(strName, strVersion);

        mainwindow.updateViews();

        dispose();
    }
}


class DialogNewProject_btnCreate_actionAdapter implements ActionListener {
    private DialogNewProject adaptee;
    DialogNewProject_btnCreate_actionAdapter(DialogNewProject adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCreate_actionPerformed(e);
    }
}


class DialogNewProject_btnCancel_actionAdapter implements ActionListener {
    private DialogNewProject adaptee;
    DialogNewProject_btnCancel_actionAdapter(DialogNewProject adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCancel_actionPerformed(e);
    }
}
