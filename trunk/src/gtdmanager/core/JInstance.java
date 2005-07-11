package gtdmanager.core;

import java.util.*;

/**
 * <p>Title: JInstance class</p>
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
public class JInstance {

    String name;
    int id;
    Calendar creationDate, startDate, endDate;
    ArrayList activities;
    boolean active;

    public String toString() {
	return name;
    }


    public JInstance() {
        active = false;
        activities = new ArrayList();
        creationDate = Calendar.getInstance();
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
    }

    void setActive(boolean boolActive) {
        this.active = boolActive;
    }

    boolean isActive() {
        return this.active;
    }

    /*boolean deleteInstance() {
    //??? wird dies benötigt?
         } */

    public int getId() {
	return id;
    }

    // private function returns the next higher id after all activities
    private int getNextId(ArrayList actList, int beginId) {

        int retId = beginId;

        if (actList.size() > 0) {
            //return ((JActivity)this.activities.get(this.activities.size()-1)).id + 1;

            for (int actIdx = 0; actIdx < actList.size(); actIdx++) {

                JActivity act = (JActivity)actList.get(actIdx);
                // durchsucht alle in actList gegebenen activities nach größeren ids
                if (act.getId() >= retId) {
                    retId = act.getId() + 1;
                }

                // durchsucht auch die kinder der in actList gegebenen activities nach größeren ids
                retId = getNextId(act.activities, retId);
            }

        } // returns id + 1 of all activities
        return retId;
    }

    public int newActivity(ArrayList actList) {

        JActivity act = new JActivity();

        act.id = getNextId(activities, 0); // gets the next higher id
        // hier nicht actList sondern activities, da alle activities nach der größten id durchsucht werden müssen

        actList.add(act);  // adds the new activity to the arraylist

        return act.id;  // return id of the new activity
    }

    public int newActivity() {
        // creates an activity in this instance
        return newActivity(activities);
    }

    public int newActivity(ArrayList actList, String strName, String strShortName, Calendar calStart, Calendar calEnd, int color) {

        JActivity act = new JActivity();
        act.id = getNextId(activities, 0); // gets the next higher id

        act.setName(strName);
        act.setShortName(strShortName);
        act.setStartDate(calStart);
        act.setEndDate(calEnd);
        act.setColor(color);

        actList.add(act);  // adds the new activity to the arraylist

        return act.id;  // return id of the new activity
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

    private boolean deleteActivity(ArrayList actList, int id) {

        for (int i = 0; i < actList.size(); i++) {
            JActivity act = (JActivity)actList.get(i);
            if (act.getId() == id) {
                actList.remove(i);
                return true;
            } else {
                return deleteActivity(act.activities, id);
            }
        }
        return false;
    }

    public boolean deleteActivity(int id) {
        return deleteActivity(activities, id);
    }

    public void setName(String strName) {
        this.name = strName;
    }

    public String getName() {
        return this.name;
    }

    public void setCreationDate(Calendar calCreationDate) {
        this.creationDate = calCreationDate;
    }

    public Calendar getCreationDate() {
        return this.creationDate;
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

}

