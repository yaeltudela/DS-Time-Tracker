package core.ds.ds_project_timetracker;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class ProgrammedTask extends TaskDecorator implements Observer {

    private Date dateToStart;

    public ProgrammedTask(Task baseTask, Date dateToStart) {
        super(baseTask);
        this.dateToStart = dateToStart;
        Clock.getInstance().addObserver(this);

    }

    @Override
    public void updateData(Date time) {
        super.updateData(time);
        this.updateValues();
    }


    @Override
    public void update(Observable o, Object arg) {
        if (Clock.getInstance().getTime().after(dateToStart)) {
            System.out.println("HE EMPEZADO");
            this.startInterval();
            Clock.getInstance().deleteObserver(this);
        }
    }
}
