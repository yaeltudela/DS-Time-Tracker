package core.ds.ds_project_timetracker;

import java.util.Date;

/**
 * Task decorator for making a Task be auto-stoppable.
 */
public class LimitedTask extends TaskDecorator {

    private Date deathLine = null;
    private int totalSeconds = -1;


    /**
     * Constructor with Date input
     *
     * @param baseTask  The task that decorates
     * @param deathLine The date when the task must stop
     */
    public LimitedTask(Task baseTask, Date deathLine) {
        super(baseTask);
        this.deathLine = deathLine;
    }


    /**
     * Constructor with seconds input
     *
     * @param baseTask     The task that decorates
     * @param totalSeconds The maximum time that the task will be running
     */
    LimitedTask(Task baseTask, int totalSeconds) {
        super(baseTask);
        this.totalSeconds = totalSeconds;
    }

    /**
     * Method that checks if the deathline has been passed to stop the Task
     * @param time time to do the update. Usually the actual Clock time
     */
    @Override
    public void updateData(Date time) {
        if (deathLine == null) {
            totalSeconds -= Clock.REFRESH_RATE;
            if (this.totalSeconds < 0) {
                this.stopInterval();
            } else {
                super.updateData(time);
            }
        } else {
            if (this.endDate.after(this.deathLine)) {
                this.stopInterval();
            } else {
                super.updateData(time);
            }
        }
    }
}

