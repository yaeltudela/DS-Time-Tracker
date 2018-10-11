package core.ds.ds_project_timetracker;


import java.io.Serializable;
import java.util.Date;

public abstract class Node implements Serializable, Visitable {

    protected String name;
    protected String description;

    protected long duration;
    protected Date startDate;
    protected Date endDate;
    protected Node parent;

    public Node(String name, String description, Node parent) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
            this.description = description;
            this.parent = parent;
            this.duration = 0;
            this.startDate = null;
            this.endDate = null;
        } else {
            throw new IllegalArgumentException("Project must have a name");
        }

    }

    public Node() {

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getDuration() {
        return duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public abstract void updateData(Date time);
}


