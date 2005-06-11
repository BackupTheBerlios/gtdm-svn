package gtdmanager.core;

import java.util.*;

/**
 * <p>Title: JManager class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Michael Fechter
 * @version 1.0
 */
public class JManager {

    int fontSize;
    String fileName;
    boolean modified;
    JProject project;

    public JManager() {
        modified = false;
        fontSize = 10;
        fileName = "";
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int size) {
        this.fontSize = size;
    }

    public JProject getProject() {
        return this.project;
    }

    public void loadProject(String fileName) {
        // loads project from xml-file
    }

    public void saveProject(String fileName) {
        // saves project in xml-file
    }

    public void newProject() {
        this.project = new JProject();
    }

    public void newProject(String strName, String strVersion) {
        this.project = new JProject();
        this.project.setName(strName);
        this.project.setVersion(strVersion);
    }

    public void generateSampleProject() {

        newProject("Testprojekt", "1.0.0.0");

        Calendar calCreate = Calendar.getInstance();
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calCreate.set(2005, 5, 1);
        calStart.set(2005, 5, 1);
        calEnd.set(2005, 6, 30);

        int newInstId = getProject().newInstance("Instanz1",
		calCreate, calStart, calEnd, true);
        JInstance inst = getProject().getInstance(newInstId);

        calStart = Calendar.getInstance();
        calEnd = Calendar.getInstance();
        calStart.set(2005, 5, 4);
        calEnd.set(2005, 5, 20);

        int actId1 = inst.newActivity("Aktivitaet1",
			"Akt1", calStart, calEnd, 0);
	
        JActivity act1 = inst.getActivity(actId1);

        calStart = Calendar.getInstance();
        calEnd = Calendar.getInstance();
        calStart.set(2005, 5, 22);
        calEnd.set(2005, 6, 3);

	act1.newActivity("Akt11: und noch tiefer","Akt11", calStart, calEnd, 0);

        int actId2 = inst.newActivity("Aktivitaet2",
			"Akt2", calStart, calEnd, 0);

        JActivity act2 = inst.getActivity(actId2);

        int depId = act1.newDependency(actId2, 2);
    }

}
