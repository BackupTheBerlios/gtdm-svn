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
public class DialogNewActivity extends JDialog {
    MainWindow mainwindow = null;

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
        this.addWindowListener(new DialogNewActivity_this_windowAdapter(this));
        this.setSize(458, 452);
        btnCreate.setBounds(new Rectangle(8, 392, 138, 25));
        btnCreate.setSelected(true);
        btnCreate.setText("Aufgabe erstellen");
        btnCreate.addActionListener(new DialogNewActivity_btnCreate_actionAdapter(this));
        btnCancel.setBounds(new Rectangle(336, 392, 104, 25));
        btnCancel.setText("Abbrechen");
        btnCancel.addActionListener(new DialogNewActivity_btnCancel_actionAdapter(this));
        tabSettings.setBounds(new Rectangle(8, 12, 432, 372));
        Aufgabedaten.setLayout(null);
        txtName.setText("");
        txtName.setBounds(new Rectangle(122, 29, 168, 22));
        lblName.setAlignmentX((float) 0.5);
        lblName.setMaximumSize(new Dimension(34, 21));
        lblName.setMinimumSize(new Dimension(34, 21));
        lblName.setPreferredSize(new Dimension(34, 21));
        lblName.setText("Aufgabenname:");
        lblName.setBounds(new Rectangle(20, 29, 89, 22));
        lblEndDate.setAlignmentX((float) 0.5);
        lblEndDate.setMaximumSize(new Dimension(34, 21));
        lblEndDate.setMinimumSize(new Dimension(34, 21));
        lblEndDate.setPreferredSize(new Dimension(34, 21));
        lblEndDate.setText("Enddatum:");
        lblEndDate.setBounds(new Rectangle(20, 210, 89, 22));
        lblShortname.setAlignmentX((float) 0.5);
        lblShortname.setMaximumSize(new Dimension(34, 21));
        lblShortname.setMinimumSize(new Dimension(34, 21));
        lblShortname.setPreferredSize(new Dimension(34, 21));
        lblShortname.setText("Kurzname:");
        lblShortname.setBounds(new Rectangle(20, 89, 89, 22));
        lblStartDate.setAlignmentX((float) 0.5);
        lblStartDate.setMaximumSize(new Dimension(34, 21));
        lblStartDate.setMinimumSize(new Dimension(34, 21));
        lblStartDate.setPreferredSize(new Dimension(34, 21));
        lblStartDate.setText("Startdatum:");
        lblStartDate.setBounds(new Rectangle(20, 150, 89, 22));
        txtShortName.setBounds(new Rectangle(122, 89, 115, 22));
        Abhaengigkeiten.setLayout(null);
        optEndeEnde.setText("Ende - Ende");
        optEndeEnde.setBounds(new Rectangle(103, 85, 117, 23));
        optEndeAnfang.setText("Ende - Anfang");
        optEndeAnfang.setBounds(new Rectangle(217, 85, 117, 23));
        optAnfangEnde.setText("Anfang - Ende");
        optAnfangEnde.setBounds(new Rectangle(103, 115, 117, 23));
        optAnfangAnfang.setText("Anfang - Anfang");
        optAnfangAnfang.setBounds(new Rectangle(217, 115, 117, 23));
        jLabel1.setLabelFor(null);
        jLabel1.setText("Abhaengigkeitstyp:");
        jLabel1.setBounds(new Rectangle(15, 29, 304, 16));
        btnAdd.setBounds(new Rectangle(192, 227, 52, 27));
        btnAdd.setText(">>");
        btnRemove.setBounds(new Rectangle(192, 305, 52, 27));
        btnRemove.setText("<<");
        jLabel2.setText("Alle Aufgaben/Phasen:");
        jLabel2.setBounds(new Rectangle(15, 209, 146, 15));
        jLabel3.setText("Abhaengigkeit mit:");
        jLabel3.setBounds(new Rectangle(302, 210, 124, 15));
        lstAdded.setBorder(BorderFactory.createLoweredBevelBorder());
        lstAdded.setBounds(new Rectangle(302, 227, 116, 105));
        lstAdded.setSelectionModel(mdlSelected);
        lstActivities.setBorder(BorderFactory.createLoweredBevelBorder());
        lstActivities.setBounds(new Rectangle(15, 227, 116, 105));
        lstActivities.setSelectionModel(mdlActivities);
        Farbe.setMinimumSize(new Dimension(329, 338));
        Farbe.setPreferredSize(new Dimension(329, 338));
        txtStartDate.setBounds(new Rectangle(122, 150, 115, 22));
        txtEndDate.setBounds(new Rectangle(122, 210, 115, 22));
        jContentPane.add(btnCancel, null);
        jContentPane.add(btnCreate, null);
        jContentPane.add(tabSettings);

