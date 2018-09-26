package core.ds.ds_project_timetracker;


import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {

    private Task parent;
    private float duration;
    private boolean active;

    private Date startDate;
    private Date endDate;

    Interval(Date startDate, Task parent) {
        this.active = true;
        this.duration = 0;
        this.startDate = startDate;
        this.endDate = null;
        this.parent = parent;
        if (this.parent.getParent().startDate == null) {
            this.parent.getParent().startDate = startDate;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Clock c = (Clock) arg;
        this.duration += Clock.REFRESHRATEMS / 1000.0;
        this.endDate = c.getTime();
        this.parent.endDate = c.getTime();
        this.parent.getParent().endDate = c.getTime();
    }


    public float getDuration() {
        return this.duration;
    }

    public void setParent(Task parent) {
        this.parent = parent;
    }


    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }
}

