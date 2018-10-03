package core.ds.ds_project_timetracker;


import java.util.Date;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {

    public static int REFRESHRATE = 1;
    private final int msInSec = 1000;
    private Date date;
    private Timer timer;
    private TimerTask tt = null;

    private static Clock clock = null;

    private Clock() {
        this.timer = new Timer();
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
        this.timer.scheduleAtFixedRate(this.tt = new TimerTask() {
            @Override
            public void run() {
                updateClock();
            }
        }, 0, Clock.REFRESHRATE * this.msInSec);
    }

    private void updateClock() {
        date = new Date();
        setChanged();
        notifyObservers(this);
    }

    public int getRefreshTicks() {
        return REFRESHRATE;
    }

    public Date getTime() {
        return date;
    }

    public void setRefreshTicks(int secs) {
        if (secs >= 1) {
            Clock.REFRESHRATE = secs;
            System.out.println("Refresh time setted to " + REFRESHRATE);
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


}