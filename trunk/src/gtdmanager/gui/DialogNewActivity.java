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
public class DialogNewActivity extends JDialog {
    MainWindow mainwindow = null;
    JInstance currentInstance = null;

    public DialogNewActivity() throws HeadlessException {
        super();

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewActivity(MainWindow owner) throws HeadlessException {
        super(owner.frame);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewActivity(MainWindow owner, boolean modal) throws
            HeadlessException {
        super(owner.frame, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewActivity(MainWindow owner, String title) throws HeadlessException {
        super(owner.frame, title);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewActivity(MainWindow owner, String title, boolean modal) throws
            HeadlessException {
        super(owner.frame, title, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewActivity(MainWindow owner, String title, boolean modal,
                            GraphicsConfiguration gc) {
        super(owner.frame, title, modal, gc);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewActivity(Dialog owner) throws HeadlessException {
        super(owner);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewActivity(Dialog owner, boolean modal) throws
            HeadlessException {
        super(owner, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewActivity(Dialog owner, String title) throws
            HeadlessException {
        super(owner, title);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewActivity(Dialog owner, String title, boolean modal) throws
            HeadlessException {
        super(owner, title, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogNewActivity(Dialog owner, String title, boolean modal,
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
        this.setSize(380, 210);
        btnCreate.setBounds(new Rectangle(10, 153, 138, 25));
        btnCreate.setSelected(true);
        btnCreate.setText("Aktivitaet erstellen");
        btnCreate.addActionListener(new DialogNewActivity_btnCreate_actionAdapter(this));
        btnCancel.setBounds(new Rectangle(250, 153, 104, 25));
        btnCancel.setText("Abbrechen");
        btnCancel.addActionListener(new DialogNewActivity_btnCancel_actionAdapter(this));
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
        lblInstanceCreationDate.setText("Kurzname:");
        lblInstanceCreationDate.setBounds(new Rectangle(10, 42, 108, 21));
        txtInstanceName.setBounds(new Rectangle(123, 11, 138, 21));
        txtShortname.setText("");
        txtShortname.setBounds(new Rectangle(123, 42, 82, 21));
        jContentPane.add(lblInstanceName, null);
        jContentPane.add(txtInstanceName, null);
        jContentPane.add(lblInstanceCreationDate, null);
        jContentPane.add(lblInstanceStartDate, null);
        jContentPane.add(txtInstanceStartDate, null);
        jContentPane.add(txtInstanceEndDate, null);
        jContentPane.add(lblInstanceEndDate, null);
        jContentPane.add(btnCreate, null);
        jContentPane.add(btnCancel, null);
        jContentPane.add(txtShortname);
        Calendar cal = Calendar.getInstance();
        txtInstanceStartDate.setText(getCal(currentInstance.getStartDate()));
        txtInstanceEndDate.setText(getCal(currentInstance.getEndDate()));
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
    JTextField txtShortname = new JTextField();
    public void btnCancel_actionPerformed(ActionEvent e) {
        dispose();
    }

    public void btnCreate_actionPerformed(ActionEvent e) {
        String strName = txtInstanceName.getText(),
               strShortname = txtShortname.getText(),
               strStartDate = txtInstanceStartDate.getText(),
               strEndDate = txtInstanceEndDate.getText();

        if (strName.length() == 0 || strShortname.length() == 0) {

            javax.swing.JOptionPane.showMessageDialog(this,
            "Bitte füllen Sie alle Felder aus.", "Falsche Eingabe", 2);

            return;
        }

        if (strStartDate.length() == 0
            || strEndDate.length() == 0) {

            javax.swing.JOptionPane.showMessageDialog(this,
            "Bitte geben Sie ein Erstellungs-, Start- und Enddatum im folgenden Format (01.01.2005) ein.", "Falsche Eingabe", 2);

            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calCreate, calStart , calEnd;

        try {
            calStart = toCal(strStartDate);
            calEnd = toCal(strEndDate);

            if (calEnd.before(calStart) || calEnd.equals(calStart)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                "Das Enddatum darf nicht vor- oder auf dem Startdatum liegen.",
                "Falsche Eingabe", 2);
                return;
            }

            if (calEnd.after(currentInstance.getEndDate()) || calStart.before(currentInstance.getStartDate())) {
                javax.swing.JOptionPane.showMessageDialog(this,
                "Der Zeitraum der neuen Aktivitaet muss im Gesamtzeitraum der letzten Instanz liegen.\nVon " + getCal(currentInstance.getStartDate()) + " bis " + getCal(currentInstance.getEndDate()) + ".",
                "Falsche Eingabe", 2);
                return;
            }

        } catch (java.text.ParseException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
            "Bitte geben Sie ein Erstellungs-, Start- und Enddatum im " +
            "folgenden Format (01.01.2005) ein.", "Falsche Eingabe", 2);
            return;
        }

        JManager manager = mainwindow.getManager() ;
        currentInstance.newActivity(null, strName, strShortname, calStart, calEnd, 0);

        mainwindow.updateViews();

        dispose();
    }
}


class DialogNewActivity_btnCreate_actionAdapter implements ActionListener {
    private DialogNewActivity adaptee;
    DialogNewActivity_btnCreate_actionAdapter(DialogNewActivity adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCreate_actionPerformed(e);
    }
}


class DialogNewActivity_btnCancel_actionAdapter implements ActionListener {
    private DialogNewActivity adaptee;
    DialogNewActivity_btnCancel_actionAdapter(DialogNewActivity adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCancel_actionPerformed(e);
    }
}
