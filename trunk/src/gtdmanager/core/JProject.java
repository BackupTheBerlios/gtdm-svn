package gtdmanager.core;

import java.*;
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

    String name, author;
    String version;
    ArrayList instances;

    public JProject() {
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

    // private function returns the next higher id after the last instance
    private int getNextId() {
        if (this.instances.size() <= 0) {
            return 0; // no existing instance given
        } else {
            return ((JInstance)this.instances.get(this.instances.size()-1)).id + 1;
        } // returns id + 1 of the last existing instance
    }

    private void copyActivityValues(JActivity sourceAct, JActivity destinationAct) {

        destinationAct.id = sourceAct.getId(); // same id in new instance
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

            destinationAct.dependencies.add(dstDep);
        }

    }

    private void cloneActivities(ArrayList sourceActs, ArrayList destinationActs) {

        if (sourceActs != null) {
            for (int i = 0; i < sourceActs.size(); i++) {

                JActivity srcAct = (JActivity)sourceActs.get(i);
                JActivity dstAct = new JActivity(); // neue activity anlegen

                copyActivityValues(srcAct, dstAct); // werte der activity mitsamt dependencies kopieren
                cloneActivities(srcAct.activities, dstAct.activities); // activities der activity klonen

                destinationActs.add(dstAct); // die neu erstellte, geklonte activity hinzufügen
            }
        }
    }

    public JInstance newEmptyInstance() {

        JInstance inst = new JInstance();
        inst.id = getNextId(); // gets the next higher id
        instances.add(inst);  // adds the new instance to the arraylist

        return inst;  // return the new empty instance
    }

    public int newInstance() {

        JInstance inst = new JInstance();
        inst.id = getNextId(); // gets the next higher id

        if (this.instances.size() > 0) {
            JInstance lastInst = (JInstance)this.instances.get(this.instances.size()-1);

            inst.setName(lastInst.getName());
            inst.setCreationDate(lastInst.getCreationDate());
            inst.setStartDate(lastInst.getStartDate());
            inst.setEndDate(lastInst.getEndDate());

            cloneActivities(lastInst.getActivities(), inst.getActivities());

        }

        instances.add(inst);  // adds the new instance to the arraylist

        inst.setActive(true);

        return inst.id;  // return id of the new instance
    }

    public int newInstance(String strName, Calendar calCreate, Calendar calStart, Calendar calEnd, boolean active) {

        int i = newInstance(); // creates new instance (clones the last instance)

        JInstance inst = getInstance(i);

        inst.setName(strName);
        inst.setCreationDate(calCreate);
        inst.setStartDate(calStart);
        inst.setEndDate(calEnd);
        inst.setActive(active);

        return inst.id;  // return id of the new instance
    }

    public ArrayList getInstances() {
	    return instances;
    }

    public JInstance getInstance(int id) {

        for (int i=0; i<this.instances.size(); i++) {
            JInstance inst = (JInstance)this.instances.get(i);
            if (inst.id == id) {
                return inst;
            }
        }
        return null;
    }

    public void deleteInstance(int id) {

        JInstance inst = getInstance(id);
        if (inst != null) {
            this.instances.remove(inst);
        }
    }

}
