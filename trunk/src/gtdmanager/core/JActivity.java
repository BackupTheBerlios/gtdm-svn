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



    public JActivity() {
        color = 0;
        dependencies = new ArrayList();
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
    }

    public int getId() {
	return id;
    }

    // private function returns the next higher id after the last dependency
    private int getNextId() {
        if (this.dependencies.size() <= 0) {
            return 0; // no existing dependency given
        } else {
            return ((JDependency)this.dependencies.get(this.dependencies.size()-1)).id + 1;
        } // returns id + 1 of the last existing dependency
    }

    public int newDependency(int toActivityId, int dependencyType) {

        JDependency dep = new JDependency();
        dep.id = getNextId(); // gets the next higher id
        dep.setToActivityId(toActivityId);
        dep.setDependencyType(dependencyType);

        dependencies.add(dep);  // adds the new dependency to the arraylist

        return dep.id;  // return id of the new dependency
    }

    public JDependency getDependency(int id) {

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

/*
    // private function returns the next higher id after the last activity
    private int getNextActivityId() {
        if (this.activities.size() <= 0) {
            return 0; // no existing activity given
        } else {
            return ((JActivity)this.activities.get(this.activities.size()-1)).id + 1;
        } // returns id + 1 of the last existing activity
    }

// nicht erlaubt, da newActivity aus der Instanz aufgerufen werden muss (unter Angabe der ArrayList, zu welcher hinzugefügt werden soll)
    int newActivity() {

        JActivity act = new JActivity();
        act.id = getNextActivityId(); // gets the next higher id

        activities.add(act);  // adds the new activity to the arraylist

        return act.id;  // return id of the new activity
    }

    int newActivity(String strName, String strShortName, Calendar calStart, Calendar calEnd, int color) {

        JActivity act = new JActivity();
        act.id = getNextActivityId(); // gets the next higher id

        act.setName(strName);
        act.setShortName(strShortName);
        act.setStartDate(calStart);
        act.setEndDate(calEnd);
        act.setColor(color);

        activities.add(act);  // adds the new activity to the arraylist

        return act.id;  // return id of the new activity
    }
*/

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
/*
// wird ebenfalls aus der Instanz aufgerufen
    public boolean deleteActivity(int id) {

        JActivity act = getActivity(id);
        if (act != null) {
            return this.activities.remove(act);
        }
        return false;
    }
*/


}
