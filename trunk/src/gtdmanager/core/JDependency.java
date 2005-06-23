package gtdmanager.core;

/**
 * <p>Title: JDependency class</p>
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
public class JDependency {

    // dependenciy types
    public static int BEGINBEGIN = 0;
    public static int BEGINEND = 1;
    public static int ENDBEGIN = 2;
    public static int ENDEND = 3;

    int id;
    int toActivityId;
    int dependencyType;

    public JDependency() {
        toActivityId = -1;
        dependencyType = -1;
    }

    public int getId() {
	    return id;
    }

    public int getToActivityId() {
        return this.toActivityId;
    }

    void setToActivityId(int toId) {
        this.toActivityId = toId;
    }

    public int getDependencyType() {
        return this.dependencyType;
    }

    public String getDependencyTypeStr() {
        int dt = getDependencyType();
        if (dt == BEGINBEGIN) {
            return "Begin-Begin";
        } else if (dt == BEGINEND) {
            return "Begin-End";
        } else if (dt == ENDBEGIN) {
            return "End-Begin";
        } else if (dt == ENDEND) {
            return "End-End";
        } else {
            return "Unknown";
        }
    }

    void setDependencyType(int depType) {
        this.dependencyType = depType;
    }

}
