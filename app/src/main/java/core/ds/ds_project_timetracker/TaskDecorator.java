package core.ds.ds_project_timetracker;


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

        this.setName(baseTask.name);
        this.setDescription(baseTask.description);
        this.setStartDate(baseTask.getStartDate());
        this.setEndDate(baseTask.getEndDate());
        this.setIntervals(baseTask.getIntervals());
        this.setParent(baseTask.getParent());
        this.setActive(baseTask.isActive());

    }


    /**
     * Method that updates all the BaseTask data with the updated data from the decorated Task
     */
    protected synchronized void updateValues() {
        this.baseTask.setIntervals(this.getIntervals());
        this.baseTask.setStartDate(this.getStartDate());
        this.baseTask.setEndDate(this.getEndDate());
        this.baseTask.setDuration(this.getDuration());
        this.baseTask.setActive(this.isActive());
    }

}
