
package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Concrete class to store data from a Detailed Report.
 * It contains all the projects, subprojects, tasks and intervals.
 */
public class DetailedReport extends Report implements TreeVisitor {


    private Container title;
    private Container subtitleReports;
    private Container reportTable;

    private Container subtitleRootProjects;
    private Container rootProjectsTable;

    private Container subtitleSubProjects;
    private Container textSubProjects;
    private Container subProjectsTable;


    private Container subtitleTasks;
    private Container textTask;
    private Container taskTable;

    private Container subtitleIntervals;
    private Container textIntervals;
    private Container intervalsTable;

    private Container footer;


    DetailedReport(final Project rootVisitable, final Period reportPeriod, final ReportGenerator reportGenerator) {
        super(rootVisitable, reportPeriod, reportGenerator);

        this.title = new Title("Detailed Report");
        this.subtitleReports = new Subtitle("Period");
        this.reportTable = createReportTable();

        this.subtitleRootProjects = new Subtitle("Root Projects");
        this.rootProjectsTable = new Table(0, 5);

        this.subtitleSubProjects = new Subtitle("Subprojects");
        this.textSubProjects = new Text("Subtitle subprojects");
        this.subProjectsTable = new Table(0, 5);

        this.subtitleTasks = new Subtitle("Task");
        this.textTask = new Text("Subtitle tasks");
        this.taskTable = new Table(0, 5);

        this.subtitleIntervals = new Subtitle("Intervals");
        this.textIntervals = new Text("Subtitle intervals");
        this.intervalsTable = new Table(0, 5);

        this.footer = new Text("Time Tracker 1.0");

        createTables();
    }

    @Override
    public void createReport() {
        this.reportGenerator.visitSeparator(new Separator());
        this.reportGenerator.visitTitle((Title) this.title);
        this.reportGenerator.visitSeparator(new Separator());

        this.reportGenerator.visitSubtitle((Subtitle) this.subtitleReports);
        this.reportGenerator.visitTable((Table) this.reportTable);
        this.reportGenerator.visitSeparator(new Separator());

        this.reportGenerator.visitSubtitle((Subtitle) this.subtitleRootProjects);
        this.reportGenerator.visitTable((Table) this.rootProjectsTable);
        this.reportGenerator.visitSeparator(new Separator());

        this.reportGenerator.visitSubtitle((Subtitle) this.subtitleSubProjects);
        this.reportGenerator.visitText((Text) this.textSubProjects);
        this.reportGenerator.visitTable((Table) this.subProjectsTable);
        this.reportGenerator.visitSeparator(new Separator());

        this.reportGenerator.visitSubtitle((Subtitle) this.subtitleTasks);
        this.reportGenerator.visitText((Text) this.textTask);
        this.reportGenerator.visitTable((Table) this.taskTable);
        this.reportGenerator.visitSeparator(new Separator());

        this.reportGenerator.visitSubtitle((Subtitle) this.subtitleIntervals);
        this.reportGenerator.visitText((Text) this.textIntervals);
        this.reportGenerator.visitTable((Table) this.intervalsTable);
        this.reportGenerator.visitSeparator(new Separator());

        this.reportGenerator.visitText((Text) footer);


    }

    private void createTables() {
        ArrayList<String> headerProjects = new ArrayList<>(Arrays.asList("Id", "Project Name", "Start Date", "End Date", "Duration"));
        ArrayList<String> headerTasks = new ArrayList<>(Arrays.asList("Id", "Task Name", "Start Date", "End Date", "Duration"));
        ArrayList<String> headerIntervals = new ArrayList<>(Arrays.asList("Id", "Task Name", "Start Date", "End Date", "Duration"));

        ((Table) this.rootProjectsTable).addRow(headerProjects);
        ((Table) this.subProjectsTable).addRow(headerProjects);
        ((Table) this.taskTable).addRow(headerTasks);
        ((Table) this.intervalsTable).addRow(headerIntervals);

        for (Node n : rootVisitable.getActivities()) {
            n.accept(this);
        }
    }


    @Override
    public void visitProject(final Project project) {
        if (isInPeriod(project.getStartDate(), project.getEndDate())) {

            for (Node n : project.getActivities()) {
                if (isInPeriod(n.getStartDate(), n.getEndDate())) {
                    n.accept(this);
                }
            }



            String name = project.getName();
            String desc = project.getDescription();
            Date startDate = getNewStartDate(project.getStartDate());
            Date endDate = getNewEndDate(project.getEndDate());
            long duration = getNewDuration(project.getStartDate(), project.getEndDate(), project.getDuration()); //ESTO NO ES REAL SIEMPRE


            ArrayList<String> entry = new ArrayList<>(Arrays.asList(name, desc,
                    startDate.toString(), endDate.toString(), String.valueOf(duration)));
            if (project.getParent().getParent() == null) {
                ((Table) this.rootProjectsTable).addRow(entry);
            } else {
                ((Table) this.subProjectsTable).addRow(entry);
            }
        }
    }

    @Override
    public void visitTask(final Task task) {

        long taskDuration = 0;
        for (Interval i : task.getIntervals()) {
            if (isInPeriod(i.getStartDate(), i.getEndDate())) {
                i.accept(this);
                taskDuration += i.getDuration();
            }
        }

        if (isInPeriod(task.getStartDate(), task.getEndDate())) {
            String name = task.getName();
            String desc = task.getDescription();
            Date startDate = getNewStartDate(task.getStartDate());
            Date endDate = getNewEndDate(task.getEndDate());
            long duration = taskDuration; //ESTO NO ES REAL SIEMPRE


            ArrayList<String> entry = new ArrayList<>(Arrays.asList(name, desc,
                    startDate.toString(), endDate.toString(), String.valueOf(duration)));
            ((Table) this.taskTable).addRow(entry);
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


            ArrayList<String> entry = new ArrayList<>(Arrays.asList(
                    name, desc, startDate.toString(),
                    endDate.toString(), String.valueOf(duration)));
            ((Table) this.intervalsTable).addRow(entry);
        }
    }

}

