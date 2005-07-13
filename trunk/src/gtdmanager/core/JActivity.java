package gtdmanager.core;

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
    ArrayList activities = new ArrayList();


    public String toString() {
        return name;
    }

    public JActivity() {
        color = 0;
        dependencies = new ArrayList();
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
    }

    public int getId() {
	return id;
    }

    // gibt die nächst größere id nach der letzten dependency zurück
    private int getNextId() {
        if (dependencies.size() <= 0) {
            return 0; // keine dependency vorhanden
        } else {
            return ((JDependency)dependencies.get(dependencies.size()-1)).id+1;
        } // gibt die id der letzten dependency + 1 zurück
    }

    public int newDependency(int toActivityId, int dependencyType) {

        JDependency dep = new JDependency();
        dep.id = getNextId();
        dep.setToActivityId(toActivityId);
        dep.setDependencyType(dependencyType);

        dependencies.add(dep);  // fügt die neue dependency der arraylist hinzu

        return dep.id;  // übergibt die id der neuen dependency
    }

    public JDependency getDependency(int id) {
        // gibt die dependency mit der übergebenen id zurück
        for (int i=0; i<this.dependencies.size(); i++) {
            JDependency dep = (JDependency)this.dependencies.get(i);
            if (dep.id == id) {
                return dep;
            }
        }
        return null;
    }

    public ArrayList getDependencies() {
        return dependencies;
    }

    public boolean deleteDependency(int id) {
        // löscht die dependency mit der übergebenen id
        JDependency dep = getDependency(id);
        if (dep != null) {
            return this.dependencies.remove(dep);
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String strName) {
        this.name = strName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String strShortName) {
        this.shortName = strShortName;
    }

    public void setStartDate(Calendar calStartDate) {
        this.startDate = calStartDate;
    }

    public Calendar getStartDate() {
        return this.startDate;
    }

    public void setEndDate(Calendar calEndDate) {
        this.endDate = calEndDate;
    }

    public Calendar getEndDate() {
        return this.endDate;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int col) {
        this.color = col;
    }

    public ArrayList getActivities() {
        return activities;
    }

    public JActivity getActivity(int id) {

        for (int i=0; i<this.activities.size(); i++) {
            JActivity act = (JActivity)this.activities.get(i);
            if (act.id == id) {
                return act;
            } else {
                act = act.getActivity(id);
                if (act != null) {
                    return act;
                }
            }
        }
        return null;
    }

}