        Aufgabedaten.add(lblName, null);
        Aufgabedaten.add(txtName, null);
        Aufgabedaten.add(txtShortName, null);
        Aufgabedaten.add(lblShortname, null);
        Aufgabedaten.add(lblStartDate, null);
        Aufgabedaten.add(txtStartDate, null);
        Aufgabedaten.add(txtEndDate, null);
        Aufgabedaten.add(lblEndDate, null);
        Abhaengigkeiten.add(lstActivities, null);
        Abhaengigkeiten.add(jLabel2, null);
        Abhaengigkeiten.add(btnAdd, null);
        Abhaengigkeiten.add(jLabel3, null);
        Abhaengigkeiten.add(lstAdded, null);
        Abhaengigkeiten.add(btnRemove, null);
        Abhaengigkeiten.add(optAnfangEnde, null);
        Abhaengigkeiten.add(optEndeEnde, null);
        Abhaengigkeiten.add(optEndeAnfang, null);
        Abhaengigkeiten.add(optAnfangAnfang, null);
        Abhaengigkeiten.add(jLabel1, null);
        tabSettings.add(Aufgabedaten, "Aufgabedaten");
        tabSettings.add(Abhaengigkeiten, "Abh\344ngigkeiten");
        tabSettings.add(Farbe, "Farbe");
        tabSettings.setSelectedComponent(Aufgabedaten);
        Calendar cal = Calendar.getInstance();
        groupDependencies.add(optEndeEnde);
        groupDependencies.add(optEndeAnfang);
        groupDependencies.add(optAnfangAnfang);
        groupDependencies.add(optAnfangEnde);
    }

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    JPanel jContentPane = new JPanel();
    JButton btnCreate = new JButton();
    JButton btnCancel = new JButton();
    JTabbedPane tabSettings = new JTabbedPane();
    JPanel Aufgabedaten = new JPanel();
    JTextField txtName = new JTextField();
    JLabel lblName = new JLabel();
    JLabel lblEndDate = new JLabel();
    JFormattedTextField txtStartDate = new JFormattedTextField(format);
    JTextField txtShortName = new JTextField();
    JFormattedTextField txtEndDate = new JFormattedTextField(format);
    JLabel lblShortname = new JLabel();
    JLabel lblStartDate = new JLabel();
    JPanel Abhaengigkeiten = new JPanel();
    JRadioButton optEndeEnde = new JRadioButton();
    JRadioButton optEndeAnfang = new JRadioButton();
    JRadioButton optAnfangEnde = new JRadioButton();
    JRadioButton optAnfangAnfang = new JRadioButton();
    JLabel jLabel1 = new JLabel();
    JList lstActivities = new JList();
    JButton btnAdd = new JButton();
    JButton btnRemove = new JButton();
    JList lstAdded = new JList();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JColorChooser Farbe = new JColorChooser();
    ButtonGroup groupDependencies = new ButtonGroup();
    JInstance currentInstance = null;
    DefaultListSelectionModel mdlActivities = new DefaultListSelectionModel();
    DefaultListSelectionModel mdlSelected = new DefaultListSelectionModel();

    public void btnCancel_actionPerformed(ActionEvent e) {
        dispose();
    }

    public void btnCreate_actionPerformed(ActionEvent e) {
        String strStartDate = txtStartDate.getText(),
               strEndDate = txtEndDate.getText(),
               strName = txtName.getText(),
               strShortName = txtShortName.getText();

        if (strName.length() == 0 || strShortName.length() == 0) {

            tabSettings.setSelectedIndex(0);

            javax.swing.JOptionPane.showMessageDialog(this,
            "Bitte f�llen Sie alle Textfelder aus.", "Falsche Eingabe", 2);

            return;
        }

        if (strStartDate.length() == 0
            || strEndDate.length() == 0) {

            tabSettings.setSelectedIndex(0);

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

                tabSettings.setSelectedIndex(0);

                javax.swing.JOptionPane.showMessageDialog(this,
                "Das Enddatum darf nicht vor- oder auf dem Startdatum liegen.",
                "Falsche Eingabe", 2);
                return;
            }

            if (calEnd.after(currentInstance.getEndDate()) || calStart.before(currentInstance.getStartDate())) {

                tabSettings.setSelectedIndex(0);

                javax.swing.JOptionPane.showMessageDialog(this,
                "Der Zeitraum der neuen Aktivitaet muss im Gesamtzeitraum der letzten Instanz liegen.\nVon " + getCal(currentInstance.getStartDate()) + " bis " + getCal(currentInstance.getEndDate()) + ".",
                "Falsche Eingabe", 2);
                return;
            }

        } catch (java.text.ParseException ex) {

            tabSettings.setSelectedIndex(0);

            javax.swing.JOptionPane.showMessageDialog(this,
            "Bitte geben Sie ein Erstellungs-, Start- und Enddatum im " +
            "folgenden Format (01.01.2005) ein.", "Falsche Eingabe", 2);
            return;
        }

        Color col = Farbe.getColor();

        JManager manager = mainwindow.getManager() ;
        currentInstance.newActivity(currentInstance.getActivities(), strName, strShortName, calStart, calEnd, col.getRGB());

        mainwindow.updateViews();

        dispose();
    }

    public void this_windowOpened(WindowEvent e) {

    }
}


class DialogNewActivity_this_windowAdapter extends WindowAdapter {
    private DialogNewActivity adaptee;
    DialogNewActivity_this_windowAdapter(DialogNewActivity adaptee) {
        this.adaptee = adaptee;
    }

    public void windowOpened(WindowEvent e) {
        adaptee.this_windowOpened(e);
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
