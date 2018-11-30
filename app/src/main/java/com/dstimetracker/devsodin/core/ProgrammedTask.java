package com.dstimetracker.devsodin.core;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;


/**
 * Task decorator for making a Task be auto-startable.
 */
public class ProgrammedTask extends TaskDecorator implements Observer {

    /**
     * Starting date of the programmed Task.
     */
    private final Date dateToStart;

    /**
     * Default delay. If it's not changed, will use the date;
     * otherwise the delay in seconds.
     */
    private int delay = -1;

    /**
     * Constructor with Date input.
     *
     * @param baseTask The task that decorates
     * @param toStart  The date when the task must start
     */
    public ProgrammedTask(final Task baseTask, final Date toStart) {
        super(baseTask);
        this.dateToStart = toStart;
        Clock.getInstance().addObserver(this);
        LOGGER.info("new Decorated task" + this.getName() + " with "
                + this.getClass().getName() + "decorator");

        this.invariant();
    }


    /**
     * Constructor with seconds input.
     *
     * @param baseTask The task that decorates
     * @param waitSecs The time the task must wait before start.
     */

    ProgrammedTask(final Task baseTask, final int waitSecs) {
        super(baseTask);
        this.delay = waitSecs;
        this.dateToStart = new Date();
        Clock.getInstance().addObserver(this);
        LOGGER.info("new Decorated task" + this.getName() + " with "
                + this.getClass().getName() + "decorator");
        this.invariant();

    }

    /**
     * Method that starts the task when it's time and
     * deletes from observing the clock.
     *
     * @param o   -
     * @param arg The Clock object
     */
    @Override
    public void update(final Observable o, final Object arg) {
        if (this.delay != -1) {

            if (((Clock) arg).getMs() >= this.dateToStart.getTime()
                    + this.delay * Clock.MS_IN_SEC) {
                ((Clock) arg).deleteObserver(this);
                super.startInterval();
            }
        } else {
            if (((Clock) arg).getTime().after(this.dateToStart)) {
                ((Clock) arg).deleteObserver(this);
                super.startInterval();
            }
        }

        this.invariant();
    }

}
