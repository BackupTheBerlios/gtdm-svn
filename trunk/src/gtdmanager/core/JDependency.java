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

    // dependency types
    public static int BEGINBEGIN = 0;
    public static int BEGINEND = 1;
    public static int ENDBEGIN = 2;
    public static int ENDEND = 3;

    public static String BEGINBEGINSTR = "begin-begin";
    public static String BEGINENDSTR   = "begin-end";
    public static String ENDBEGINSTR   = "end-begin";
    public static String ENDENDSTR     = "end-end";

    int id;
    int toActivityId;
    int dependencyType;
    int color;
    String line; // dotted, ... ?

    public JDependency() {
        toActivityId = -1;
        dependencyType = -1;
        color = -1;
        line = "";
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
            return BEGINBEGINSTR;
        } else if (dt == BEGINEND) {
            return BEGINENDSTR;
        } else if (dt == ENDBEGIN) {
            return ENDBEGINSTR;
        } else if (dt == ENDEND) {
            return ENDENDSTR;
        } else {
            return "";
        }
    }

    void setDependencyType(int depType) {
        this.dependencyType = depType;
    }

    public int getColor() {
        return this.color;
    }

    void setColor(int col) {
        this.color = col;
    }

    public String getLine() {
        return this.line;
    }

    void setLine(String lineStr) {
        this.line = lineStr;
    }

}
