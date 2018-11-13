package core.ds.ds_project_timetracker;

/**
 * Interface for generalization of the different Visitors.
 * Contains a visitX method for all the Visitable classes
 */
public interface TreeVisitor extends Visitor {
    /**
     * Abstract method to visit a Project.
     *
     * @param project The project to visit
     */
    void visitProject(Project project);

    /**
     * Abstract method to visit a Task.
     *
     * @param task The task to visit
     */
    void visitTask(Task task);

    /**
     * Abstract method to visit a Interval.
     *
     * @param interval The interval to visit
     */
    void visitInterval(Interval interval);

}
