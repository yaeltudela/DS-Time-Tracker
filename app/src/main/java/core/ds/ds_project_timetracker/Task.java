package core.ds.ds_project_timetracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Abstract class who extends from Node and it's used to define a generic Task.
 * It must be serializable as the Node, so it can be saved/loaded to/from disk
 */
public abstract class Task extends Node implements Serializable {

    protected Collection<Interval> intervals;
    protected boolean active;

    /**
     * Constructor for the Task objects. It calls the Node constructor and also initialices
     * the intervals Collection.
     *
     * @param name        Task's Name. Must be non empty String
     * @param description Task's Description
     * @param project     Task's parent project. Must be a project
     */
    public Task(String name, String description, Project project) {
        super(name, description, project);

        this.setIntervals(new ArrayList<Interval>());
        this.setParent(project);
        this.setStartDate(null);
        this.setEndDate(null);
        this.setActive(false);
    }


    /**
     * Task empty Constructor
     */
    public Task() {

    }

    /**
     * Method that checks if the task isn't running and creates a new Interval object and makes
     * it an Observer
     */
    public void startInterval() {
        if (!this.isActive()) {
            this.setActive(true);
            Date startDate = Clock.getInstance().getTime();
            Interval interval = new Interval(startDate, this);
            this.getIntervals().add(interval);
            Clock.getInstance().addObserver(interval);
        } else {
            throw new IllegalStateException("Task already running");
        }

    }

    /**
     * Method that checks if the task is running, and deletes the last interval (the one that
     * is running) from being an Observer (making he don't refresh it's state more)
     */
    public void stopInterval() {
        if (this.isActive()) {
            Clock.getInstance().deleteObserver(this.getIntervals().get(this.getIntervals().size() - 1));
            this.setActive(false);
        } else {
            throw new IllegalStateException("Task isn't running");
        }
    }

    /**
     * Method that updates all the data for the current task and calls recursively to it's project
     *
     * @param time time to do the update. Usually the actual Clock time
     */
    public void updateData(Date time) {

        if (this.getStartDate() == null) {
            this.setStartDate(time);
        }

        this.setEndDate(time);

        long duration = 0;
        for (Interval i : this.getIntervals()) {
            duration += i.getDuration();
        }
        this.setDuration(duration);

        this.getParent().updateData(time);

    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visitTask(this);
    }

    @Override
    public String toString() {
        return this.getName() + "\t" + this.getStartDate() + "\t" + this.getEndDate() + "\t" + this.getDuration() + "\t" + getParent().getName();
    }

    //GETTERS AND SETTERS

    @Override
    public Project getParent() {
        return (Project) parent;
    }

    public ArrayList<Interval> getIntervals() {
        return (ArrayList<Interval>) intervals;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean state) {
        this.active = state;
    }


    public void setIntervals(ArrayList<Interval> intervals) {
        this.intervals = intervals;
    }
}

