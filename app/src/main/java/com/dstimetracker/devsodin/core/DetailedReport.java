package com.dstimetracker.devsodin.core;

import java.util.Date;

/**
 * Concrete class to store data from a Detailed Report.
 * It contains all the projects, subProjects, tasks and intervals.
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

    /**
     * Constructor for Detailed report. It calls the Report constructor and
     * adds the rest of the tables.
     *
     * @param rootVisitable   The first node to visit.
     * @param reportPeriod    Period associated to the period.
     * @param reportGenerator Strategy used to generate the report.
     */
    DetailedReport(final Project rootVisitable, final Period reportPeriod,
                   final ReportGenerator reportGenerator) {
        super(rootVisitable, reportPeriod, reportGenerator);

        this.title = new Title("Detailed Report");

        this.subtitleRootProjects = new Subtitle("Root Projects");
        this.rootProjectsTable = new Table(0, FIVE);

        this.subtitleSubProjects = new Subtitle("SubProjects");
        this.textSubProjects = new Text("Subtitle subProjects");
        this.subProjectsTable = new Table(0, FIVE);

        this.subtitleTasks = new Subtitle("Task");
        this.textTask = new Text("Subtitle tasks");
        this.taskTable = new Table(0, FIVE);

        this.subtitleIntervals = new Subtitle("Intervals");
        this.textIntervals = new Text("Subtitle intervals");
        this.intervalsTable = new Table(0, SIX);

        createTables();
        fillTables();
    }

    /**
     * Method thar visits all the elements of the report.
     */
    @Override
    public void createReport() {
        this.reportGenerator.visitSeparator(new Separator());
        this.reportGenerator.visitTitle((Title) this.title);
        this.reportGenerator.visitSeparator(new Separator());

        this.reportGenerator.visitSubtitle((Subtitle)
                this.subtitleReports);
        this.reportGenerator.visitTable((Table) this.reportTable);
        this.reportGenerator.visitSeparator(new Separator());

        this.reportGenerator.visitSubtitle((Subtitle)
                this.subtitleRootProjects);
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

    /**
     * Method that creates the necessary tables for a detailed Report.
     */
    @Override
    protected void createTables() {
        this.createRootProjectTables();
        this.createSubProjectsTable();
        this.createTaskTable();
        this.createIntervalTable();
    }

    /**
     * Method that adds the corresponding headers to SubProjects Table.
     */
    private void createSubProjectsTable() {
        ((Table) this.subProjectsTable).addRow();
        ((Table) this.subProjectsTable).setCell(ZERO, ZERO, "Id");
        ((Table) this.subProjectsTable).setCell(ZERO, ONE, "Project Name");
        ((Table) this.subProjectsTable).setCell(ZERO, TWO, "Start Date");
        ((Table) this.subProjectsTable).setCell(ZERO, THREE, "End Date");
        ((Table) this.subProjectsTable).setCell(ZERO, FOUR, "Duration");
    }

    /**
     * Method that adds the corresponding headers to Task Table.
     */
    private void createTaskTable() {
        ((Table) this.taskTable).addRow();
        ((Table) this.taskTable).setCell(ZERO, ZERO, "Id");
        ((Table) this.taskTable).setCell(ZERO, ONE, "Task Name");
        ((Table) this.taskTable).setCell(ZERO, TWO, "Start Date");
        ((Table) this.taskTable).setCell(ZERO, THREE, "End Date");
        ((Table) this.taskTable).setCell(ZERO, FOUR, "Duration");
    }

    /**
     * Method that adds the corresponding headers to Interval Table.
     */
    private void createIntervalTable() {
        ((Table) this.intervalsTable).addRow();
        ((Table) this.intervalsTable).setCell(ZERO, ZERO, "Project Id");
        ((Table) this.intervalsTable).setCell(ZERO, ONE, "Task  Name");
        ((Table) this.intervalsTable).setCell(ZERO, TWO, "Id");
        ((Table) this.intervalsTable).setCell(ZERO, THREE, "Start Date");
        ((Table) this.intervalsTable).setCell(ZERO, FOUR, "End Date");
        ((Table) this.intervalsTable).setCell(ZERO, FIVE, "Duration");

    }


    /**
     * Method that visits a Project.
     * It uses the currentDuration variable to store the duration
     * between project and subProjects.
     * If needs to be visited (is inside period) it visit all the task getting
     * the real duration stored on currentDuration.
     *
     * @param project The project to visit
     */
    @Override
    public void visitProject(final Project project) {
        long acc = Report.currentDuration;
        Report.currentDuration = 0;
        if (isOnPeriod(project.getStartDate(), project.getEndDate())) {

            for (Node n : project.getActivities()) {
                if (isOnPeriod(n.getStartDate(), n.getEndDate())) {
                    n.accept(this);
                }
            }

            String id = project.getIdName();
            String name = project.getName();
            String startDate = calcStartDate(project.getStartDate(),
                    project.getEndDate()).toString();
            String endDate = calcEndDate(project.getStartDate(),
                    project.getEndDate()).toString();
            String duration = String.valueOf(Report.currentDuration);

            if (project.isRootNode()) {
                ((Table) this.rootProjectsTable).addRow();
                int index = ((Table) this.rootProjectsTable).getRows() - 1;

                ((Table) this.rootProjectsTable).setCell(index, ZERO, id);
                ((Table) this.rootProjectsTable).setCell(index, ONE, name);
                ((Table) this.rootProjectsTable).setCell(index, TWO, startDate);
                ((Table) this.rootProjectsTable).setCell(index, THREE, endDate);
                ((Table) this.rootProjectsTable).setCell(index, FOUR, duration);
            } else {
                ((Table) this.subProjectsTable).addRow();
                int index = ((Table) this.subProjectsTable).getRows() - 1;

                ((Table) this.subProjectsTable).setCell(index, ZERO, id);
                ((Table) this.subProjectsTable).setCell(index, ONE, name);
                ((Table) this.subProjectsTable).setCell(index, TWO, startDate);
                ((Table) this.subProjectsTable).setCell(index, THREE, endDate);
                ((Table) this.subProjectsTable).setCell(index, FOUR, duration);
            }
        }
        Report.currentDuration = acc;
    }

    /**
     * Method that visits a task.
     * If the task is on period, visit all of his intervals and
     * gets it's duration.
     * After that, it calculates the startDate and endDate and
     * add the data to the table.
     * After adding to table, it stores the real duration in class
     *
     * @param task The task to visit
     */
    @Override
    public void visitTask(final Task task) {
        long taskDuration = 0;
        if (isOnPeriod(task.getStartDate(), task.getEndDate())) {

            for (Interval i : task.getIntervals()) {
                if (isOnPeriod(i.getStartDate(), i.getEndDate())) {
                    i.accept(this);
                    long addDuration = calcDuration(i.getStartDate(),
                            i.getEndDate(), i.getDuration());
                    if (addDuration >= Clock.nREFRESHRATE) {
                        taskDuration += addDuration;
                    }
                }
            }
        }

        String id = task.getIdName();
        String name = task.getName();
        String startDate = calcStartDate(task.getStartDate(),
                task.getEndDate()).toString();
        String endDate = calcEndDate(task.getStartDate(),
                task.getEndDate()).toString();
        String duration = String.valueOf(taskDuration);

        ((Table) this.taskTable).addRow();
        int index = ((Table) this.taskTable).getRows() - 1;
        ((Table) this.taskTable).setCell(index, ZERO, id);
        ((Table) this.taskTable).setCell(index, ONE, name);
        ((Table) this.taskTable).setCell(index, TWO, startDate);
        ((Table) this.taskTable).setCell(index, THREE, endDate);
        ((Table) this.taskTable).setCell(index, FOUR, duration);

        Report.currentDuration += taskDuration;

    }

    /**
     * Method that visits an interval.
     * It recalculates the startDate, endDate and duration according to
     * the associated Period.
     * After getting the correct data, adds it to the corresponding table.
     *
     * @param interval The interval to visit
     */
    @Override
    public void visitInterval(final Interval interval) {
        Date intervalStartDate = calcStartDate(interval.getStartDate(),
                interval.getEndDate());
        Date intervalEndDate = calcEndDate(interval.getStartDate(),
                interval.getEndDate());
        long intervalDuration = calcDuration(interval.getStartDate(),
                interval.getEndDate(), interval.getDuration());

        if (intervalDuration > Clock.nREFRESHRATE) {

            String projectId = interval.getParentTask().getParent().getIdName();
            String name = interval.getParentTask().getName();
            String id = interval.getIdName();
            String startDate = intervalStartDate.toString();
            String endDate = intervalEndDate.toString();
            String duration = String.valueOf(intervalDuration);

            ((Table) this.intervalsTable).addRow();
            int index = ((Table) this.intervalsTable).getRows() - 1;

            ((Table) this.intervalsTable).setCell(index, ZERO, projectId);
            ((Table) this.intervalsTable).setCell(index, ONE, name);
            ((Table) this.intervalsTable).setCell(index, TWO, id);
            ((Table) this.intervalsTable).setCell(index, THREE, startDate);
            ((Table) this.intervalsTable).setCell(index, FOUR, endDate);
            ((Table) this.intervalsTable).setCell(index, FIVE, duration);
        }
    }

}

