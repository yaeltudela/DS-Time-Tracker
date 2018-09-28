package core.ds.ds_project_timetracker;


import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {

    private boolean active;

    private Task parent;
    private Date startDate;
    private Date endDate;
    private float duration;

    Interval(Date startDate, Task parent) {
        this.duration = 0;
        this.startDate = startDate;
        this.endDate = null;
        this.parent = parent;

    }

    @Override
    public void update(Observable o, Object arg) {
        this.duration += Clock.REFRESHRATE;
        this.endDate = ((Clock) arg).getTime();
        this.parent.updateData(((Clock) arg).getTime());
    }


    //Getters & Setters

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

    public void setActive(boolean state) {
        this.active = state;
    }
}

