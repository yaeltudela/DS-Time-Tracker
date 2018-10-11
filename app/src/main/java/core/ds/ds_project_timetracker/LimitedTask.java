package core.ds.ds_project_timetracker;

import java.util.Date;

public class LimitedTask extends TaskDecorator {

    private Date deathLine;

    public LimitedTask(Task task, Date deathLine) {
        super(task);
        this.deathLine = deathLine;
    }

    @Override
    public void updateData(Date time) {
        this.endDate = time;
        if (this.endDate.before(this.deathLine)) {
            super.updateData(time);
            this.updateValues();

        } else {
            this.stopInterval();
        }

    }

    protected void updateValues() {
        this.baseTask.startDate = this.startDate;
        this.baseTask.endDate = this.endDate;
        this.baseTask.duration = this.duration;

    }
}
