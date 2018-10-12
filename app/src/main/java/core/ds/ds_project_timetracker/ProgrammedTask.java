package core.ds.ds_project_timetracker;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class ProgrammedTask extends TaskDecorator implements Observer {

    private Date dateToStart = null;
    private int delay = -1;

    public ProgrammedTask(Task baseTask, Date dateToStart) {
        super(baseTask);
        this.dateToStart = dateToStart;
        Clock.getInstance().addObserver(this);

    }

    public ProgrammedTask(Task baseTask, int delay) {
        super(baseTask);
        this.delay = delay;
        dateToStart = new Date();
        Clock.getInstance().addObserver(this);

    }

    @Override
    public void updateData(Date time) {
        super.updateData(time);
        this.updateValues();
    }


    @Override
    public void update(Observable o, Object arg) {
        if (this.delay != -1) {

            if (Clock.getInstance().getMs() >= dateToStart.getTime() + delay * 1000) {
                this.startInterval();
                Clock.getInstance().deleteObserver(this);
            }
        } else {
            if (Clock.getInstance().getTime().after(dateToStart)) {
                this.startInterval();
                Clock.getInstance().deleteObserver(this);
            }
        }

    }
}
