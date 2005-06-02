package gtdmanagercore;

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

    // private function returns the next higher id after the last activity
    private int getNextId() {
        if (this.activities.size() <= 0) {
            return 0; // no existing activity given
        } else {
            return ((JActivity)this.activities.get(this.activities.size()-1)).id + 1;
        } // returns id + 1 of the last existing activity
    }

    int newActivity() {

        JActivity act = new JActivity();
        act.id = getNextId(); // gets the next higher id

        activities.add(act);  // adds the new activity to the arraylist

        return act.id;  // return id of the new activity
    }

    int newActivity(String strName, String strShortName, Calendar calStart, Calendar calEnd, int color) {

        JActivity act = new JActivity();
        act.id = getNextId(); // gets the next higher id

        act.setName(strName);
        act.setShortName(strShortName);
        act.setStartDate(calStart);
        act.setEndDate(calEnd);
        act.setColor(color);

        activities.add(act);  // adds the new activity to the arraylist

        return act.id;  // return id of the new activity
    }

    JActivity getActivity(int id) {
        for (int i=0; i<this.activities.size(); i++) {
            JActivity act = (JActivity)this.activities.get(i);
            if (act.id == id) {
                return act;
            }
        }
        return null;
    }

    boolean deleteActivity(int id) {

        JActivity act = getActivity(id);
        if (act != null) {
            return this.activities.remove(act);
        }
        return false;
    }

    void setName(String strName) {
        this.name = strName;
    }

    String getName() {
        return this.name;
    }

    void setCreationDate(Calendar calCreationDate) {
        this.creationDate = calCreationDate;
    }

    Calendar getCreationDate() {
        return this.creationDate;
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

}

