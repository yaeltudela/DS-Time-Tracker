package core.ds.ds_project_timetracker;

import java.util.Date;

/**
 * Task decorator for making a Task be auto-stoppable.
 */
public class LimitedTask extends TaskDecorator {

    private Date deathLine;
    private int totalSeconds = -1;


    /**
     * Constructor with Date input
     *
     * @param baseTask  The task that decorates
     * @param deathLine The date when the task must stop
     */
    public LimitedTask(Task baseTask, Date deathLine) {
        super(baseTask);
        this.getParent().getActivities().add(this);
        this.deathLine = deathLine;
    }


    /**
     * Constructor with seconds input
     *
     * @param baseTask     The task that decorates
     * @param totalSeconds The maximum time that the task will be running
     */
    public LimitedTask(Task baseTask, int totalSeconds) {
        super(baseTask);
        this.totalSeconds = totalSeconds;
        this.deathLine = null;
    }

    /**
     * Method that checks if the decorator has been instantiated with the totalseconds constructor
     * (and sets up the deathLine date) and call the parent method
     */
    @Override
    public void startInterval() {
        if (totalSeconds != -1) {
            this.deathLine = new Date();
            deathLine.setTime(deathLine.getTime() + 1000 * totalSeconds);
        }
        super.startInterval();
    }


    /**
     * Method that checks if the deathline has been passed to stop the Task
     * @param time time to do the update. Usually the actual Clock time
     */
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
