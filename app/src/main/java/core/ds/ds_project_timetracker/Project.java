package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.List;

public class Project extends Node {

    private List<Node> activities;
    private Project parent;


    public Project(String name, String description, Project parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;

        activities = new ArrayList<>();

        System.out.println(this.toString());
    }


    //  To String
    @Override
    public String toString() {
        if (getParent() == null) {
            return "Project Name: " + getName() + "  -  project description: " + getDescription() + " - parent: None";
        } else {
            return "Project Name: " + getName() + "  -  project description: " + getDescription() + " - parent: " + getParent().getName();
        }

    }

/*
    public void getTree(){
        System.out.println(this.toString());

        for (Node n: activities) {
            if(n instanceof Task){
                System.out.println("\t" + n.toString());
            }else{
                System.out.print("\t");
                (Project) n.getTree();
            }
        }

    }

*/


    public void addProject(String name, String description) {
        Project subproject = new Project(name, description, this);
        activities.add(subproject);
    }

    public void addProject(Project subproject) {
        activities.add(subproject);

    }

    public void addTask(String name, String description) {
        Task task = new Task(name, description, this);
        activities.add(task);
    }

    public Project getParent() {
        return parent;
    }
}
