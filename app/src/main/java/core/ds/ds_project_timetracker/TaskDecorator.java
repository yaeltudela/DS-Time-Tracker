
package core.ds.ds_project_timetracker;


import java.util.ArrayList;
import java.util.Date;

/**
 * Abstract class used for generalize all the decorators for Task.
 */
public abstract class TaskDecorator extends Task {

    /**
     * The task to be decorated.
     */
    private Task baseTask;

    /**
     * Default constructor that updates the decorated
     * data with all the baseTask data.
     *
     * @param baseTask Task to be decorated
     */
    TaskDecorator(final Task baseTask) {
        this.baseTask = baseTask;

    }

    @Override
    public void updateData(final Date time) {
        this.baseTask.updateData(time);
    }

    @Override
    public void accept(final Visitor visitor) {
        this.baseTask.accept(visitor);
    }


    @Override
    public Project getParent() {
        return this.baseTask.getParent();
    }

    @Override
    public void setParent(final Node parent) {
        this.baseTask.setParent(parent);
    }

    @Override
    public Date getStartDate() {
        return this.baseTask.getStartDate();
    }

    @Override
    public void setStartDate(final Date startDate) {
        this.baseTask.setStartDate(startDate);
    }

    @Override
    public Date getEndDate() {
        return this.baseTask.getEndDate();
    }

    @Override
    public void setEndDate(final Date endDate) {
        this.baseTask.setEndDate(endDate);
    }

    @Override
    public long getDuration() {
        return this.baseTask.getDuration();
    }

    @Override
    public void setDuration(final long duration) {
        this.baseTask.setDuration(duration);
    }

    @Override
    public String getName() {
        return this.baseTask.getName();
    }

    @Override
    public void setName(final String name) {
        this.baseTask.setName(name);
    }

    @Override
    public String getDescription() {
        return this.baseTask.getDescription();
    }

    @Override
    public void setDescription(final String description) {
        this.baseTask.setDescription(description);
    }

    @Override
    public ArrayList<Interval> getIntervals() {
        return this.baseTask.getIntervals();
    }

    @Override
    public void setIntervals(final ArrayList<Interval> intervals) {
        this.baseTask.setIntervals(intervals);
    }

    @Override
    public boolean isActive() {
        return this.baseTask.isActive();
    }

    @Override
    public void setActive(final boolean state) {
        this.baseTask.setActive(state);
    }

}
