package core.ds.ds_project_timetracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public abstract class Task extends Node implements Serializable {

    protected Collection<Interval> intervals;
    protected boolean active;

    public Task(String name, String description, Project project) {
        super(name, description, project);

        this.intervals = new ArrayList<>();
        this.parent = project;
        this.startDate = null;
        this.endDate = null;
        this.active = false;
    }

    public Task() {

    }

    public void startInterval() {
        if (!this.isActive()) {
            Date startDate = Clock.getInstance().getTime();
            Interval interval = new Interval(startDate, this);
            this.intervals.add(interval);
            Clock.getInstance().addObserver(interval);
            if (this.startDate == null) {
                this.startDate = startDate;
            }
            if (this.endDate == null) {
                this.endDate = startDate;
            }

        } else {
            System.out.println("Task already running!");
            //TODO THROW EXCEPTION OR SOMETHING
        }

    }


    public void stopInterval() {
        if (this.isActive()) {
            Clock.getInstance().deleteObserver(this.getIntervals().get(this.getIntervals().size() - 1));
            setActive(false);
        } else {
            System.out.println("Task isn't running");
            //TODO THROW EXCEPTION OR SOMETHING
        }
    }


    public void updateData(Date time) {

        if (this.startDate == null) {
            this.startDate = time;
        }

        this.endDate = time;

        long duration = 0;
        for (Interval i : this.intervals) {
            duration += i.getDuration();
        }
        this.duration = duration;

        this.parent.updateData(time);

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


    @Override
    public void accept(Visitor visitor) {
        visitor.visitTask(this);
    }

}

