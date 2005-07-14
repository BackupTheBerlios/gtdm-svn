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
public class DialogViewSettings extends JDialog {
    MainWindow mainwindow = null;

    public DialogViewSettings() throws HeadlessException {
        super();

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogViewSettings(MainWindow owner) throws HeadlessException {
        super(owner.frame);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogViewSettings(MainWindow owner, boolean modal) throws
            HeadlessException {
        super(owner.frame, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogViewSettings(MainWindow owner, String title) throws HeadlessException {
        super(owner.frame, title);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogViewSettings(MainWindow owner, String title, boolean modal) throws
            HeadlessException {
        super(owner.frame, title, modal);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogViewSettings(MainWindow owner, String title, boolean modal,
                            GraphicsConfiguration gc) {
        super(owner.frame, title, modal, gc);

        mainwindow = owner;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogViewSettings(Dialog owner) throws HeadlessException {
        super(owner);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogViewSettings(Dialog owner, boolean modal) throws
            HeadlessException {
        super(owner, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogViewSettings(Dialog owner, String title) throws
            HeadlessException {
        super(owner, title);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogViewSettings(Dialog owner, String title, boolean modal) throws
            HeadlessException {
        super(owner, title, modal);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogViewSettings(Dialog owner, String title, boolean modal,
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
        this.addWindowListener(new DialogViewSettings_this_windowAdapter(this));
        this.setSize(458, 452);
        btnCancel.setBounds(new Rectangle(336, 392, 104, 25));
        btnCancel.setText("Abbrechen");
        btnCancel.addActionListener(new DialogViewSettings_btnCancel_actionAdapter(this));
        btnOk.setBounds(new Rectangle(17, 392, 100, 25));
        btnOk.setText("OK");
        jLabel1.setText("Anzeigeeinstellungen:");
        jLabel1.setBounds(new Rectangle(17, 23, 246, 15));
        lblFrom.setText("Von Datum:");
        lblFrom.setBounds(new Rectangle(50, 53, 80, 22));
        jLabel2.setText("Bis Datum:");
        jLabel2.setBounds(new Rectangle(50, 83, 86, 22));
        txtStartDate.setText("");
        txtStartDate.setBounds(new Rectangle(146, 53, 97, 22));
        txtEndDate.setBounds(new Rectangle(146, 83, 97, 22));
        txtFontSize.setText("16");
        txtFontSize.setBounds(new Rectangle(153, 153, 40, 22));
        lblFontsize.setText("Schriftgroesse:");
        lblFontsize.setBounds(new Rectangle(50, 153, 90, 22));
        lblMasse.setText("Dimensionen:");
        lblMasse.setBounds(new Rectangle(17, 217, 246, 15));
        txtWidth.setText("16");
        txtWidth.setBounds(new Rectangle(153, 251, 40, 22));
        lblWidth.setText("Breite:");
        lblWidth.setBounds(new Rectangle(50, 251, 90, 22));
        lblcm.setText("cm");
        lblcm.setBounds(new Rectangle(205, 251, 34, 22));
        lblHeight.setText("Hoehe:");
        lblHeight.setBounds(new Rectangle(50, 282, 90, 22));
        txtHeight.setText("16");
        txtHeight.setBounds(new Rectangle(153, 280, 40, 22));
        lblcm2.setText("cm");
        lblcm2.setBounds(new Rectangle(205, 280, 34, 22));
        lblDpi.setText("DotsPerInch:");
        lblDpi.setBounds(new Rectangle(50, 323, 90, 22));
        txtDpi.setText("300");
        txtDpi.setBounds(new Rectangle(153, 323, 40, 22));
        jContentPane.add(btnCancel, null);
        jContentPane.add(btnOk);
        jContentPane.add(jLabel1);
        jContentPane.add(lblFrom);
        jContentPane.add(jLabel2);
        jContentPane.add(txtEndDate);
        jContentPane.add(txtStartDate);
        jContentPane.add(lblMasse);
        jContentPane.add(lblFontsize);
        jContentPane.add(lblWidth);
        jContentPane.add(txtFontSize);
        jContentPane.add(txtWidth);
        jContentPane.add(lblcm);
        jContentPane.add(txtHeight);
        jContentPane.add(lblcm2);
        jContentPane.add(lblHeight);
        jContentPane.add(lblDpi);
        jContentPane.add(txtDpi);
        Calendar cal = Calendar.getInstance();
    }

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    JPanel jContentPane = new JPanel();
    JButton btnCancel = new JButton();
    JButton btnOk = new JButton();
    JLabel jLabel1 = new JLabel();
    JLabel lblFrom = new JLabel();
    JLabel jLabel2 = new JLabel();
    JFormattedTextField txtStartDate = new JFormattedTextField();
    JFormattedTextField txtEndDate = new JFormattedTextField();
    JTextField txtFontSize = new JTextField();
    JLabel lblFontsize = new JLabel();
    JLabel lblMasse = new JLabel();
    JTextField txtWidth = new JTextField();
    JLabel lblWidth = new JLabel();
    JLabel lblcm = new JLabel();
    JLabel lblHeight = new JLabel();
    JTextField txtHeight = new JTextField();
    JLabel lblcm2 = new JLabel();
    JLabel lblDpi = new JLabel();
    JTextField txtDpi = new JTextField();
    public void btnCancel_actionPerformed(ActionEvent e) {
        dispose();
    }

    public void this_windowOpened(WindowEvent e) {
        JInstance firstInstance = (JInstance)mainwindow.manager.getProject().getInstance(0);
        txtStartDate.setText(getCal(firstInstance.getStartDate()));
        txtEndDate.setText(getCal(firstInstance.getEndDate()));

        txtWidth.setText(Integer.toString(mainwindow.getDiagramView().getWidth()));
        txtHeight.setText(Integer.toString(mainwindow.getDiagramView().getHeight()));
      }
}


class DialogViewSettings_this_windowAdapter extends WindowAdapter {
    private DialogViewSettings adaptee;
    DialogViewSettings_this_windowAdapter(DialogViewSettings adaptee) {
        this.adaptee = adaptee;
    }

    public void windowOpened(WindowEvent e) {
        adaptee.this_windowOpened(e);
    }
}


class DialogViewSettings_btnCancel_actionAdapter implements ActionListener {
    private DialogViewSettings adaptee;
    DialogViewSettings_btnCancel_actionAdapter(DialogViewSettings adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCancel_actionPerformed(e);
    }
}
