package core.ds.ds_project_timetracker;


import java.util.Date;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {

    public static int REFRESH_RATE = 1;
    private Date date;
    private Timer timer;
    private TimerTask tt = null;

    private static Clock clock = null;

    private Clock() {
        this.timer = new Timer(true);
        date = new Date();
        setupTimer();
    }

    public static Clock getInstance() {
        if (Clock.clock == null) {
            Clock.clock = new Clock();
        }
        return Clock.clock;
    }



    private void setupTimer() {
        int msInSec = 1000;
        this.timer.scheduleAtFixedRate(this.tt = new TimerTask() {
            @Override
            public void run() {
                updateClock();
            }
        }, 0, Clock.REFRESH_RATE * msInSec);
    }

    private void updateClock() {
        date = new Date();
        setChanged();
        notifyObservers(this);
    }

    public int getRefreshTicks() {
        return REFRESH_RATE;
    }

    public Date getTime() {
        return date;
    }

    public void setRefreshTicks(int secs) {
        if (secs >= 1) {
            Clock.REFRESH_RATE = secs;
            System.out.println("Refresh time has been set to " + REFRESH_RATE);
            if (this.timer != null) {
                this.tt.cancel();
                setupTimer();
            }
        } else {
            System.out.println("Value needs to be 1 or above");
            throw new IllegalArgumentException();
        }

    }

    public void stopClock() {
        this.tt.cancel();
        this.timer.cancel();
    }

    public long getMs() {
        return clock.getTime().getTime();
    }


}