package core.ds.ds_project_timetracker;


import java.util.Date;

public class Node {

    protected String name;
    protected String description;

    protected float duration = 0;
    protected Date startDate;
    protected Date endDate;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getDuration() {
        return duration;
    }


}


