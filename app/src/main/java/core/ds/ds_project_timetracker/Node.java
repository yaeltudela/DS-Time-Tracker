package core.ds.ds_project_timetracker;


import java.io.Serializable;
import java.util.Date;

public abstract class Node implements Serializable {

    protected String name;
    protected String description;

    protected long duration;
    Date startDate;
    Date endDate;

    protected Node parent;

    public Node(String name, String description, Node parent) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
            this.description = description;
            this.parent = parent;
            this.duration = 0;
        } else {
            throw new IllegalArgumentException("Project must have a name");
        }

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


    public abstract void updateData(Date time);
}


