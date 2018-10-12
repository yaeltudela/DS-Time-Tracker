package core.ds.ds_project_timetracker;

import java.util.Observable;
import java.util.Observer;

public class PrinterVisitor implements Visitor, Observer {

    private final Project rootProject;

    public PrinterVisitor(Project rootProject) {
        this.rootProject = rootProject;
    }

    @Override
    public void visitProject(Project project) {
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

    @Override
    public void visitTask(Task task) {
        System.out.println(task.toString());
        for (Interval i : task.getIntervals()) {
            i.accept(this);
        }

    }

    @Override
    public void visitInterval(Interval interval) {
        //System.out.println("\t" + interval.toString());


    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("--------------------------------------");
        this.rootProject.accept(this);
        System.out.println("--------------------------------------");

    }
}
