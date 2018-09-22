package core.ds.ds_project_timetracker;


import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {

    private Task parent;
    private float duration;
    private boolean isRunning;

    private Date startDate;
    private Date endDate;

    Interval(Date startDate) {
        this.startDate = startDate;
    }

    private void stopInterval() {

    }

    @Override
    public void update(Observable o, Object arg) {
        duration += Clock.REFRESHRATEMS;
    }

}

