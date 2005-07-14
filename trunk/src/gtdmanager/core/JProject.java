package gtdmanager.core;

import java.util.*;

/**
 * <p>Title: JProject class</p>
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
public class JProject {

    int fontSize, sizeX, sizeY;
    String name, author;
    String version, unit;
    boolean modified;
    ArrayList instances;

    public String toString() {
        return name;
    }

    public JProject() {
        modified = false;
        fontSize = 8;
        sizeX = 8;
        sizeY = 8;
        unit = "cm";
        name = "";
        author = "";
        version = "";
        this.instances = new ArrayList();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String strName) {
        this.name = strName;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String strAuthor) {
        this.author = strAuthor;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String strVersion) {
        this.version = strVersion;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int size) {
        this.fontSize = size;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public void setSizeX(int size) {
        this.sizeX = size;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public void setSizeY(int size) {
        this.sizeY = size;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String strUnit) {
        this.unit = strUnit;
    }

    // gibt die nächst größere id nach der letzten instanz zurück
    private int getNextId() {
        if (instances.size() <= 0) {
            return 0; // keine instanz vorhanden
        } else {
            return ((JInstance)instances.get(instances.size()-1)).id + 1;
        } // gibt die id der letzten instanz + 1 zurück
    }

    private void copyActivityValues(JActivity sourceAct,
                                    JActivity destinationAct) {

        destinationAct.id = sourceAct.getId(); // selbe id in neuer instanz
        destinationAct.setName(sourceAct.getName());
        destinationAct.setShortName(sourceAct.getShortName());
        destinationAct.setColor(sourceAct.getColor());
        destinationAct.setStartDate(sourceAct.getStartDate());
        destinationAct.setEndDate(sourceAct.getEndDate());

        for (int i = 0; i < sourceAct.dependencies.size(); i++) {
            JDependency srcDep = (JDependency)sourceAct.dependencies.get(i);
            JDependency dstDep = new JDependency();

            dstDep.setToActivityId(srcDep.getToActivityId());
            dstDep.setDependencyType(srcDep.getDependencyType());
            dstDep.setColor(srcDep.getColor());
            dstDep.setLine(srcDep.getLine());
            destinationAct.dependencies.add(dstDep);
        }

    }

    private void cloneActivities(ArrayList sourceActs,
                                 ArrayList destinationActs) {

        if (sourceActs != null) {
            for (int i = 0; i < sourceActs.size(); i++) {

                JActivity srcAct = (JActivity)sourceActs.get(i);
                JActivity dstAct = new JActivity(); // neue activity anlegen

                copyActivityValues(srcAct, dstAct);
                // werte der activity mitsamt dependencies kopieren

                cloneActivities(srcAct.activities, dstAct.activities);
                // activities der activity klonen

                destinationActs.add(dstAct);
                // die neu erstellte, geklonte activity hinzufügen
            }
        }
    }

    public JInstance newEmptyInstance() {

        JInstance inst = new JInstance();
        inst.id = getNextId(); // nächst höhere id
        instances.add(inst);  // fügt die neue instanz der arraylist hinzu

        return inst;  // übergibt die neue leere instanz
    }

    public int newInstance() {

        JInstance inst = new JInstance();
        inst.id = getNextId(); // nächst höhere id

        if (instances.size() > 0) {
            JInstance lastInst = (JInstance)instances.get(instances.size() - 1);

            inst.setName(lastInst.getName());
            inst.setCreationDate(lastInst.getCreationDate());
            inst.setStartDate(lastInst.getStartDate());
            inst.setEndDate(lastInst.getEndDate());

            cloneActivities(lastInst.getActivities(), inst.getActivities());

        }

        instances.add(inst);  // fügt die neue instanz der arraylist hinzu

        inst.setActive(true);

        return inst.id;  // übergibt die id der neuen instanz
    }

    public int newInstance(String strName, Calendar calCreate,
                           Calendar calStart, Calendar calEnd, boolean active) {

        int i = newInstance();
        // erstellt neue instanz, wobei die letzte instanz geklont wird

        JInstance inst = getInstance(i);

        inst.setName(strName);
        inst.setCreationDate(calCreate);
        inst.setStartDate(calStart);
        inst.setEndDate(calEnd);
        inst.setActive(active);

        return inst.id;  // übergibt die id der neuen instanz
    }

    public ArrayList getInstances() {
	    return instances;
    }

    public JInstance getInstance(int id) {
        // gibt die instanz mit der übergebenen id zurück
        for (int i=0; i<this.instances.size(); i++) {
            JInstance inst = (JInstance)this.instances.get(i);
            if (inst.id == id) {
                return inst;
            }
        }
        return null;
    }

    public boolean deleteInstance(int id) {
        // löscht die instanz mit der übergebenen id
        JInstance inst = getInstance(id);
        if (inst != null) {
		return this.instances.remove(inst);
	  
        }
	return false;
    }

}
