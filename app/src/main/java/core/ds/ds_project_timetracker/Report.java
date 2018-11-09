
package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Abstract class that represents a Report.
 * A report takes some tables with the tree data during a Period.
 */
public abstract class Report {

    protected String name;
    protected final Project rootVisitable;
    protected final Period reportPeriod;
    protected Collection<Container> tables;
    private ReportGenerator reportGenerator;


    private Container title = new Title("Short Report");
    private Container subtitleReports = new Subtitle("Period");
    private Container subtitleProjects = new Subtitle("Root Projects");


    /**
     * Report constructor. It set's up the main table with all the contents.
     *
     * @param rootVisitable The first node to be visited.
     * @param reportPeriod  The associated period with the report.
     */
    Report(final Project rootVisitable, final Period reportPeriod, ReportGenerator reportGenerator) {
        this.rootVisitable = rootVisitable;
        this.reportPeriod = reportPeriod;
        this.reportGenerator = reportGenerator;
        this.tables = new ArrayList<>();

    }


    protected void addToReport(final Container container) {
        tables.add(container);
    }

    /**
     * Abstract method to implement the create the tables by type.
     */
    public abstract void createReport();

    /**
     * Method to check if the Node or Interval needs to be evaluated.
     *
     * @param startDate start date to verify.
     * @param endDate   final date to verify.
     * @return True if is inside or intersect the period; false otherwise.
     */
    boolean isInPeriod(final Date startDate, final Date endDate) {
        return endDate.after(this.reportPeriod.getStartDate()) || startDate.before(this.reportPeriod.getEndDate());
    }

    /**
     * Method that adds a null table simulating a separator.
     */
    void createSeparatorTable() {
        this.tables.add(null);
    }


    protected Table createReportTable() {

        Table table = new Table(4, 2);

        table.setCell(0, 0, "Date: ");
        table.setCell(1, 0, "From: ");
        table.setCell(2, 0, "To: ");
        table.setCell(3, 0, "Report generated at: ");
        table.setCell(0, 1, "");
        table.setCell(1, 1, this.reportPeriod.getStartDate().toString());
        table.setCell(2, 1, this.reportPeriod.getEndDate().toString());
        table.setCell(3, 1, this.reportPeriod.getReportDate().toString());

        return table;
    }

    protected void createRootProjectTables() {
        Table rootProjectsTable = new Table(0, 4);
        ArrayList<String> rootProjectHeader = new ArrayList<>(Arrays.asList("Name", "Start Date", "End Date", "Duration"));
        rootProjectsTable.addRow(rootProjectHeader);

    }


    /**
     * Method that starts visiting every element of the rootVisitable.
     */
    protected void generateReport() {
        for (Node p : rootVisitable.getActivities()) {
            p.accept(this.);
        }
    }

    /**
     * Method that recalculates, if needed, the new StartDate.
     * @param startDate Date to recalculate
     * @return startDate or the new date (Period startDate)
     */
    protected Date getNewStartDate(final Date startDate) {
        if (startDate.before(this.reportPeriod.getStartDate())) {
            return this.reportPeriod.getStartDate();
        } else {
            return startDate;
        }
    }

    /**
     * Method that recalculates, if needed, the new endDate.
     * @param endDate Date to recalculate
     * @return endDate or the new date (Period endDate)
     */
    protected Date getNewEndDate(final Date endDate) {
        if (endDate.after(this.reportPeriod.getEndDate())) {
            return this.reportPeriod.getStartDate();
        } else {
            return endDate;
        }
    }

    /**
     * Method that recalculates, if needed, the new StartDate.
     *
     * @param startDate startDate to recalculate
     * @param endDate   startDate to recalculate
     * @param duration  the default duration
     * @return duration or the new duration
     */
    protected long getNewDuration(final Date startDate, final Date endDate, final long duration) {
        if (startDate.before(this.reportPeriod.getStartDate()) && endDate.after(this.reportPeriod.getEndDate())) {
            return this.reportPeriod.getMaxDuration();
        } else {
            return duration;
        }
    }

    public ArrayList<Table> getTables() {
        return (ArrayList) tables;
    }


}
