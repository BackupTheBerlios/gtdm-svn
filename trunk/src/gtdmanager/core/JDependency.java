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

    int id;
    int toActivityId;
    int dependencyType; // 0=Begin-Begin, 1=Begin-End, 2=End-Begin, 3=End-End

    public JDependency() {
        toActivityId = -1;
        dependencyType = -1;
    }

    public int getToActivityId() {
        return this.toActivityId;
    }

    void setToActivityId(int toId) {
        this.toActivityId = toId;
    }

    int getDependencyType() {
        return this.dependencyType;
    }

    void setDependencyType(int depType) {
        this.dependencyType = depType;
    }

}
