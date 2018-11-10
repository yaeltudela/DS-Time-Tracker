package core.ds.ds_project_timetracker;

/**
 * Concrete class that represent a basic and Short Report.
 * It contains only the Report data and the rootProjects data.
 */
public class ShortReport extends Report implements TreeVisitor {

    private final Container title;
    private final Container subtitleReports;
    private final Container subtitleRootProjects;
    private final Container footer;

    /**
     * Constructor for the ShortReport.
     * It creates the title and the tables.
     *
     * @param rootVisitable the first Visitable to visit.
     * @param reportPeriod  The period to be reported.
     * @param reportGenerator The strategy used to generate the report.
     */
    public ShortReport(final Project rootVisitable, final Period reportPeriod, final ReportGenerator reportGenerator) {
        super(rootVisitable, reportPeriod, reportGenerator);

        this.title = new Title("Detailed Report");
        this.subtitleReports = new Subtitle("Period");
        this.reportTable = createReportTable();

        this.subtitleRootProjects = new Subtitle("Root Projects");
        this.rootProjectsTable = new Table(0, 5);

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

        this.reportGenerator.visitText((Text) footer);

    }

    @Override
    protected void createTables() {
        this.createRootProjectTables();

    }


    @Override
    public void visitProject(final Project project) {

        long projectduration = 0;
        for (Node n : project.getActivities()) {
            if (isOnPeriod(n.getStartDate(), n.getEndDate())) {
                projectduration += calcDuration(n.getStartDate(), n.getEndDate(), n.getDuration());
            }
        }

        if (project.isRootNode()) {
            ((Table) this.rootProjectsTable).addRow();
            int index = ((Table) this.rootProjectsTable).getRows() - 1;

            ((Table) this.rootProjectsTable).setCell(index, 0, "id");
            ((Table) this.rootProjectsTable).setCell(index, 1, project.name);
            ((Table) this.rootProjectsTable).setCell(index, 2, calcStartDate(project.getStartDate(), project.getEndDate()).toString());
            ((Table) this.rootProjectsTable).setCell(index, 3, calcEndDate(project.getStartDate(), project.getEndDate()).toString());
            ((Table) this.rootProjectsTable).setCell(index, 4, String.valueOf(projectduration));
        }

    }

    @Override
    public void visitTask(final Task task) {


    }

    @Override
    public void visitInterval(Interval interval) {

    }

}
