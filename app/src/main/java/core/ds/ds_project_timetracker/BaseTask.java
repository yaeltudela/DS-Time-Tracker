package core.ds.ds_project_timetracker;

import java.util.ArrayList;

public class BaseTask extends Task {

    public BaseTask(String name, String description, Project project) {
        super(name, description, project);

        this.intervals = new ArrayList<>();
        this.parent = project;
        this.startDate = null;
        this.endDate = null;
        this.active = false;

        project.getActivities().add(this);
    }


}
