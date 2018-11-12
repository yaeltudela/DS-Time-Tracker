package core.ds.ds_project_timetracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract class who extends from Node and it's used to define a generic Task.
 * It must be serializable as the Node, so it can be saved/loaded to/from disk
 */
public abstract class Task extends Node implements Serializable {

    protected final static Logger LOGGER = LoggerFactory.getLogger(Task.class);

    /**
     * Method that checks if the task isn't running and
     * creates a new Interval object and makes it an Observer.
     */
    public void startInterval() {
        //Preconditions
        assert (this.isActive()) : "Task is already running";
        assert (this.getIntervals() != null) : "Intervals are not initialized";


        if (!this.isActive()) {
            this.setActive(true);
            Interval interval = new Interval(Clock.getInstance()
                    .getTime(), this);
            this.getIntervals().add(interval);
            Clock.getInstance().addObserver(interval);
            LOGGER.info("Task starting interval" + this.getName() + " - " + interval.getId());
        } else {
            LOGGER.error("Tried to start an already started task. " + this.getName());
            throw new IllegalStateException("Task already running");
        }


        //Postconditions
        assert (!this.isActive()) : "Task didn't started";
        assert (this.getIntervals().size() <= 0) : "Task didn't add the running interval";
        assert (Clock.getInstance().countObservers() < 0) : "Interval is not observing clock";
    }

    /**
     * Method that checks if the task is running, and deletes
     * the last interval (the one that is running) from being an
     * Observer (making he don't refresh it's state more).
     */
    public void stopInterval() {
        //Preconditions
        assert (!this.isActive()) : "Task didn't started";
        assert (this.getIntervals().size() <= 0) : "There's no intervals on the task";
        assert (Clock.getInstance().countObservers() < 0) : "Interval is not observing clock";


        if (this.isActive()) {
            Clock.getInstance().deleteObserver(this.getIntervals().get(
                    this.getIntervals().size() - 1));
            this.setActive(false);
            LOGGER.info("Task stopping interval" + this.getName() + " - " + this.getIntervals().get(
                    this.getIntervals().size() - 1).getId());

        } else {
            LOGGER.error("Tried to stop an already stopped task. " + this.getName());
            throw new IllegalStateException("Task isn't running");
        }


        //Postconditions
        assert (this.isActive()) : "Task is already stopped";
        assert (this.getIntervals().size() <= 0) : "Intervals are not initialized";
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


    protected boolean invariant() { //TODO PORQUE DEBERIA SER boolean y cuando se llama? al acabar cada metodo??
        assert (this.getName() == null) : "Task name is null";
        return true;
    }
}

