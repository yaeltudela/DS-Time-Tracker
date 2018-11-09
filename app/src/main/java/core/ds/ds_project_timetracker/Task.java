
package core.ds.ds_project_timetracker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract class who extends from Node and it's used to define a generic Task.
 * It must be serializable as the Node, so it can be saved/loaded to/from disk
 */
public abstract class Task extends Node implements Serializable {

    /**
     * Method that checks if the task isn't running and
     * creates a new Interval object and makes it an Observer.
     */
    public void startInterval() {
        if (!this.isActive()) {
            this.setActive(true);
            Interval interval = new Interval(Clock.getInstance()
                    .getTime(), this);
            this.getIntervals().add(interval);
            Clock.getInstance().addObserver(interval);
        } else {
            throw new IllegalStateException("Task already running");
        }

    }

    /**
     * Method that checks if the task is running, and deletes
     * the last interval (the one that is running) from being an
     * Observer (making he don't refresh it's state more).
     */
    public void stopInterval() {
        if (this.isActive()) {
            Clock.getInstance().deleteObserver(this.getIntervals().get(
                    this.getIntervals().size() - 1));
            this.setActive(false);
        } else {
            throw new IllegalStateException("Task isn't running");
        }
    }




    @Override
    public String toString() {
        return this.getName() + "\t" + this.getStartDate() + "\t" + this.getEndDate() + "\t" + this.getDuration() + "\t" + getParent().getName();
    }

    //GETTERS AND SETTERS

    /**
     * Getter for the parent field.
     *
     * @return Task parent
     */
    @Override
    public abstract Project getParent();

    /**
     * Getter for the Intervals field.
     *
     * @return Interval collection from the Task
     */
    public abstract ArrayList<Interval> getIntervals();

    /**
     * Setter for the Intervals field.
     *
     * @param intervals An arrayList with all the Intervals for this task
     */
    public abstract void setIntervals(ArrayList<Interval> intervals);

    /**
     * Getter for the Active field.
     *
     * @return Boolean with the active status
     */
    public abstract boolean isActive();

    /**
     * Setter for the Active field.
     *
     * @param state the state
     */
    public abstract void setActive(boolean state);
}

