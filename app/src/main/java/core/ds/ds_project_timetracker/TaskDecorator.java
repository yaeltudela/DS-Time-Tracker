package core.ds.ds_project_timetracker;


/**
 * Abstract class used for generalize all the decorators for Task.
 */
public abstract class TaskDecorator extends Task {

    protected Task baseTask;

    public TaskDecorator(Task baseTask) {
        this.baseTask = baseTask;

        this.intervals = baseTask.intervals;
        this.setParent(baseTask.parent);
        this.setStartDate(baseTask.startDate);
        this.setEndDate(baseTask.endDate);
        this.setActive(baseTask.active);
    }


    protected void updateValues() {
        this.baseTask.setStartDate(this.getStartDate());
        this.baseTask.setEndDate(this.getEndDate());
        this.baseTask.setDuration(this.getDuration());
    }

}
