package core.ds.ds_project_timetracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Project extends Node implements Observer, Serializable {

    private Collection<Node> activities;


    public Project(String name, String description, Project parent) {
        super(name, description, parent);

        activities = new ArrayList<>();

        if (this.parent != null) {
            this.getParent().getActivities().add(this);
        }
    }

    public Collection<Node> getActivities() {
        return activities;
    }

    private void printSons(Project project) {
        System.out.println(project.toString());
        for (Node n : project.activities) {
            if(n instanceof Task){
                Task task = (Task) n;
                System.out.println(task.toString());
            } else {
                printSons((Project) n);
            }
        }
    }

    @Override
    public void updateData(Date time) {
        if (this.startDate == null) {
            this.startDate = time;
        }
        long tmp = 0;
        for (Node i : this.activities) {
            tmp += i.getDuration();
        }
        this.duration = tmp;
        this.endDate = time;

        if (this.parent != null) {
            this.parent.updateData(time);
        }
    }

    @Override
    public String toString() {
        return this.getName() + "\t" + this.getStartDate() + "\t" + this.getEndDate() + "\t" + this.getDuration() + "\t" + (this.getParent() == null ? "null" : this.getParent().getName());
    }

    public Project getParent() {
        return (Project) parent;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.printSons(this);
        System.out.println("-----------------------------------------------");

    }
}
