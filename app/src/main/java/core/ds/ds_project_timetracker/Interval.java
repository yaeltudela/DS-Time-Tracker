package core.ds.ds_project_timetracker;


import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer, Serializable, Visitable {

    private Task parentTask;
    private Date startDate;
    private Date endDate;
    private long duration;

    public Interval(Date startDate, Task parentTask) {
        this.duration = 0;
        this.startDate = startDate;
        this.endDate = null;
        this.parentTask = parentTask;
        this.parentTask.setActive(true);

    }

    //Constructor para intervalos manuales
    public Interval(Date startDate, Date endDate, Task parentTask) {
        this.parentTask = parentTask;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = this.endDate.getTime() - this.startDate.getTime();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.endDate = ((Clock) arg).getTime();
        this.duration += Clock.REFRESHRATE;
        this.parentTask.updateData(this.endDate);
    }


    //Getters & Setters

    public long getDuration() {
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


    @Override
    public void accept(Visitor visitor) {
        visitor.visitInterval(this);

    }

    @Override
    public String toString() {
        return "\t" + this.parentTask.getName() + " Interval \t" + this.duration;
    }
}

