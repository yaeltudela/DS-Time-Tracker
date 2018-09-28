package core.ds.ds_project_timetracker;


import java.util.Date;

public abstract class Node {

    protected String name;
    protected String description;

    float duration;
    Date startDate;
    Date endDate;

    protected Node parent;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getDuration() {
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


