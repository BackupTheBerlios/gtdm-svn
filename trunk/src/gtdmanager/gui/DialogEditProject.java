package gtdmanager.gui;

import java.awt.*;

import gtdmanager.core.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
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
public class DialogEditProject extends JDialog {
    MainWindow mainwindow = null;
    JInstance firstInstance = null;

    public DialogEditProject() throws HeadlessException {
        super();

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogEditProject(MainWindow owner) throws HeadlessException {
        super(owner.frame);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogEditProject(MainWindow owner, boolean modal) throws
            HeadlessException {
        super(owner.frame, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogEditProject(MainWindow owner, String title) throws HeadlessException {
        super(owner.frame, title);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogEditProject(MainWindow owner, String title, boolean modal) throws
            HeadlessException {
        super(owner.frame, title, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogEditProject(MainWindow owner, String title, boolean modal,
                            GraphicsConfiguration gc) {
        super(owner.frame, title, modal, gc);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogEditProject(Dialog owner) throws HeadlessException {
        super(owner);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogEditProject(Dialog owner, boolean modal) throws
            HeadlessException {
        super(owner, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogEditProject(Dialog owner, String title) throws
            HeadlessException {
        super(owner, title);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogEditProject(Dialog owner, String title, boolean modal) throws
            HeadlessException {
        super(owner, title, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogEditProject(Dialog owner, String title, boolean modal,
                            GraphicsConfiguration gc) throws HeadlessException {
        super(owner, title, modal, gc);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getCal(Calendar c) {
        String strDay = Integer.toString(c.get(Calendar.DATE)),
               strMonth = Integer.toString(c.get(Calendar.MONTH)),
               strYear = Integer.toString(c.get(Calendar.YEAR));

        if (strDay.length() == 1) {
            strDay = "0" + strDay;
        }

        if (strMonth.length() == 1) {
            strMonth = "0" + strMonth;
        }

        return strDay + "." + strMonth  + "." + strYear;
    }

    public static Calendar toCal(String str) throws java.text.ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        try {
            sdf.parse(str);
            sdf.getCalendar().set(Calendar.MONTH, sdf.getCalendar().get(Calendar.MONTH) + 1);
        } catch (java.text.ParseException ex) {
            java.text.ParseException exthrow = new java.text.ParseException(ex.getLocalizedMessage(),ex.getErrorOffset());
            throw exthrow;
        }

        return sdf.getCalendar();
    }

    private void jbInit() throws Exception {
        jContentPane.setLayout(null);
        this.setContentPane(jContentPane);
        this.setSize(371, 120);
        btnCreate.setBounds(new Rectangle(9, 61, 173, 25));
        btnCreate.setSelected(true);
        btnCreate.setText("Änderungen speichern");
        btnCreate.addActionListener(new DialogEditProject_btnCreate_actionAdapter(this));
        btnCancel.setBounds(new Rectangle(249, 61, 104, 25));
        btnCancel.setText("Abbrechen");
        btnCancel.addActionListener(new DialogEditProject_btnCancel_actionAdapter(this));
        lblInstanceName.setAlignmentX((float) 0.5);
        lblInstanceName.setMaximumSize(new Dimension(34, 21));
        lblInstanceName.setMinimumSize(new Dimension(34, 21));
        lblInstanceName.setPreferredSize(new Dimension(34, 21));
        lblInstanceName.setText("Projektname:");
        lblInstanceName.setBounds(new Rectangle(10, 11, 108, 21));
        txtProjectName.setBounds(new Rectangle(123, 11, 138, 21));
        jContentPane.add(lblInstanceName, null);
        jContentPane.add(txtProjectName, null);
        jContentPane.add(btnCreate, null);
        jContentPane.add(btnCancel, null);
        Calendar cal = Calendar.getInstance();

        if (mainwindow != null) {
            JManager manager = mainwindow.getManager();

            if (manager != null && manager.getProject() != null) {
                 txtProjectName.setText(manager.getProject().getName());
            }
        }
    }

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    JPanel jContentPane = new JPanel();
    JButton btnCreate = new JButton();
    JButton btnCancel = new JButton();
    JLabel lblInstanceName = new JLabel();
    JTextField txtProjectName = new JTextField();
    public void btnCancel_actionPerformed(ActionEvent e) {
        dispose();
    }

    public void btnCreate_actionPerformed(ActionEvent e) {
        String strProjectName = txtProjectName.getText();

        if (strProjectName.length() == 0) {

            javax.swing.JOptionPane.showMessageDialog(this,
            "Bitte füllen Sie alle Felder aus.", "Falsche Eingabe", 2);

            return;
        }

        JManager manager = mainwindow.getManager() ;
        manager.getProject().setName(strProjectName);

        mainwindow.updateViews();

        dispose();
    }
}


class DialogEditProject_btnCreate_actionAdapter implements ActionListener {
    private DialogEditProject adaptee;
    DialogEditProject_btnCreate_actionAdapter(DialogEditProject adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCreate_actionPerformed(e);
    }
}


class DialogEditProject_btnCancel_actionAdapter implements ActionListener {
    private DialogEditProject adaptee;
    DialogEditProject_btnCancel_actionAdapter(DialogEditProject adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCancel_actionPerformed(e);
    }
}
