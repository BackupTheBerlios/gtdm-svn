package gtdmanagercore;

import java.util.*;

/**
 * <p>Title: JActivity class</p>
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
public class JActivity {

    String name, shortName;
    int id;
    Calendar startDate, endDate;
    int color;
    ArrayList dependencies;
    // hier fehlen noch weitere Unteraktivitäten !!!

    public JActivity() {
        color = 0;
        dependencies = new ArrayList();
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
    }

    // private function returns the next higher id after the last dependency
    private int getNextId() {
        if (this.dependencies.size() <= 0) {
            return 0; // no existing dependency given
        } else {
            return ((JDependency)this.dependencies.get(this.dependencies.size()-1)).id + 1;
        } // returns id + 1 of the last existing dependency
    }

    int newDependency(int toActivityId, int dependencyType) {

        JDependency dep = new JDependency();
        dep.id = getNextId(); // gets the next higher id
        dep.setToActivityId(toActivityId);
        dep.setDependencyType(dependencyType);

        dependencies.add(dep);  // adds the new dependency to the arraylist

        return dep.id;  // return id of the new dependency
    }

    JDependency getDependency(int id) {

        for (int i=0; i<this.dependencies.size(); i++) {
            JDependency dep = (JDependency)this.dependencies.get(i);
            if (dep.id == id) {
                return dep;
            }
        }
        return null;
    }

    boolean deleteDependency(int id) {

        JDependency dep = getDependency(id);
        if (dep != null) {
            return this.dependencies.remove(dep);
        }
        return false;
    }

    String getName() {
        return this.name;
    }

    void setName(String strName) {
        this.name = strName;
    }

    String getShortName() {
        return this.shortName;
    }

    void setShortName(String strShortName) {
        this.shortName = strShortName;
    }

    void setStartDate(Calendar calStartDate) {
        this.startDate = calStartDate;
    }

    Calendar getStartDate() {
        return this.startDate;
    }

    void setEndDate(Calendar calEndDate) {
        this.endDate = calEndDate;
    }

    Calendar getEndDate() {
        return this.endDate;
    }

    int getColor() {
        return this.color;
    }

    void setColor(int col) {
        this.color = col;
    }

}
