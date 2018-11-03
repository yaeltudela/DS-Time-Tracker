package core.ds.ds_project_timetracker;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class BaseTask extends Task {

    private Collection<Interval> intervals;
    private boolean active;

    /**
     * BaseTask constructor. Calls the Task constructor and adds itself to it's parent activities
     *
     * @param name        BaseTask's name
     * @param description BaseTask's description
     * @param project     BaseTask's parent project
     */
    BaseTask(String name, String description, Project project) {
        this.setName(name);
        this.setDescription(description);
        this.setDuration(0);

        this.setIntervals(new ArrayList<Interval>());
        this.setParent(project);
        this.setStartDate(null);
        this.setEndDate(null);
        this.setActive(false);

        this.getParent().getActivities().add(this);
    }

    /**
     * Method that updates all the data for the current task and calls recursively to it's project
     *
     * @param time time to do the update. Usually the actual Clock time
     */
    public void updateData(Date time) {

        if (this.getStartDate() == null) {
            this.setStartDate(time);
        }
        this.setEndDate(time);

        long duration = 0;
        for (Interval i : this.getIntervals()) {
            duration += i.getDuration();
        }
        this.setDuration(duration);

        this.getParent().updateData(time);
    }


    @Override
    public String toString() {
        return this.getName() + "\t" + this.getStartDate() + "\t" + this.getEndDate() + "\t" + this.getDuration() + "\t" + getParent().getName();
    }

    //GETTERS AND SETTERS

    /**
     * Getter for the parent field
     *
     * @return Task parent
     */
    @Override
    public Project getParent() {
        return (Project) parent;
    }

    /**
     * Getter for the Intervals field
     *
     * @return Interval collection from the Task
     */
    public ArrayList<Interval> getIntervals() {
        return (ArrayList<Interval>) intervals;
    }

    /**
     * Setter for the Intervals field
     *
     * @param intervals An arrayList with all the Intervals for this task
     */
    public void setIntervals(ArrayList<Interval> intervals) {
        this.intervals = intervals;
    }

    /**
     * Getter for the Active field
     *
     * @return Boolean with the active status
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Setter for the Active field
     *
     * @param state the state
     */
    public void setActive(boolean state) {
        this.active = state;
    }

    /**
     * Entrance method for the visitor to do tasks for the Task class
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visitTask(this);
    }
}

