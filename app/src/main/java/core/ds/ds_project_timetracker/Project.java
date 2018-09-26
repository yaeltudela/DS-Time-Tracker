package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Project extends Node implements Observer {

    private List<Node> activities;


    public Project(String name, String description, Project parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.duration = 0;

        activities = new ArrayList<>();

        if (this.parent != null) {
            this.getParent().getActivities().add(this);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.printSons(this);
        System.out.println("-----------------------------------------------");

    }
    //  To String

    public Project getParent() {
        return (Project) parent;
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
    public String toString() {
        return this.getName() + "\t" + this.getStartDate() + "\t" + this.getEndDate() + "\t" + this.getDuration() + "\t" + (this.getParent() == null ? "null" : this.getParent().getName());
    }
}
