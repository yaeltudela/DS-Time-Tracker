package com.dstimetracker.devsodin.core;


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

    /**
     * Method that updates recursively the data of all the parents.
     *
     * @param time time to do the update. Usually the actual Clock time
     */
    @Override
    public void updateData(final Date time) {
        this.baseTask.updateData(time);
    }

    /**
     * Method that accept the visitors for the baseTask.
     *
     * @param visitor The visitor itself
     */
    @Override
    public void accept(final Visitor visitor) {
        LOGGER.info("Visitor accepts in task " + this.getName());
        this.getBaseTask().accept(visitor);
    }

    /**
     * Getter for the BaseTask.
     *
     * @return Task to be decorated.
     */
    public Task getBaseTask() {
        return this.baseTask;
    }

    /**
     * Getter for the parent field. It gets the field for the baseTask.
     *
     * @return Node with the baseTask parent.
     */
    @Override
    public Project getParent() {
        return this.getBaseTask().getParent();
    }

    /**
     * Setter for the parent field. It sets the field for the baseTask.
     *
     * @param parent Node with the baseTask parent.
     */
    @Override
    public void setParent(final Node parent) {
        this.getBaseTask().setParent(parent);
    }

    /**
     * Getter for the startDate field. It gets the field for the baseTask.
     *
     * @return Date with the baseTask startDate.
     */
    @Override
    public Date getStartDate() {
        return this.getBaseTask().getStartDate();
    }

    /**
     * Setter for the startDate field. It sets the field for the baseTask.
     *
     * @param startDate Date with the baseTask startDate.
     */
    @Override
    public void setStartDate(final Date startDate) {
        this.getBaseTask().setStartDate(startDate);
    }

    /**
     * Getter for the endDate field. It gets the field for the baseTask.
     *
     * @return Date with the baseTask endDate.
     */
    @Override
    public Date getEndDate() {
        return this.getBaseTask().getEndDate();
    }

    /**
     * Setter for the endDate field. It sets the field for the baseTask.
     *
     * @param endDate Date with the baseTask endDate.
     */
    @Override
    public void setEndDate(final Date endDate) {
        this.getBaseTask().setEndDate(endDate);
    }

    /**
     * Getter for the duration field. It gets the field for the baseTask.
     *
     * @return long with the baseTask duration.
     */
    @Override
    public long getDuration() {
        return this.getBaseTask().getDuration();
    }

    /**
     * Setter for the duration field. It sets the field for the baseTask.
     *
     * @param duration long with the baseTask duration.
     */
    @Override
    public void setDuration(final long duration) {
        this.getBaseTask().setDuration(duration);
    }

    /**
     * Getter for the name field. It gets the field for the baseTask.
     *
     * @return String with the baseTask name.
     */
    @Override
    public String getName() {
        return this.getBaseTask().getName();
    }

    /**
     * Setter for the name field. It sets the field for the baseTask.
     *
     * @param name String with the BaseTask name
     */
    @Override
    public void setName(final String name) {
        this.getBaseTask().setName(name);
    }

    /**
     * Getter for the description field. It gets the field for the baseTask.
     *
     * @return String with the baseTask description.
     */
    @Override
    public String getDescription() {
        return this.getBaseTask().getDescription();
    }

    /**
     * Setter for the description field. It sets the field for the baseTask.
     *
     * @param description String with the BaseTask description
     */
    @Override
    public void setDescription(final String description) {
        this.getBaseTask().setDescription(description);
    }

    /**
     * Getter for the intervals field. It gets the field for the baseTask.
     *
     * @return ArrayList with the baseTask intervals.
     */
    @Override
    public ArrayList<Interval> getIntervals() {
        return this.getBaseTask().getIntervals();
    }

    /**
     * Setter for the intervals field. It sets the field for the baseTask.
     *
     * @param intervals An arrayList with all the Intervals for this task
     */
    @Override
    public void setIntervals(final ArrayList<Interval> intervals) {
        this.getBaseTask().setIntervals(intervals);
    }

    /**
     * Getter for the active field. It gets the field for the baseTask.
     *
     * @return true if the baseTask is active, false otherwise.
     */
    @Override
    public boolean isActive() {
        return this.getBaseTask().isActive();
    }

    /**
     * Setter for the active field. It sets the field for the baseTask.
     *
     * @param state the state
     */
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
