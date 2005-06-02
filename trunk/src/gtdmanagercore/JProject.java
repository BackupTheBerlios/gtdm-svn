package gtdmanagercore;

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

    String name;
    String version;
    ArrayList instances;

    public JProject() {
        this.instances = new ArrayList();
    }

    String getName() {
        return this.name;
    }

    void setName(String strName) {
        this.name = strName;
    }

    String getVersion() {
        return this.version;
    }

    void setVersion(String strVersion) {
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

    int newInstance() {

        JInstance inst = new JInstance();
        inst.id = getNextId(); // gets the next higher id

        instances.add(inst);  // adds the new instance to the arraylist

        return inst.id;  // return id of the new instance
    }

    int newInstance(String strName, Calendar calCreate, Calendar calStart, Calendar calEnd, boolean active) {

        JInstance inst = new JInstance();
        inst.id = getNextId(); // gets the next higher id

        inst.setName(strName);
        inst.setCreationDate(calCreate);
        inst.setStartDate(calStart);
        inst.setEndDate(calEnd);
        inst.setActive(active);

        instances.add(inst);  // adds the new instance to the arraylist

        return inst.id;  // return id of the new instance
    }

    JInstance getInstance(int id) {

        for (int i=0; i<this.instances.size(); i++) {
            JInstance inst = (JInstance)this.instances.get(i);
            if (inst.id == id) {
                return inst;
            }
        }
        return null;
    }

    void deleteInstance(int id) {

        JInstance inst = getInstance(id);
        if (inst != null) {
            this.instances.remove(inst);
        }
    }

}
