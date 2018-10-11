package core.ds.ds_project_timetracker;

import java.util.Date;

public class ProgrammedTask extends TaskDecorator {
    public ProgrammedTask(Task baseTask) {
        super(baseTask);
    }


    @Override
    public void startInterval() {

    }

    @Override
    public void stopInterval() {

    }


    @Override
    public void updateData(Date time) {

    }

}
