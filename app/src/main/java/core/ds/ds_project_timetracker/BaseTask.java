package core.ds.ds_project_timetracker;


public class BaseTask extends Task {

    /**
     * BaseTask constructor. Calls the Task constructor and adds itself to it's parent activities
     *
     * @param name        BaseTask's name
     * @param description BaseTask's description
     * @param project     BaseTask's parent project
     */
    public BaseTask(String name, String description, Project project) {
        super(name, description, project);
        this.getParent().getActivities().add(this);
    }

    public BaseTask() {

    }


}
