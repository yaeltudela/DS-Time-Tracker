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

    public void startInterval() {
        if (!isActive()) {
            Date startDate = Clock.getInstance().getTime();
            Interval interval = new Interval(startDate, this);
            this.intervals.add(interval);
            Clock.getInstance().addObserver(interval);
            if (this.startDate == null) {
                this.startDate = startDate;
            }
        } else {
            System.out.println("Task already running!");
            //TODO THROW EXCEPTION O ALGO
        }

    }

    public void stopInterval() {
        if (isActive()) {
            Clock.getInstance().deleteObserver(this.getIntervals().get(this.getIntervals().size() - 1));
            setActive(false);
        } else {
            System.out.println("Task isn't running");
            //TODO THROW EXCEPTION O ALGO
        }
    }

    public Project getParent() {
        return (Project) parent;
    }

    public ArrayList<Interval> getIntervals() {
        return (ArrayList<Interval>) intervals;
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
        this.endDate = time;

        long duration = 0;
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

