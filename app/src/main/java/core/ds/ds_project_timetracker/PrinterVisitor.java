package core.ds.ds_project_timetracker;

import java.util.Observable;
import java.util.Observer;

/**
 * Concrete Class for printing all the visitable class starting from a root.
 */
public class PrinterVisitor implements Visitor, Observer {

    private final Project rootVisitable;


    /**
     * Default Constructor.
     *
     * @param rootProject The starter visitable object
     */
    PrinterVisitor(final Project rootProject) {
        this.rootVisitable = rootProject;
    }

    /**
     * Method to print Project objects. It prints the project data and* visit's
     * all of his sons.
     * @param project The project to visit
     */
    @Override
    public void visitProject(final Project project) {
        System.out.println(project.toString());
        for (Node n : project.getActivities()) {
            if (n instanceof Task) {
                Task task = (Task) n;
                task.accept(this);
            } else {
                Project subProject = (Project) n;
                subProject.accept(this);
            }

        }

    }

    /**
     * Method to print Task objects. It prints the task data and visit's all of his intervals.
     * @param task The task to visit
     */
    @Override
    public void visitTask(final Task task) {
        System.out.println(task.toString());
        for (Interval i : task.getIntervals()) {
            i.accept(this);
        }

    }

    /**
     * Method to print Interval objects. It prints the Interval data.
     * @param interval The Interval to visit
     */
    @Override
    public void visitInterval(final Interval interval) {
        //System.out.println("\t" + interval.toString());


    }


    /**
     * Method that calls the rootVisitable accept to print all the data.
     * @param o The Observable object
     * @param arg The Clock object
     */
    @Override
    public void update(final Observable o, final Object arg) {
        System.out.println("--------------------------------------");
        this.rootVisitable.accept(this);
        System.out.println("--------------------------------------");

    }
}
