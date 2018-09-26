package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task extends Node {

    //Name
    //Description
    private List<Interval> intervals;

    public Task(String name, String description, Project project) {
        this.name = name;
        this.description = description;
        this.intervals = new ArrayList<>();
        this.parent = project;
        this.startDate = null;
        this.endDate = null;
        this.duration = 0;

        project.getActivities().add(this);
    }

    //  To String
    public void startInterval(Clock clock) {
        Interval interval = new Interval(new Date(), this);
        this.intervals.add(interval);
        clock.addObserver(interval);
    }

    public void stopInterval(Clock clock) {
        Interval interval = null;
        if (this.getIntervals() != null && !this.getIntervals().isEmpty()) {
            interval = this.getIntervals().get(this.getIntervals().size() - 1);
        }
        clock.deleteObserver(interval);
        this.getDuration();
        this.startDate = this.intervals.get(0).getStartDate();
        this.endDate = this.intervals.get(this.intervals.size() - 1).getEndDate();
    }

    public Project getParent() {
        return (Project) parent;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }

    @Override
    public float getDuration() {
        float duration = 0;
        for (Interval i : intervals) {
            duration += i.getDuration();
        }
        this.duration = duration;
        return duration;
    }

    @Override
    public String toString() {
        return getName() + "\t" + getStartDate() + "\t" + getEndDate() + "\t" + getDuration() + "\t" + getParent().getName();
    }
}

