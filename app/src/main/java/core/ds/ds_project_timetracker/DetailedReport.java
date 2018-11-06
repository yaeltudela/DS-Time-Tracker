
package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Concrete class to store data from a Detailed Report.
 * It contains all the projects, subprojects, tasks and intervals.
 */
public class DetailedReport extends Report implements Visitor {


    private Table subrojectsTable;
    private Table tasksTable;
    private Table intervalsTable;

    protected DetailedReport(Project rootVisitable, Period reportPeriod) {
        super(rootVisitable, reportPeriod);

        createSeparatorTable();
        createSectionTable("Detailed Report", null);
        createSeparatorTable();

        createCommonTables();
        createSubprojectTable();
        createTaskTable();
        createIntervalsTable();


    }

    private void createSubprojectTable() {
        createSectionTable("Subprojects", "Subtitle");

        this.subrojectsTable = new Table(0, 4);
        this.tables.add(this.subrojectsTable);
        ArrayList<String> subProjectHeader = new ArrayList<>(Arrays.asList("Name", "Start Date", "End Date", "Duration"));

        this.subrojectsTable.addRow(subProjectHeader);
        createSeparatorTable();
    }

    private void createTaskTable() {
        createSectionTable("Tasks", "Subtitle");
        this.tasksTable = new Table(0, 5);
        this.tables.add(this.tasksTable);
        ArrayList<String> tasksHeader = new ArrayList<>(Arrays.asList("Name", "Start Date", "End Date", "Duration"));

        this.tasksTable.addRow(tasksHeader);
        createSeparatorTable();
    }

    private void createIntervalsTable() {
        createSectionTable("Intervals", "Subtitle");
        this.intervalsTable = new Table(0, 6);
        this.tables.add(this.intervalsTable);
        ArrayList<String> intervalsHeaders = new ArrayList<>(Arrays.asList("Name", "Start Date", "End Date", "Duration"));
        this.intervalsTable.addRow(intervalsHeaders);
        createSeparatorTable();
    }

    @Override
    public void visitProject(final Project project) {

        for (Node n : project.getActivities()) {
            n.accept(this);
        }

        if (isInPeriod(project.getStartDate(), project.getEndDate())) {
            String name = project.getName();
            String desc = project.getDescription();
            Date startDate = getNewStartDate(project.getStartDate());
            Date endDate = getNewEndDate(project.getEndDate());
            long duration = getNewDuration(project.getStartDate(), project.getEndDate(), project.getDuration()); //ESTO NO ES REAL SIEMPRE


            ArrayList<String> entry = new ArrayList<>(Arrays.asList(name, desc,
                    startDate.toString(), endDate.toString(), String.valueOf(duration)));
            if (project.getParent().getParent() == null) {
                this.rootProjectsTable.addRow(entry);
            } else {
                this.subrojectsTable.addRow(entry);
            }
        }
    }

    @Override
    public void visitTask(final Task task) {

        for (Interval i : task.getIntervals()) {
            i.accept(this);
        }

        if (isInPeriod(task.getStartDate(), task.getEndDate())) {
            String name = task.getName();
            String desc = task.getDescription();
            Date startDate = getNewStartDate(task.getStartDate());
            Date endDate = getNewEndDate(task.getEndDate());
            long duration = getNewDuration(task.getStartDate(), task.getEndDate(), task.getDuration()); //ESTO NO ES REAL SIEMPRE


            ArrayList<String> entry = new ArrayList<>(Arrays.asList(name, desc,
                    startDate.toString(), endDate.toString(), String.valueOf(duration)));
            this.tasksTable.addRow(entry);
        }
    }

    @Override
    public void visitInterval(final Interval interval) {
        if (isInPeriod(interval.getStartDate(), interval.getEndDate())) {
            String name = interval.getParentTask().getName() + "_interval";
            String desc = interval.getParentTask().getDescription() + "_interval";
            Date startDate = getNewStartDate(interval.getStartDate());
            Date endDate = getNewEndDate(interval.getEndDate());
            long duration = getNewDuration(interval.getStartDate(), interval.getEndDate(), interval.getDuration()); //ESTO NO ES REAL SIEMPRE


            ArrayList<String> entry = new ArrayList<>(Arrays.asList(name, desc,
                    startDate.toString(), endDate.toString(), String.valueOf(duration)));
            this.intervalsTable.addRow(entry);
        }
    }


}
