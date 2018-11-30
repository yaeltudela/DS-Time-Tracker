package com.dstimetracker.devsodin.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * Class that represent every time that a task has been active.
 */
public class Interval implements Observer, Serializable, Visitable {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(Interval.class);
    private final Task parentTask;
    private final Date startDate;
    private final Id id;
    private Date endDate;
    private long duration;

    /**
     * Default Interval constructor. It sets up the fields.
     *
     * @param start  Time when the Interval starts (usually current time)
     * @param parent The task that contains the Interval object
     */
    Interval(final Date start, final Task parent) {
        this.duration = 0;
        this.startDate = start;
        this.endDate = null;
        this.parentTask = parent;

        this.id = new Id();
        this.id.setId(String.valueOf(this.getParentTask()
                .getIntervals().size() + 1));

        LOGGER.info("Created new Interval by task"
                + this.getParentTask().getName() + " - " + this.getIdName());


    }

    /**
     * Interval constructor for the user-input intervals. (Not implemented yet)
     *
     * @param start  Time when the Interval starts
     * @param end    Time when the Interval ends
     * @param parent The task that contains the Interval object
     */
    public Interval(final Date start, final Date end, final Task parent) {
        this.parentTask = parent;
        this.startDate = start;
        this.endDate = end;
        this.duration = (this.endDate.getTime()
                - this.startDate.getTime()) / Clock.MS_IN_SEC;

        this.id = new Id();
        this.id.setId(String.valueOf(this.getParentTask()
                .getIntervals().size() + 1));

        LOGGER.info("Created new Interval by interval"
                + this.getParentTask().getName() + " - " + this.getIdName());

    }

    /**
     * Method that updates the end date, the interval's
     * duration and the parents data.
     *
     * @param o   -
     * @param arg Clock instance
     */
    @Override
    public void update(final Observable o, final Object arg) {
        this.endDate = ((Clock) arg).getTime();
        this.duration += Clock.nREFRESHRATE;
        this.parentTask.updateData(this.endDate);
    }


    //Getters & Setters

    /**
     * Getter for the duration field.
     *
     * @return long with the interval duration
     */
    public long getDuration() {
        return this.duration;
    }

    /**
     * Getter for the parent field.
     *
     * @return Task with the interval parent
     */
    public Task getParentTask() {
        return this.parentTask;
    }

    /**
     * Getter for the StartDate field.
     *
     * @return Date with the interval startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Getter for the endDate field.
     *
     * @return Date with the interval endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Entrance method for the visitor to do tasks for the Interval class.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final Visitor visitor) {
        LOGGER.info("Visitor accepts in Interval "
                + this.getParentTask().getName() + " - " + this.getIdName());
        ((TreeVisitor) visitor).visitInterval(this);

    }

    /**
     * Method that gets all the relevant Interval data.
     *
     * @return String with Interval data as the following. "Task_name duration".
     */
    @Override
    public String toString() {
        return "\t" + this.parentTask.getName() + "Interval \t" + this.duration;
    }

    /**
     * Getter for the Interval id.
     *
     * @return Interval Id.
     */
    public Id getId() {
        return this.id;
    }

    /**
     * Getter for the String value of id.
     *
     * @return String with the id.
     */
    public String getIdName() {
        return this.id.getId();
    }
}

