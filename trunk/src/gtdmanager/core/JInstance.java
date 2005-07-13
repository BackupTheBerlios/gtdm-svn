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

    public int getId() {
	return id;
    }

    // gibt die nächst größere id nach allen (!) activities zurück
    private int getNextId(ArrayList actList, int beginId) {

        int retId = beginId;

        if (actList.size() > 0) {

            for (int actIdx = 0; actIdx < actList.size(); actIdx++) {

                JActivity act = (JActivity)actList.get(actIdx);
                // durchsucht alle in actList geg. activities nach größeren ids
                if (act.getId() >= retId) {
                    retId = act.getId() + 1;
                }

                // durchsucht auch die kinder der in actList gegebenen
                // activities nach größeren ids
                retId = getNextId(act.activities, retId);
            }

        }
        // gibt die höchste id von allen activities + 1 zurück
        return retId;
    }

    public int newActivity(ArrayList actList) {

        JActivity act = new JActivity();

        act.id = getNextId(activities, 0);
        // hier nicht actList sondern activities,
        // da alle activities nach der größten id durchsucht werden müssen

        actList.add(act);  // fügt die neue activity zur arraylist hinzu

        return act.id;  // gibt die id der neuen activity zurück
    }

    public int newActivity() {
        // erstellt eine neue activity in dieser instanz
        return newActivity(activities);
    }

    public int newActivity(ArrayList actList, String strName,
                           String strShortName, Calendar calStart,
                           Calendar calEnd, int color) {

        JActivity act = new JActivity();

        act.id = getNextId(activities, 0);
        // hier nicht actList sondern activities,
        // da alle activities nach der größten id durchsucht werden müssen

        act.setName(strName);
        act.setShortName(strShortName);
        act.setStartDate(calStart);
        act.setEndDate(calEnd);
        act.setColor(color);

        actList.add(act);  // fügt die neue activity zur arraylist hinzu

        return act.id;  // gibt die id der neuen activity zurück
    }


    public ArrayList getActivities() {
        return activities;
    }

    public JActivity getActivity(int id) {
        // gibt die activity mit der übergebenen id zurück
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
                if (deleteActivity(act.activities, id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteActivity(int id) {
        // löscht die activity mit der übergebenen id
        return deleteActivity(activities, id);
    }

    private void deleteDependencies(ArrayList elemList, int toActId) {
        for (int i = elemList.size() - 1; i >= 0; i--) {
            if (elemList.get(i).getClass() == JActivity.class) {
                JActivity act = (JActivity)elemList.get(i);
                deleteDependencies(act.dependencies, toActId);
            } else if (elemList.get(i).getClass() == JDependency.class) {
                JDependency dep = (JDependency)elemList.get(i);
                if (dep.getToActivityId() == toActId) {
                    elemList.remove(i);
                }
            }
        }
    }

    public void deleteDependencies(int toActId) {
        // löscht alle dependencies, die auf die id verweisen
        deleteDependencies(activities, toActId);
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

