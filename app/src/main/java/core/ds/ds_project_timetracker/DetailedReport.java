package core.ds.ds_project_timetracker;

import java.util.Date;

/**
 * Concrete class to store data from a Detailed Report.
 * It contains all the projects, subprojects, tasks and intervals.
 */
public class DetailedReport extends Report implements TreeVisitor {


    private final Container subtitleSubProjects;
    private final Container textSubProjects;
    private final Container subProjectsTable;
    private final Container subtitleTasks;
    private final Container textTask;
    private final Container taskTable;
    private final Container subtitleIntervals;
    private final Container textIntervals;
    private final Container intervalsTable;
    private final Container footer;

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
        this.intervalsTable = new Table(0, 6);

        this.footer = new Text("Time Tracker 1.0");

        createTables();
        fillTables();


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

        createTables();

    }

    @Override
    protected void createTables() {
        this.createRootProjectTables();
        this.createSubProjectsTable();
        this.createTaskTable();
        this.createIntervalTable();
    }


    private void createIntervalTable() {
        ((Table) this.intervalsTable).addRow();
        ((Table) this.intervalsTable).setCell(0, 0, "Project Id");
        ((Table) this.intervalsTable).setCell(0, 1, "Task  Name");
        ((Table) this.intervalsTable).setCell(0, 2, "Id");
        ((Table) this.intervalsTable).setCell(0, 3, "Start Date");
        ((Table) this.intervalsTable).setCell(0, 4, "End Date");
        ((Table) this.intervalsTable).setCell(0, 5, "Duration");

    }

    private void createTaskTable() {
        ((Table) this.taskTable).addRow();
        ((Table) this.taskTable).setCell(0, 0, "Id");
        ((Table) this.taskTable).setCell(0, 1, "Task Name");
        ((Table) this.taskTable).setCell(0, 2, "Start Date");
        ((Table) this.taskTable).setCell(0, 3, "End Date");
        ((Table) this.taskTable).setCell(0, 4, "Duration");
    }

    private void createSubProjectsTable() {
        ((Table) this.subProjectsTable).addRow();
        ((Table) this.subProjectsTable).setCell(0, 0, "Id");
        ((Table) this.subProjectsTable).setCell(0, 1, "Project Name");
        ((Table) this.subProjectsTable).setCell(0, 2, "Start Date");
        ((Table) this.subProjectsTable).setCell(0, 3, "End Date");
        ((Table) this.subProjectsTable).setCell(0, 4, "Duration");
    }


    @Override
    public void visitProject(final Project project) {
        long acc = this.currentDuration;
        this.currentDuration = 0;
        if (isOnPeriod(project.getStartDate(), project.getEndDate())) {

            long projectduration = 0;
            for (Node n : project.getActivities()) {
                if (isOnPeriod(n.getStartDate(), n.getEndDate())) {
                    n.accept(this);
                }
            }

            if (project.isRootNode()) {
                ((Table) this.rootProjectsTable).addRow();
                int index = ((Table) this.rootProjectsTable).getRows() - 1;

                ((Table) this.rootProjectsTable).setCell(index, 0, project.getId().getId());
                ((Table) this.rootProjectsTable).setCell(index, 1, project.getName());
                ((Table) this.rootProjectsTable).setCell(index, 2, calcStartDate(project.getStartDate(), project.getEndDate()).toString());
                ((Table) this.rootProjectsTable).setCell(index, 3, calcEndDate(project.getStartDate(), project.getEndDate()).toString());
                ((Table) this.rootProjectsTable).setCell(index, 4, String.valueOf(this.currentDuration));
            } else {
                ((Table) this.subProjectsTable).addRow();
                int index = ((Table) this.subProjectsTable).getRows() - 1;

                ((Table) this.subProjectsTable).setCell(index, 0, project.getId().getId());
                ((Table) this.subProjectsTable).setCell(index, 1, project.getName());
                ((Table) this.subProjectsTable).setCell(index, 2, calcStartDate(project.getStartDate(), project.getEndDate()).toString());
                ((Table) this.subProjectsTable).setCell(index, 3, calcEndDate(project.getStartDate(), project.getEndDate()).toString());
                ((Table) this.subProjectsTable).setCell(index, 4, String.valueOf(this.currentDuration));
            }
        }


        this.currentDuration = acc;
    }

    @Override
    public void visitTask(final Task task) {
        long taskDuration = 0;
        if (isOnPeriod(task.getStartDate(), task.getEndDate())) {

            for (Interval i : task.getIntervals()) {
                if (isOnPeriod(i.getStartDate(), i.getEndDate())) {
                    i.accept(this);
                    long addDuration = calcDuration(i.getStartDate(), i.getEndDate(), i.getDuration());
                    if (addDuration >= Clock.REFRESHRATE) {
                        taskDuration += addDuration;
                    }
                }
            }
        }


        ((Table) this.taskTable).addRow();
        int index = ((Table) this.taskTable).getRows() - 1;
        ((Table) this.taskTable).setCell(index, 0, task.getId().getId());
        ((Table) this.taskTable).setCell(index, 1, task.getName());
        ((Table) this.taskTable).setCell(index, 2, calcStartDate(task.getStartDate(), task.getEndDate()).toString());
        ((Table) this.taskTable).setCell(index, 3, calcEndDate(task.getStartDate(), task.getEndDate()).toString());
        ((Table) this.taskTable).setCell(index, 4, String.valueOf(taskDuration));

        this.currentDuration += taskDuration;

    }

    @Override
    public void visitInterval(final Interval interval) {
        Date intervalStartDate = calcStartDate(interval.getStartDate(), interval.getEndDate());
        Date intervalEndDate = calcEndDate(interval.getStartDate(), interval.getEndDate());
        long intervalDuration = calcDuration(interval.getStartDate(), interval.getEndDate(), interval.getDuration());

        if (intervalDuration > Clock.REFRESHRATE) {
            ((Table) this.intervalsTable).addRow();
            int index = ((Table) this.intervalsTable).getRows() - 1;

            ((Table) this.intervalsTable).setCell(index, 0, interval.getParentTask().getParent().getId().getId()); //TODO PROJECT ID
            ((Table) this.intervalsTable).setCell(index, 1, interval.getParentTask().getName());
            ((Table) this.intervalsTable).setCell(index, 2, interval.getId().getId()); //TODO CREATE ID
            ((Table) this.intervalsTable).setCell(index, 3, intervalStartDate.toString());
            ((Table) this.intervalsTable).setCell(index, 4, intervalEndDate.toString());
            ((Table) this.intervalsTable).setCell(index, 5, String.valueOf(intervalDuration));
        }
    }

}

