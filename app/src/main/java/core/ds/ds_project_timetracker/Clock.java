
package core.ds.ds_project_timetracker;


import java.util.Date;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Clock used for controlling time inside the project.
 * Observable Singleton.
 * It refreshes with a settable rate.
 */
public final class Clock extends Observable {
    /**
     * Create the timer in another thread and is setup.
     */

    public static final int MS_IN_SEC = 1000;
    /**
     * Frequency (in seconds) used to update the clock.
     */
    public static int REFRESHRATE = 1;
    /**
     * the Clock instance
     */
    private static Clock clock = null;
    /**
     * the Clock date
     */
    private Date date;
    /**
     * Timer used to schedule the updates.
     */
    private Timer timer;
    /**
     * Task that runs on a thread.
     */
    private TimerTask tt = null;

    private Clock() {
        this.timer = new Timer(true);
        this.date = new Date();
        setupTimer();
    }

    /**
     * The invoker of the class.
     * Whenever is called it returns the clock.
     * The first time, in adition of the return, it creates the clock first.
     *
     * @return the Clock instance
     */
    public static synchronized Clock getInstance() {
        if (Clock.clock == null) {
            Clock.clock = new Clock();
        }
        return Clock.clock;
    }

    /**
     * Set up the timer to refresh with the user Refresh
     * rate to call an update method with this
     * periodicity
     */
    private void setupTimer() {
        this.timer.scheduleAtFixedRate(this.tt = new TimerTask() {
            @Override
            public void run() {
                updateClock();
            }
        }, 0, Clock.REFRESHRATE * Clock.MS_IN_SEC);
    }


    /**
     * Updates his date with the actual date and notify all the observers.
     */
    private void updateClock() {
        this.setTime(new Date());
        setChanged();
        notifyObservers(this);
    }


    /**
     * Method to set up the refresh rate (checking min
     * value) and re-setup the clock with the new configuration.
     *
     * @param secs the seconds wanted for refresh rate
     */
    public void setRefreshTicks(final int secs) {
        if (secs >= 1) {
            Clock.REFRESHRATE = secs;
            System.out.println("Refresh time has been set to " + REFRESHRATE);
            if (this.timer != null) {
                this.tt.cancel();
                setupTimer();
            }
        } else {
            throw new IllegalArgumentException("Value needs to be 1 or above");
        }

    }

    /**
     * Method that interupts the thread and cancel the task.
     */
    public void stopClock() {
        this.tt.cancel();
        this.timer.cancel();
    }


    /**
     * Method to get the date in Date format.
     *
     * @return Date object with clock's date
     */
    public Date getTime() {
        return date;
    }

    private void setTime(final Date date) {
        this.date = date;
    }

    /**
     * Method to get the date in ms (long).
     *
     * @return long with clock's date
     */
    public long getMs() {
        return clock.getTime().getTime();
    }
}
