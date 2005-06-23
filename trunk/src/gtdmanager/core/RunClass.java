package gtdmanager.core;

import java.util.*;

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
public class RunClass {
    static JManager man = null;

    public RunClass() {
        super();
    }

    public static void prtln(String s) {
        System.out.println(s);
    }

    public static void prtln(String s, int tabs) {
        for (int i = 1; i <= tabs; i++) {
            prt("  ");
        }
        System.out.println(s);
    }

    public static void prt(String s) {
        System.out.print(s);
    }

    public static String getCal(Calendar c) {
        return Integer.toString(c.get(Calendar.DATE)) + "." + Integer.toString(c.get(Calendar.MONTH)) + "." + Integer.toString(c.get(Calendar.YEAR));
    }

    public static void outputActivity(JActivity act, int tabs) {
        prtln("Activity { name = " + act.getName() + "; short-name = " + act.getShortName() + "; id = " + act.getId() + "; start-date = " + getCal(act.getStartDate()) + "; end-date = " + getCal(act.getEndDate()) + "; color = " + act.getColor(), tabs);

        int depCount = act.getDependencies().size();
        prtln("Dependency-Count = " + Integer.toString(depCount), tabs + 1);

        for (int i = 0; i < depCount; i++) {
            JDependency dep = act.getDependency(i);
            prtln("Dependency { id = " + dep.getId() + "; toActivityId = " + dep.getToActivityId() + "; dependencyType = " + dep.getDependencyTypeStr() + " }", tabs + 1);
        }

        int actCount = act.getActivities().size();
        prtln("Activity-Count = " + Integer.toString(actCount), tabs + 1);

        for (int i = 0; i < actCount; i++) {
            outputActivity(act.getActivity(i), tabs + 1);
        }

        prtln("}", tabs);

    }

    public static void outputInstance(JInstance inst) {
        prt("  Instance { name = " + inst.getName() + "; id = " + inst.getId() + "; creation-date = " + getCal(inst.getCreationDate()) + "; start-date = " + getCal(inst.getStartDate()) + "; end-date = " + getCal(inst.getEndDate()));
        if (inst.isActive()) { prtln("; is active");
        } else { prtln("; is not active"); }

        int actCount = inst.getActivities().size();
        prtln("Activity-Count = " + Integer.toString(actCount), 2);

        for (int i = 0; i < actCount; i++) {
            outputActivity(inst.getActivity(i), 2);
        }

        prtln("  }");
    }

    public static void outputProject() {
        prtln("outputProject:");
        prtln("Manager { font-size = " + Integer.toString(man.getFontSize()) + "; size-x = " + Integer.toString(man.getSizeX()) + "; size-y = " + Integer.toString(man.getSizeY()) + "; unit = " + man.getUnit() + " }");

        JProject proj = man.getProject();
        int instCount = proj.getInstances().size();
        prtln("  Instance-Count = " + Integer.toString(instCount));

        for (int i = 0; i < instCount; i++) {
            outputInstance(proj.getInstance(i));
        }

        prtln("end of outputProject");
    }

    public static void main(String[] args) {
        RunClass runclass = new RunClass();

        man = new JManager();
        //man.newProject();
        man.generateSampleProject();

        outputProject();
        //man.loadProject("c:\\example.xml");
        man.getProject().getInstance(1).getActivity(1).setName("A1EDIT");
        outputProject();

        //JProject project = man.getProject();
        System.out.println("Ende");
    }
}
