package core.ds.ds_project_timetracker;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Class that represents a task without decorators.
 * Basetask instaciates Intervals and control when starts or stop.
 */
public class BaseTask extends Task {

    private Collection<Interval> intervals;
    private boolean active;

    /**
     * BaseTask constructor. Calls the Task constructor
     * and adds itself to it's parent activities.
     *
     * @param name        BaseTask's name
     * @param description BaseTask's description
     * @param project     BaseTask's parent project
     */
    BaseTask(final String name, final String description, final Project project) {
        this.setName(name);
        this.setDescription(description);
        this.setDuration(0);

        this.setIntervals(new ArrayList<Interval>());
        this.setParent(project);
        this.setStartDate(null);
        this.setEndDate(null);
        this.setActive(false);


        this.getParent().getActivities().add(this);
        this.id = new Id();
        if (isRootNode()) {
            this.id.generateid();
        } else {
            this.id.setId(this.getParent().getId().getId() + "." + this.getParent().getActivities().size());
        }
        LOGGER.info("new basic Task " + this.getName());

    }

    /**
     * Method that updates all the data for the current task
     * and calls recursively to it's project.
     *
     * @param time time to do the update. Usually the actual Clock time
     */
    public void updateData(final Date time) {

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
     * Getter for the parent field.
     *
     * @return Task parent
     */
    @Override
    public Project getParent() {
        return (Project) parent;
    }

    /**
     * Getter for the Intervals field.
     *
     * @return Interval collection from the Task
     */
    public ArrayList<Interval> getIntervals() {
        return (ArrayList<Interval>) intervals;
    }

    /**
     * Setter for the Intervals field.
     *
     * @param intervalList An arrayList with all the Intervals for this task
     */
    public void setIntervals(final ArrayList<Interval> intervalList) {
        this.intervals = intervalList;
    }

    /**
     * Getter for the Active field.
     *
     * @return Boolean with the active status
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Setter for the Active field.
     *
     * @param state the state
     */
    public void setActive(final boolean state) {
        this.active = state;
    }

    /**
     * Entrance method for the visitor to do tasks for the Task class.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final Visitor visitor) {
        LOGGER.info("Visitor accepts in task " + this.getName());
        ((TreeVisitor) visitor).visitTask(this);
    }
}

