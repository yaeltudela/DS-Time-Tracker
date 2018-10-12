package core.ds.ds_project_timetracker;

import java.util.Date;

public class LimitedTask extends TaskDecorator {

    private Date deathLine;
    private int totalSeconds = -1;


    public LimitedTask(Task task, Date deathLine) {
        super(task);
        this.getParent().getActivities().add(this);
        this.deathLine = deathLine;
    }

    public LimitedTask(Task task, int totalSeconds) {
        super(task);
        this.totalSeconds = totalSeconds;
        this.deathLine = null;
    }

    @Override
    public void startInterval() {
        if (totalSeconds != -1) {
            this.deathLine = new Date();
            deathLine.setTime(deathLine.getTime() + 1000 * totalSeconds);
        }
        super.startInterval();
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


}
