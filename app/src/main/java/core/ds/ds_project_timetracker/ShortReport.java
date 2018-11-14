package core.ds.ds_project_timetracker;

/**
 * Concrete class that represent a basic and Short Report.
 * It contains only the Report data and the rootProjects data.
 */
public class ShortReport extends Report implements TreeVisitor {

    /**
     * Constructor for the ShortReport.
     * It creates the title and the tables.
     *
     * @param rootVisitable   the first Visitable to visit.
     * @param reportPeriod    The period to be reported.
     * @param reportGenerator The strategy used to generate the report.
     */
    public ShortReport(final Project rootVisitable, final Period reportPeriod, final ReportGenerator reportGenerator) {
        super(rootVisitable, reportPeriod, reportGenerator);
        this.title = new Title("Short Report");

        createTables();
        fillTables();

        Report.LOGGER.info("Created new short Report");
    }


    /**
     * Method that visits the components of the ShortReport.
     */
    @Override
    public void createReport() {
        this.reportGenerator.visitSeparator(new Separator());
        this.reportGenerator.visitTitle((Title) this.title);
        this.reportGenerator.visitSeparator(new Separator());

        this.reportGenerator.visitSubtitle((Subtitle) this.subtitleReports);
        this.reportGenerator.visitTable((Table) this.reportTable);
        this.reportGenerator.visitSeparator(new Separator());

        this.reportGenerator.visitSubtitle((Subtitle)
                this.subtitleRootProjects);
        this.reportGenerator.visitTable((Table) this.rootProjectsTable);
        this.reportGenerator.visitSeparator(new Separator());

        this.reportGenerator.visitText((Text) footer);

    }

    /**
     * Method that creates the rootProject table.
     */
    @Override
    protected void createTables() {
        this.createRootProjectTables();
    }

    /**
     * Method that visits a Project.
     * It uses the currentDuration variable to store the duration
     * between project and subProjects.
     * If needs to be visited (is inside period) and is a rootProject
     * it visit all the task getting the real duration stored
     * on currentDuration.
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
            }
        }
        Report.currentDuration = acc;

    }


    /**
     * Method that visits a task.
     * It checks if needs to be visited and visits all of the intervals
     * inside period getting the correct duration of the task.
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
                    long addDuration = calcDuration(
                            i.getStartDate(), i.getEndDate(), i.getDuration());
                    if (addDuration >= Clock.nREFRESHRATE) {
                        taskDuration += addDuration;
                    }
                }
            }
        }

        Report.currentDuration += taskDuration;

    }

    @Override
    public void visitInterval(final Interval interval) {
        //do nothing
    }

}
