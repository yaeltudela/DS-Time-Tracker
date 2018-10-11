package core.ds.ds_project_timetracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Project extends Node implements Serializable {

    private Collection<Node> activities = new ArrayList<>();


    public Project(String name, String description, Project parent) {
        super(name, description, parent);

        //activities = new ArrayList<>();

        if (this.parent != null) {
            this.getParent().getActivities().add(this);
        }
    }

    public Collection<Node> getActivities() {
        return activities;
    }

    @Override
    public void updateData(Date time) {
        if (this.startDate == null) {
            this.startDate = time;
        }
        this.endDate = time;
        long tmp = 0;
        for (Node i : this.activities) {
            tmp += i.getDuration();
        }
        this.duration = tmp;

        if (this.parent != null) {
            this.parent.updateData(time);
        }
    }

    @Override
    public String toString() {
        return this.getName() + "\t" + this.getStartDate() + "\t" + this.getEndDate() + "\t" + this.getDuration() + "\t" + (this.getParent() == null ? "null" : this.getParent().getName());
    }

    @Override
    public Project getParent() {
        return (Project) parent;
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visitProject(this);
    }
}
