package core.ds.ds_project_timetracker;



import java.util.Date;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {

    public static int REFRESHRATEMS = 2000;
    private Date date;
    private Timer timer = null;
    private TimerTask tt = null;

    public Clock() {
        this.timer = new Timer();
        date = new Date();
        setupTimer();
    }

    private void setupTimer() {
        this.timer.scheduleAtFixedRate(tt = new TimerTask() {
            @Override
            public void run() {
                updateClock();
            }
        }, 0, Clock.REFRESHRATEMS);
    }

    private void updateClock() {
        date = new Date();
        setChanged();
        notifyObservers(this);
    }

    public void setRefreshTicks(int secs) {
        if (secs >= 1) {
            REFRESHRATEMS = secs * 1000;
            System.out.println("Refresh time setted to " + REFRESHRATEMS);
            if (this.timer != null) {
                this.tt.cancel();
                setupTimer();
            }
        } else {
            System.out.println("Value needs to be 1 or above");
            //Throw Exception o algo
        }

    }

    public Date getTime() {
        return date;
    }

    public int getRefreshTicks() {
        return REFRESHRATEMS;
    }



}