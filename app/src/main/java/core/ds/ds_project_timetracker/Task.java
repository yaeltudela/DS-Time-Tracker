package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Task extends Node {

    private Collection<Interval> intervals;

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

    public void startInterval(Clock clock) {
        Interval interval = new Interval(new Date(), this);
        this.intervals.add(interval);
        interval.setActive(true);
        clock.addObserver(interval);
    }

    public void stopInterval(Clock clock) {
        Interval interval = null;

        if (this.getIntervals() != null && !this.getIntervals().isEmpty()) {
            interval = (Interval) this.getIntervals().toArray()[this.getIntervals().size() - 1];
        }
        interval.setActive(false);
        clock.deleteObserver(interval);
        this.startDate = ((Interval) this.intervals.toArray()[0]).getStartDate();
        this.endDate = ((Interval) this.intervals.toArray()[(this.intervals.size() - 1)]).getEndDate();
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
        this.startDate = ((Interval) this.intervals.toArray()[0]).getStartDate();
        this.endDate = ((Interval) this.intervals.toArray()[this.intervals.size() - 1]).getEndDate();

        float duration = 0;
        for (Interval i : this.intervals) {
            duration += i.getDuration();
        }
        this.duration = duration;

        this.parent.updateData(time);

    }
}

