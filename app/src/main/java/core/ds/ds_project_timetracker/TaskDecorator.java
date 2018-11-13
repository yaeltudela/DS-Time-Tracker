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
    private final Task baseTask;

    /**
     * Default constructor that updates the decorated
     * data with all the baseTask data.
     *
     * @param task Task to be decorated
     */
    TaskDecorator(final Task task) {
        this.baseTask = task;

    }

    @Override
    public void updateData(final Date time) {
        this.baseTask.updateData(time);
    }

    @Override
    public void accept(final Visitor visitor) {
        LOGGER.info("Visitor accepts in task " + this.getName());
        this.getBaseTask().accept(visitor);
    }

    public Task getBaseTask() {
        return this.baseTask;
    }

    @Override
    public Project getParent() {
        return this.getBaseTask().getParent();
    }

    @Override
    public void setParent(final Node parent) {
        this.getBaseTask().setParent(parent);
    }

    @Override
    public Date getStartDate() {
        return this.getBaseTask().getStartDate();
    }

    @Override
    public void setStartDate(final Date startDate) {
        this.getBaseTask().setStartDate(startDate);
    }

    @Override
    public Date getEndDate() {
        return this.getBaseTask().getEndDate();
    }

    @Override
    public void setEndDate(final Date endDate) {
        this.getBaseTask().setEndDate(endDate);
    }

    @Override
    public long getDuration() {
        return this.getBaseTask().getDuration();
    }

    @Override
    public void setDuration(final long duration) {
        this.getBaseTask().setDuration(duration);
    }

    @Override
    public String getName() {
        return this.getBaseTask().getName();
    }

    @Override
    public void setName(final String name) {
        this.getBaseTask().setName(name);
    }

    @Override
    public String getDescription() {
        return this.getBaseTask().getDescription();
    }

    @Override
    public void setDescription(final String description) {
        this.getBaseTask().setDescription(description);
    }

    @Override
    public ArrayList<Interval> getIntervals() {
        return this.getBaseTask().getIntervals();
    }

    @Override
    public void setIntervals(final ArrayList<Interval> intervals) {
        this.getBaseTask().setIntervals(intervals);
    }

    @Override
    public boolean isActive() {
        return this.getBaseTask().isActive();
    }

    @Override
    public void setActive(final boolean state) {
        this.getBaseTask().setActive(state);
    }

    /**
     * Method that checks the invariant of TaskDecorator.
     * It checks the invariants of parent classes and itself.
     */
    @Override
    protected void invariant() {
        super.invariant();
        assert (this.getBaseTask() == null) : "BaseTask is null";
    }
}
