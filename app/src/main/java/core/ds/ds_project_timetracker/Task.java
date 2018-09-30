package core.ds.ds_project_timetracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Task extends Node implements Serializable {

    private Collection<Interval> intervals;
    private boolean active;


    public Task(String name, String description, Project project) {
        this.name = name;
        this.description = description;
        this.intervals = new ArrayList<>();
        this.parent = project;
        this.startDate = null;
        this.endDate = null;
        this.duration = 0;
        this.active = false;

        project.getActivities().add(this);
    }

    public void startInterval(Clock clock) {
        if (!isActive()) {
            Interval interval = new Interval(new Date(), this);
            this.intervals.add(interval);
            clock.addObserver(interval);
        } else {
            System.out.println("Task already running!");
            //TODO THROW EXCEPTION O ALGO
        }

    }

    public void stopInterval(Clock clock) {
        if (isActive()) {
            Interval lastInterval = (Interval) this.intervals.toArray()[(this.intervals.size() - 1)];
            clock.deleteObserver(lastInterval);

            this.startDate = lastInterval.getStartDate();
            this.endDate = lastInterval.getEndDate();
            setActive(false);
        } else {
            System.out.println("Task isn't running");
            //TODO THROW EXCEPTION O ALGO
        }
    }

    public Project getParent() {
        return (Project) parent;
    }

    public Collection<Interval> getIntervals() {
        return intervals;
    }

    @Override
    public float getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return getName() + "\t" + getStartDate() + "\t" + getEndDate() + "\t" + getDuration() + "\t" + getParent().getName();
    }

    @Override
    public void updateData(Date time) {
        if (this.startDate == null) {
            this.startDate = time;
        }

        this.endDate = ((Interval) this.intervals.toArray()[(this.intervals.size() - 1)]).getEndDate();

        float duration = 0;
        for (Interval i : this.intervals) {
            duration += i.getDuration();
        }
        this.duration = duration;

        this.parent.updateData(time);

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean state) {
        this.active = state;
    }
}

