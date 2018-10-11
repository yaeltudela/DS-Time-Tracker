package core.ds.ds_project_timetracker;

public abstract class TaskDecorator extends Task {

    protected Task baseTask;

    public TaskDecorator(Task baseTask) {
        this.baseTask = baseTask;

        this.intervals = baseTask.intervals;
        this.parent = baseTask.parent;
        this.startDate = baseTask.startDate;
        this.endDate = baseTask.endDate;
        this.active = baseTask.active;
    }

    protected void updateValues() {
        this.baseTask.startDate = this.startDate;
        this.baseTask.endDate = this.endDate;
        this.baseTask.duration = this.duration;

    }

}
