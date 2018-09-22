package core.ds.ds_project_timetracker;



import java.util.Date;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {

    public static int REFRESHRATEMS = 1000;
    private Date date;
    private Timer timer;

    public Clock() {
        timer = new Timer();
        date = new Date();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateClock();
            }
        }, 0, REFRESHRATEMS);
    }

    private void updateClock() {
        date = new Date();
        setChanged();
        notifyObservers(this);
    }

    public Date getTime() {
        return date;
    }

    public int getRefreshTicks() {
        return REFRESHRATEMS;
    }

    public void setRefreshTicks(int secs) {
        if (secs > 1) {
            REFRESHRATEMS = secs * 1000;
            System.out.println("Refresh time setted to 2");
        }else{
            System.out.println("Value needs to be 1 or above");
            //Throw Exception o algo
        }

    }



}