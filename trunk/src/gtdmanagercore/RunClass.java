package gtdmanagercore;

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

    public static void main(String[] args) {
        RunClass runclass = new RunClass();

        man = new JManager();
        man.generateSampleProject();

        JProject project = man.getProject();
        System.out.println("Ende");
    }
}
