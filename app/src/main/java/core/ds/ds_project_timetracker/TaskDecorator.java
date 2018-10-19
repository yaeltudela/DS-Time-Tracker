package core.ds.ds_project_timetracker;


import java.util.ArrayList;
import java.util.Date;

/**
 * Abstract class used for generalize all the decorators for Task.
 */
public abstract class TaskDecorator extends Task {

    protected Task baseTask;

    /**
     * Default constructor that updates the decorated data with all the baseTask data.
     *
     * @param baseTask
     */
    public TaskDecorator(Task baseTask) {
        this.baseTask = baseTask;

    }


    @Override
    public void updateData(Date time) {
        this.baseTask.updateData(time);
    }

    @Override
    public void accept(Visitor visitor) {
        this.baseTask.accept(visitor);
    }


    @Override
    public Project getParent() {
        return this.baseTask.getParent();
    }

    @Override
    public void setParent(Node parent) {
        this.baseTask.setParent(parent);
    }

    @Override
    public Date getStartDate() {
        return this.baseTask.getStartDate();
    }

    @Override
    public void setStartDate(Date startDate) {
        this.baseTask.setStartDate(startDate);
    }

    @Override
    public Date getEndDate() {
        return this.baseTask.getEndDate();
    }

    @Override
    public void setEndDate(Date endDate) {
        this.baseTask.setEndDate(endDate);
    }

    @Override
    public long getDuration() {
        return this.baseTask.getDuration();
    }

    @Override
    public void setDuration(long duration) {
        this.baseTask.setDuration(duration);
    }

    @Override
    public String getName() {
        return this.baseTask.getName();
    }

    @Override
    public void setName(String name) {
        this.baseTask.setName(name);
    }

    @Override
    public String getDescription() {
        return this.baseTask.getDescription();
    }

    @Override
    public void setDescription(String description) {
        this.baseTask.setDescription(description);
    }

    @Override
    public ArrayList<Interval> getIntervals() {
        return this.baseTask.getIntervals();
    }

    @Override
    public void setIntervals(ArrayList<Interval> intervals) {
        this.baseTask.setIntervals(intervals);
    }

    @Override
    public boolean isActive() {
        return this.baseTask.isActive();
    }

    @Override
    public void setActive(boolean state) {
        this.baseTask.setActive(state);
    }

}
