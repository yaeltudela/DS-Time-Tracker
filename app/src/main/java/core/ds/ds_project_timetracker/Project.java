package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Project extends Node implements Observer {

    private List<Node> activities;
    private Project parent;


    public Project(String name, String description, Project parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;

        activities = new ArrayList<>();

        if (this.parent != null) {
            this.parent.getActivities().add(this);
        }
    }


    //  To String
    @Override
    public String toString() {
        return this.getName() + "\t" + this.getStartDate() + "\t" + this.getEndDate() + "\t" + this.getDuration() + "\t" + (this.getParent() == null ? "null" : this.getParent().getName());
    }

    public Project getParent() {
        return parent;
    }

    public List<Node> getActivities() {
        return activities;
    }

    @Override
    public float getDuration() {
        this.duration = 0;
        for (Node n : activities) {
            this.duration += n.getDuration();
        }
        return this.duration;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.printSons(this);
        System.out.println("-----------------------------------------------");

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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }


}
