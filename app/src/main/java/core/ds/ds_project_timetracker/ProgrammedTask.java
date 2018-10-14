package core.ds.ds_project_timetracker;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;


/**
 * Task decorator for making a Task be auto-startable.
 */
public class ProgrammedTask extends TaskDecorator implements Observer {

    private Date dateToStart;
    private int delay = -1;

    /**
     * Constructor with Date input
     *
     * @param baseTask    The task that decorates
     * @param dateToStart The date when the task must start
     */
    public ProgrammedTask(Task baseTask, Date dateToStart) {
        super(baseTask);
        this.dateToStart = dateToStart;
        Clock.getInstance().addObserver(this);

    }


    /**
     * Constructor with seconds input
     * @param baseTask The task that decorates
     * @param delay The time the task must wait before start.
     */

    public ProgrammedTask(Task baseTask, int delay) {
        super(baseTask);
        this.delay = delay;
        dateToStart = new Date();
        Clock.getInstance().addObserver(this);

    }

    /**
     * Method that updates data and updates the values of the baseTask
     */
    @Override
    public void updateData(Date time) {
        super.updateData(time);
        this.updateValues();
    }


    /**
     * Method that starts the task when it's time and deletes from observing the clock
     * @param o -
     * @param arg The Clock object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (this.delay != -1) {

            if (((Clock) arg).getMs() >= dateToStart.getTime() + delay * 1000) {
                this.startInterval();
                ((Clock) arg).deleteObserver(this);
            }
        } else {
            if (((Clock) arg).getTime().after(dateToStart)) {
                this.startInterval();
                ((Clock) arg).deleteObserver(this);
            }
        }

    }
}
