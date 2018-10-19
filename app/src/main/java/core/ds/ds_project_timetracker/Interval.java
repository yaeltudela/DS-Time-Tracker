package core.ds.ds_project_timetracker;


import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * Class that represent every time that a task has been active
 */
public class Interval implements Observer, Serializable, Visitable {

    private Task parentTask;
    private Date startDate;
    private Date endDate;
    private long duration;

    /**
     * Default Interval constructor. It sets up the fields.
     *
     * @param startDate  Time when the Interval starts (usually current time)
     * @param parentTask The task that contains the Interval object
     */
    public Interval(Date startDate, Task parentTask) {
        this.duration = 0;
        this.startDate = startDate;
        this.endDate = null;
        this.parentTask = parentTask;

    }

    /**
     * Interval contructor for the user-input intervals. (Not implemented yet)
     *
     * @param startDate Time when the Interval starts
     * @param endDate Time when the Interval ends
     * @param parentTask The task that contains the Interval object
     */
    public Interval(Date startDate, Date endDate, Task parentTask) {
        this.parentTask = parentTask;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = (this.endDate.getTime() - this.startDate.getTime()) / 1000;

    }

    /**
     * Method that updates the end date, the interval's duration and the parents data
     * @param o -
     * @param arg Clock instance
     */
    @Override
    public void update(Observable o, Object arg) {
        this.endDate = ((Clock) arg).getTime();
        this.duration += Clock.REFRESH_RATE;
        this.parentTask.updateData(this.endDate);
    }


    //Getters & Setters

    public long getDuration() {
        return this.duration;
    }

    public Task getParentTask() {
        return this.parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    /**
     * Entrance method for the visitor to do tasks for the Interval class
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visitInterval(this);

    }

    @Override
    public String toString() {
        return "\t" + this.parentTask.getName() + " Interval \t" + this.duration;
    }
}

