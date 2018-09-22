package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.List;

public class Task extends Node {

    private List<Interval> intervals;
    private Project project;

    public Task(String name, String description, Project project) {
        this.name = name;
        this.description = description;
        this.project = project;
        this.intervals = new ArrayList<>();

        System.out.println(this.toString());
    }

    //  To String
    @Override
    public String toString() {
        return "Task Name: " + getName() + "  -  task description: " + getDescription() + " - Project: " + getProject().getName();
    }

    public void startInterval() {

    }

    public void stopInterval() {

    }

    public void getTotalWorkTime() {

    }

    private Project getProject() {
        return project;
    }
}
