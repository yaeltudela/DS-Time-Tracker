package core.ds.ds_project_timetracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Task extends Node implements Serializable {

    private Collection<Interval> intervals;
    private boolean active;


    public Task(String name, String description, Project project) {
        super(name, description, project);
        this.intervals = new ArrayList<>();
        this.parent = project;
        this.startDate = null;
        this.endDate = null;
        this.active = false;

        project.getActivities().add(this);
    }

    public void startInterval(Clock clock) {
        if (!isActive()) {
            Interval interval = new Interval(clock.getTime(), this);
            this.intervals.add(interval);
            clock.addObserver(interval);
        } else {
            System.out.println("Task already running!");
            //TODO THROW EXCEPTION O ALGO
        }

    }

    public void stopInterval(Clock clock) {
        if (isActive()) {
            clock.deleteObserver(((ArrayList<Interval>) (this.getIntervals())).get(this.getIntervals().size() - 1));
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
    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return getName() + "\t" + getStartDate() + "\t" + getEndDate() + "\t" + getDuration() + "\t" + getParent().getName();
    }

    @Override
    public void updateData(Date time) {
        this.endDate = ((ArrayList<Interval>) (this.getIntervals())).get(this.getIntervals().size() - 1).getEndDate();

        long duration = 0;
        for (Interval i : this.intervals) {
            duration += i.getDuration();
        }
        this.duration = duration;
        if (this.startDate == null) {
            this.startDate = time;
        }
        this.parent.updateData(time);

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean state) {
        this.active = state;
    }


}

