package gtdmanager.gui;

import java.awt.*;

import gtdmanager.core.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

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
public class DialogNewInstance extends JDialog {
    MainWindow mainwindow = null;

    public DialogNewInstance() throws HeadlessException {
        super();

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewInstance(MainWindow owner) throws HeadlessException {
        super(owner.frame);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewInstance(MainWindow owner, boolean modal) throws
            HeadlessException {
        super(owner.frame, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewInstance(MainWindow owner, String title) throws HeadlessException {
        super(owner.frame, title);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewInstance(MainWindow owner, String title, boolean modal) throws
            HeadlessException {
        super(owner.frame, title, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewInstance(MainWindow owner, String title, boolean modal,
                            GraphicsConfiguration gc) {
        super(owner.frame, title, modal, gc);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewInstance(Dialog owner) throws HeadlessException {
        super(owner);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewInstance(Dialog owner, boolean modal) throws
            HeadlessException {
        super(owner, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewInstance(Dialog owner, String title) throws
            HeadlessException {
        super(owner, title);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewInstance(Dialog owner, String title, boolean modal) throws
            HeadlessException {
        super(owner, title, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewInstance(Dialog owner, String title, boolean modal,
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
               strMonth = Integer.toString(c.get(Calendar.MONTH) + 1),
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
        } catch (java.text.ParseException ex) {
            java.text.ParseException exthrow = new java.text.ParseException(ex.getLocalizedMessage(),ex.getErrorOffset());
            throw exthrow;
        }

        return sdf.getCalendar();
    }

    private void jbInit() throws Exception {
        jContentPane.setLayout(null);
        this.setContentPane(jContentPane);
        this.addWindowListener(new DialogNewInstance_this_windowAdapter(this));
        this.setSize(380, 210);
        btnCreate.setBounds(new Rectangle(10, 153, 138, 25));
        btnCreate.setSelected(true);
        btnCreate.setText("Instanz erstellen");
        btnCreate.addActionListener(new DialogNewInstance_btnCreate_actionAdapter(this));
        btnCancel.setBounds(new Rectangle(250, 153, 104, 25));
        btnCancel.setText("Abbrechen");
        btnCancel.addActionListener(new DialogNewInstance_btnCancel_actionAdapter(this));
        lblInstanceName.setAlignmentX((float) 0.5);
        lblInstanceName.setMaximumSize(new Dimension(34, 21));
        lblInstanceName.setMinimumSize(new Dimension(34, 21));
        lblInstanceName.setPreferredSize(new Dimension(34, 21));
        lblInstanceName.setText("Instanzname:");
        lblInstanceName.setBounds(new Rectangle(10, 11, 108, 21));
        lblInstanceStartDate.setAlignmentX((float) 0.5);
        lblInstanceStartDate.setMaximumSize(new Dimension(34, 21));
        lblInstanceStartDate.setMinimumSize(new Dimension(34, 21));
        lblInstanceStartDate.setPreferredSize(new Dimension(34, 21));
        lblInstanceStartDate.setText("Startdatum:");
        lblInstanceStartDate.setBounds(new Rectangle(10, 74, 108, 21));
        lblInstanceEndDate.setAlignmentX((float) 0.5);
        lblInstanceEndDate.setMaximumSize(new Dimension(34, 21));
        lblInstanceEndDate.setMinimumSize(new Dimension(34, 21));
        lblInstanceEndDate.setPreferredSize(new Dimension(34, 21));
        lblInstanceEndDate.setText("Enddatum:");
        lblInstanceEndDate.setBounds(new Rectangle(10, 105, 108, 21));
        txtInstanceStartDate.setText("");
        txtInstanceStartDate.setBounds(new Rectangle(123, 74, 82, 21));
        txtInstanceEndDate.setText("");
        txtInstanceEndDate.setBounds(new Rectangle(123, 105, 82, 21));
        lblInstanceCreationDate.setAlignmentX((float) 0.5);
        lblInstanceCreationDate.setMaximumSize(new Dimension(34, 21));
        lblInstanceCreationDate.setMinimumSize(new Dimension(34, 21));
        lblInstanceCreationDate.setPreferredSize(new Dimension(34, 21));
        lblInstanceCreationDate.setText("Erstellungsdatum:");
        lblInstanceCreationDate.setBounds(new Rectangle(10, 42, 108, 21));
        txtInstanceCreationDate.setBounds(new Rectangle(123, 42, 82, 21));
        txtInstanceName.setBounds(new Rectangle(123, 11, 138, 21));
        jContentPane.add(lblInstanceName, null);
        jContentPane.add(txtInstanceName, null);
        jContentPane.add(txtInstanceCreationDate, null);
        jContentPane.add(lblInstanceCreationDate, null);
        jContentPane.add(lblInstanceStartDate, null);
        jContentPane.add(txtInstanceStartDate, null);
        jContentPane.add(txtInstanceEndDate, null);
        jContentPane.add(lblInstanceEndDate, null);
        jContentPane.add(btnCreate, null);
        jContentPane.add(btnCancel, null);
        Calendar cal = Calendar.getInstance();
        txtInstanceCreationDate.setText(getCal(cal));
    }

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    JPanel jContentPane = new JPanel();
    JButton btnCreate = new JButton();
    JButton btnCancel = new JButton();
    JLabel lblInstanceName = new JLabel();
    JTextField txtInstanceName = new JTextField();
    JLabel lblInstanceStartDate = new JLabel();
    JLabel lblInstanceEndDate = new JLabel();
    JLabel lblInstanceCreationDate = new JLabel();
    JFormattedTextField txtInstanceStartDate = new JFormattedTextField(format);
    JFormattedTextField txtInstanceEndDate = new JFormattedTextField(format);
    JFormattedTextField txtInstanceCreationDate = new JFormattedTextField(format);

    public JInstance currentInstance = null;

    public void btnCancel_actionPerformed(ActionEvent e) {
        dispose();
    }

    public void btnCreate_actionPerformed(ActionEvent e) {
        String strInstanceName = txtInstanceName.getText(),
               strInstanceCreationDate = txtInstanceCreationDate.getText(),
               strInstanceStartDate = txtInstanceStartDate.getText(),
               strInstanceEndDate = txtInstanceEndDate.getText();

        if (strInstanceName.length() == 0) {

            javax.swing.JOptionPane.showMessageDialog(this,
            "Bitte füllen Sie alle Felder aus.", "Falsche Eingabe", 2);

            return;
        }

        if (strInstanceCreationDate.length() == 0 || strInstanceStartDate.length() == 0
            || strInstanceEndDate.length() == 0) {

            javax.swing.JOptionPane.showMessageDialog(this,
            "Bitte geben Sie ein Erstellungs-, Start- und Enddatum im folgenden Format (01.01.2005) ein.", "Falsche Eingabe", 2);

            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calCreate, calStart , calEnd;

        try {
            calCreate = toCal(strInstanceCreationDate);
            calStart = toCal(strInstanceStartDate);
            calEnd = toCal(strInstanceEndDate);

            if (calEnd.before(calStart) || calEnd.equals(calStart)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                "Das Enddatum darf nicht vor- oder auf dem Startdatum liegen.",
                "Falsche Eingabe", 2);
                return;
            }

            /*if (calEnd.after(currentInstance.getEndDate()) || calStart.before(currentInstance.getStartDate())) {
                javax.swing.JOptionPane.showMessageDialog(this,
                "Der Zeitraum der neuen Instanz muss im Zeitraum der alten Instanz liegen.\nVon " + getCal(firstInstance.getStartDate()) + " bis " + getCal(firstInstance.getEndDate()) + ".",
                "Falsche Eingabe", 2);
                return;
            }*/

        } catch (java.text.ParseException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
            "Bitte geben Sie ein Erstellungs-, Start- und Enddatum im " +
            "folgenden Format (01.01.2005) ein.", "Falsche Eingabe", 2);
            return;
        }

        JManager manager = mainwindow.getManager() ;
        manager.getProject().newInstance(strInstanceName, calCreate, calStart, calEnd, true);

        mainwindow.updateViews();

        dispose();
    }

    public void this_windowOpened(WindowEvent e) {
      if (currentInstance != null) {
        txtInstanceStartDate.setText(getCal(currentInstance.getStartDate()));
        txtInstanceEndDate.setText(getCal(currentInstance.getEndDate()));
      }
    }
}


class DialogNewInstance_this_windowAdapter extends WindowAdapter {
    private DialogNewInstance adaptee;
    DialogNewInstance_this_windowAdapter(DialogNewInstance adaptee) {
        this.adaptee = adaptee;
    }

    public void windowOpened(WindowEvent e) {
        adaptee.this_windowOpened(e);
    }
}


class DialogNewInstance_btnCreate_actionAdapter implements ActionListener {
    private DialogNewInstance adaptee;
    DialogNewInstance_btnCreate_actionAdapter(DialogNewInstance adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCreate_actionPerformed(e);
    }
}


class DialogNewInstance_btnCancel_actionAdapter implements ActionListener {
    private DialogNewInstance adaptee;
    DialogNewInstance_btnCancel_actionAdapter(DialogNewInstance adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCancel_actionPerformed(e);
    }
}
