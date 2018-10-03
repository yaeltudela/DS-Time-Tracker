package core.ds.ds_project_timetracker;

public interface Visitor {
    void visitProject(Project project);

    void visitTask(Task task);

    void visitInterval(Interval interval);

}
