package core.ds.ds_project_timetracker;

import java.util.Date;

/**
 * Task decorator for making a Task be auto-stoppable.
 */
public class LimitedTask extends TaskDecorator {

    /**
     * Maximum date to end the task.
     */
    private Date deathLine = null;
    /**
     * Maximum seconds that the task will be running.
     */
    private int totalSeconds = -1;


    /**
     * Constructor with Date input.
     *
     * @param baseTask The task that decorates
     * @param maxDate  The date when the task must stop
     */
    public LimitedTask(final Task baseTask, final Date maxDate) {
        super(baseTask);
        this.deathLine = maxDate;
        LOGGER.info("new Decorated task" + this.getName() + " with " + this.getClass().getName() + "decorator");

    }


    /**
     * Constructor with seconds input.
     *
     * @param baseTask   The task that decorates
     * @param maxSeconds The maximum time that the task will be running
     */
    LimitedTask(final Task baseTask, final int maxSeconds) {
        super(baseTask);
        this.totalSeconds = maxSeconds;
        LOGGER.info("new Decorated task" + this.getName() + " with " + this.getClass().getName() + "decorator");

    }

    /**
     * Method that checks if the deathline has been passed to stop the Task.
     *
     * @param time time to do the update. Usually the actual Clock time
     */
    @Override
    public void updateData(final Date time) {
        if (deathLine == null) {
            totalSeconds -= Clock.REFRESHRATE;
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

