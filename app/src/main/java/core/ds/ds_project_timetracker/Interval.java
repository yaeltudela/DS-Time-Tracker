package core.ds.ds_project_timetracker;


import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer, Serializable {

    private Task parentTask;
    private Date startDate;
    private Date endDate;
    private float duration;

    Interval(Date startDate, Task parentTask) {
        this.duration = 0;
        this.startDate = startDate;
        this.endDate = null;
        this.parentTask = parentTask;
        this.parentTask.setActive(true);

    }

    @Override
    public void update(Observable o, Object arg) {
        this.duration += Clock.REFRESHRATE;
        this.endDate = ((Clock) arg).getTime();
        this.parentTask.updateData(((Clock) arg).getTime());
    }


    //Getters & Setters

    public float getDuration() {
        return this.duration;
    }

    public Task getParentTask() {
        return this.parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }


}

