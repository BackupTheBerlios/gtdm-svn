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
public class DialogAdminDependency extends JDialog {
    MainWindow mainwindow = null;

    public DialogAdminDependency() throws HeadlessException {
        super();

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogAdminDependency(MainWindow owner) throws HeadlessException {
        super(owner.frame);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogAdminDependency(MainWindow owner, boolean modal) throws
            HeadlessException {
        super(owner.frame, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogAdminDependency(MainWindow owner, String title) throws HeadlessException {
        super(owner.frame, title);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogAdminDependency(MainWindow owner, String title, boolean modal) throws
            HeadlessException {
        super(owner.frame, title, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogAdminDependency(MainWindow owner, String title, boolean modal,
                            GraphicsConfiguration gc) {
        super(owner.frame, title, modal, gc);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogAdminDependency(Dialog owner) throws HeadlessException {
        super(owner);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogAdminDependency(Dialog owner, boolean modal) throws
            HeadlessException {
        super(owner, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogAdminDependency(Dialog owner, String title) throws
            HeadlessException {
        super(owner, title);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogAdminDependency(Dialog owner, String title, boolean modal) throws
            HeadlessException {
        super(owner, title, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogAdminDependency(Dialog owner, String title, boolean modal,
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
        this.addWindowListener(new DialogAdminDependency_this_windowAdapter(this));
        this.setSize(458, 452);
        btnCancel.setBounds(new Rectangle(336, 392, 104, 25));
        btnCancel.setText("Abbrechen");
        btnCancel.addActionListener(new DialogAdminDependency_btnCancel_actionAdapter(this));
        tabSettings.setBounds(new Rectangle(8, 12, 432, 372));
        Erstellen.setLayout(null);
        Loeschen.setLayout(null);
        lstDependencies.setBackground(Color.white);
        lstDependencies.setBorder(null);
        lstDependencies.setModel(mdlDependenciesNames);
        lblFrom.setAlignmentX((float) 0.5);
        lblFrom.setMaximumSize(new Dimension(34, 21));
        lblFrom.setMinimumSize(new Dimension(34, 21));
        lblFrom.setPreferredSize(new Dimension(34, 21));
        lblFrom.setText("Zu Aufgabe:");
        lblFrom.setBounds(new Rectangle(15, 68, 172, 22));
        cmbTo.setModel(mdlActivitiesTo);
        cmbTo.setBounds(new Rectangle(186, 68, 168, 22));
        optAnfangEnde.setText("Anfang - Ende");
        optAnfangEnde.setBounds(new Rectangle(103, 209, 117, 23));
        lblType.setText("Abhaengigkeitstyp:");
        lblType.setBounds(new Rectangle(15, 147, 304, 16));
        optEndeAnfang.setText("Ende - Anfang");
        optEndeAnfang.setBounds(new Rectangle(217, 179, 117, 23));
        optAnfangAnfang.setText("Anfang - Anfang");
        optAnfangAnfang.setBounds(new Rectangle(217, 209, 117, 23));
        optEndeEnde.setText("Ende - Ende");
        optEndeEnde.setBounds(new Rectangle(103, 179, 117, 23));
        lblTo.setAlignmentX((float) 0.5);
        lblTo.setMaximumSize(new Dimension(34, 21));
        lblTo.setMinimumSize(new Dimension(34, 21));
        lblTo.setPreferredSize(new Dimension(34, 21));
        lblTo.setText("Abhhaengigkeit von Aufgabe:");
        lblTo.setBounds(new Rectangle(15, 37, 172, 22));
        cmbFrom.setModel(mdlActivitiesFrom);
        cmbFrom.setBounds(new Rectangle(186, 37, 168, 22));
        btnCreate.setBounds(new Rectangle(15, 307, 230, 25));
        btnCreate.setText("Neue Abhaengigkeit erstellen");
        btnCreate.addActionListener(new
                DialogAdminDependency_btnCreate_actionAdapter(this));
        lblExisting.setText("Bestehende Abhaengigkeiten:");
        lblExisting.setBounds(new Rectangle(15, 17, 173, 15));
        btnDelete.setBounds(new Rectangle(15, 307, 231, 25));
        btnDelete.setText("Markierte Abhaengigkeit loeschen");
        btnDelete.addActionListener(new
                DialogAdminDependency_btnDelete_actionAdapter(this));
        scrollExisting.setBounds(new Rectangle(15, 33, 396, 81));
        jContentPane.add(btnCancel, null);
        jContentPane.add(tabSettings);

        Erstellen.add(btnCreate);
        Erstellen.add(lblTo);
        Erstellen.add(lblFrom);
        Erstellen.add(cmbFrom);
        Erstellen.add(cmbTo);
        Erstellen.add(optEndeEnde);
        Erstellen.add(lblType);
        Erstellen.add(optAnfangEnde);
        Erstellen.add(optAnfangAnfang);
        Erstellen.add(optEndeAnfang);
        Loeschen.add(lblExisting);
        Loeschen.add(btnDelete);
        Loeschen.add(scrollExisting);
        scrollExisting.getViewport().add(lstDependencies);
        tabSettings.add(Erstellen, "Abhaengigkeiten erstellen");
        tabSettings.add(Loeschen, "Abhaengigkeiten loeschen");
        tabSettings.setSelectedComponent(Erstellen);
        Calendar cal = Calendar.getInstance();
        groupDependencyTypes.add(optEndeEnde);
        groupDependencyTypes.add(optEndeAnfang);
        groupDependencyTypes.add(optAnfangAnfang);
        groupDependencyTypes.add(optAnfangEnde);
    }

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    JPanel jContentPane = new JPanel();
    JButton btnCancel = new JButton();
    JTabbedPane tabSettings = new JTabbedPane();
    JPanel Erstellen = new JPanel();
    JPanel Loeschen = new JPanel();
    JList lstDependencies = new JList();
    ButtonGroup groupDependencies = new ButtonGroup();
    JLabel lblFrom = new JLabel();
    JComboBox cmbTo = new JComboBox();
    JRadioButton optAnfangEnde = new JRadioButton();
    JLabel lblType = new JLabel();
    JRadioButton optEndeAnfang = new JRadioButton();
    JRadioButton optAnfangAnfang = new JRadioButton();
    JRadioButton optEndeEnde = new JRadioButton();
    JLabel lblTo = new JLabel();
    JComboBox cmbFrom = new JComboBox();
    JButton btnCreate = new JButton();
    JLabel lblExisting = new JLabel();
    JButton btnDelete = new JButton();
    JScrollPane scrollExisting = new JScrollPane();

    public DefaultListModel mdlDependenciesNames = new DefaultListModel();
    public DefaultListModel mdlDependenciesObjects = new DefaultListModel();
    public DefaultComboBoxModel mdlActivitiesFrom = new DefaultComboBoxModel();
    public DefaultComboBoxModel mdlActivitiesTo = new DefaultComboBoxModel();
    public JInstance currentInstance = null;
    ButtonGroup groupDependencyTypes = new ButtonGroup();

    public void btnCancel_actionPerformed(ActionEvent e) {
        dispose();
    }

    public void this_windowOpened(WindowEvent e) {
        cmbFrom.setSelectedIndex(-1);
        cmbTo.setSelectedIndex(-1);
    }

    public void btnCreate_actionPerformed(ActionEvent e) {

        if (cmbFrom.getSelectedIndex() == -1 || cmbTo.getSelectedIndex() == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
            "Bitte waehlen Sie eine Start- und Endaufgabe aus.", "Falsche Eingabe", 2);
            return;
        }

        if (cmbFrom.getSelectedItem() == cmbTo.getSelectedItem()) {
            javax.swing.JOptionPane.showMessageDialog(this,
            "Sie koennen nicht zweimal die gleiche Aufgabe auswaehlen.", "Falsche Eingabe", 2);
            return;
        }

        boolean bChecked = false;
        int nType = JDependency.BEGINBEGIN;

        if (optAnfangAnfang.isSelected() == true) {
            nType = JDependency.BEGINBEGIN;
            bChecked = true;
        }

        if (optAnfangEnde.isSelected() == true) {
            nType = JDependency.BEGINEND;
            bChecked = true;
        }

        if (optEndeAnfang.isSelected() == true) {
            nType = JDependency.ENDBEGIN;
            bChecked = true;
        }

        if (optEndeEnde.isSelected() == true) {
            nType = JDependency.ENDEND;
            bChecked = true;
        }

        if (bChecked == false) {
            javax.swing.JOptionPane.showMessageDialog(this,
            "Sie muessen einen Abhaengigkeitstyp auswaehlen.", "Falsche Eingabe", 2);
            return;
        }

        JActivity actFrom = (JActivity)cmbFrom.getSelectedItem();
        JActivity actTo = (JActivity)cmbTo.getSelectedItem();

        int nNewId = actFrom.newDependency(actTo.getId(), nType);
        JDependency depNew = actFrom.getDependency(nNewId);

        mainwindow.updateViews();

        String str = "Abhaengigkeit " + actFrom.getName() + " zu " + actTo.getName();
        mdlDependenciesNames.addElement((String)str);
        mdlDependenciesObjects.addElement(depNew);

        cmbFrom.setSelectedIndex(-1);
        cmbTo.setSelectedIndex(-1);
        optAnfangAnfang.setSelected(false);
        optAnfangEnde.setSelected(false);
        optEndeAnfang.setSelected(false);
        optEndeEnde.setSelected(false);

        javax.swing.JOptionPane.showMessageDialog(this,
        "Die Abhaengigkeit wurde erfolgreich erstellt.",
        "Abhaengigkeit erstellen", 1);
    }

    public void btnDelete_actionPerformed(ActionEvent e) {

        String str = (String)lstDependencies.getSelectedValue();
        int nItem = mdlDependenciesNames.indexOf(str);

        if (nItem == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
            "Bitte markieren Sie eine Abhaengigkeit aus der Liste.", "Keine Abhaengigkeit markiert", 2);
            return;
        }

        JDependency selDep = (JDependency)mdlDependenciesObjects.elementAt(nItem);

        String [] optionen = {"Ja", "Nein"};
        int wahl = javax.swing.JOptionPane.showOptionDialog(
                   this,  "Sind Sie sicher, dass Sie die markierte Abhaengigkeit \"" + str + "\"loeschen wollen?",
                   "Abhaengigkeit loeschen",
                   javax.swing.JOptionPane.YES_NO_OPTION,
                   javax.swing.JOptionPane.QUESTION_MESSAGE,
                   null,
                   optionen, optionen[0]);

        if (wahl == javax.swing.JOptionPane.YES_OPTION) {
            currentInstance.deleteDependencies(selDep.getToActivityId());

            mdlDependenciesNames.remove(nItem);
            mdlDependenciesObjects.removeElement(selDep);
            mainwindow.updateViews();
        }
    }
}


class DialogAdminDependency_btnDelete_actionAdapter implements ActionListener {
    private DialogAdminDependency adaptee;
    DialogAdminDependency_btnDelete_actionAdapter(DialogAdminDependency adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnDelete_actionPerformed(e);
    }
}


class DialogAdminDependency_btnCreate_actionAdapter implements ActionListener {
    private DialogAdminDependency adaptee;
    DialogAdminDependency_btnCreate_actionAdapter(DialogAdminDependency adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCreate_actionPerformed(e);
    }
}


class DialogAdminDependency_this_windowAdapter extends WindowAdapter {
    private DialogAdminDependency adaptee;
    DialogAdminDependency_this_windowAdapter(DialogAdminDependency adaptee) {
        this.adaptee = adaptee;
    }

    public void windowOpened(WindowEvent e) {
        adaptee.this_windowOpened(e);
    }
}


class DialogAdminDependency_btnCancel_actionAdapter implements ActionListener {
    private DialogAdminDependency adaptee;
    DialogAdminDependency_btnCancel_actionAdapter(DialogAdminDependency adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCancel_actionPerformed(e);
    }
}
