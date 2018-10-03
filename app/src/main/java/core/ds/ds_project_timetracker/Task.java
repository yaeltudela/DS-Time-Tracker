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


    //Create an interval and adds it as observer and setup the startDate from this task (if needed)
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


    //Stops an interval and delete it as an observer and stops the task
    public void stopInterval() {
        if (isActive()) {
            Clock.getInstance().deleteObserver(this.getIntervals().get(this.getIntervals().size() - 1));
            setActive(false);
        } else {
            System.out.println("Task isn't running");
            //TODO THROW EXCEPTION O ALGO
        }
    }


    @Override
    //Recursive function to calculate the duration (sum of intervals)
    public void updateData(Date time) {
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
        return getName() + "\t" + getStartDate() + "\t" + getEndDate() + "\t" + getDuration() + "\t" + getParent().getName();
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

