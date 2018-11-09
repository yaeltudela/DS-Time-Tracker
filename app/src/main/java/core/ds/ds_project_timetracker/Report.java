
package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Abstract class that represents a Report.
 * A report takes some tables with the tree data during a Period.
 */
public abstract class Report implements Visitor {

    protected String name;
    protected final Project rootVisitable;
    protected final Period reportPeriod;
    protected Collection<Table> tables;

    protected Table reportTable;
    protected Table rootProjectsTable;

    /**
     * Report constructor. It set's up the main table with all the contents.
     *
     * @param rootVisitable The first node to be visited.
     * @param reportPeriod  The associated period with the report.
     */
    protected Report(final Project rootVisitable, final Period reportPeriod) {
        this.rootVisitable = rootVisitable;
        this.reportPeriod = reportPeriod;
        this.tables = new ArrayList<>();
    }

    /**
     * Method to check if the Node or Interval needs to be evaluated.
     *
     * @param startDate start date to verify.
     * @param endDate   final date to verify.
     * @return True if is inside or intersect the period; false otherwise.
     */
    protected boolean isInPeriod(final Date startDate, final Date endDate) {
        return endDate.after(this.reportPeriod.getStartDate()) || startDate.before(this.reportPeriod.getEndDate());
    }

    /**
     * Method that adds a null table simulating a separator.
     */
    void createSeparatorTable() {
        this.tables.add(null);
    }

    /**
     * Method that creates and add a table with a Title and,
     * optionally a subtitle.
     *
     * @param title    The title of section.
     * @param subtitle null or subtitle of section.
     */
    void createSectionTable(final String title, String subtitle) {
        Table section = new Table(2, 1);
        section.setCell(0, 0, title);
        if (subtitle == null) {
            subtitle = "";
        }
        section.setCell(1, 0, subtitle);
        this.tables.add(section);
    }

    protected void createReportTable() {
        this.reportTable = new Table(0, 2);
        this.tables.add(this.reportTable);

        ArrayList<String> reportStartEntry = new ArrayList<>(Arrays.asList("From: ", reportPeriod.getStartDate().toString()));
        this.reportTable.addRow(reportStartEntry);
        ArrayList<String> reportEndEntry = new ArrayList<>(Arrays.asList("To: ", reportPeriod.getEndDate().toString()));
        this.reportTable.addRow(reportEndEntry);
        ArrayList<String> reportGenerationDateEntry = new ArrayList<>(Arrays.asList("Report generated at: ", reportPeriod.getReportDate().toString()));
        this.reportTable.addRow(reportGenerationDateEntry);

        createSeparatorTable();
    }

    protected void createRootProjectTables() {
        this.rootProjectsTable = new Table(0, 4);
        this.tables.add(this.rootProjectsTable);
        ArrayList<String> rootProjectHeader = new ArrayList<>(Arrays.asList("Name", "Start Date", "End Date", "Duration"));

        this.rootProjectsTable.addRow(rootProjectHeader);

        createSeparatorTable();
    }


    protected void createCommonTables() {
        createReportTable();
        createSectionTable("Root projects", null);
        createRootProjectTables();
    }


    /**
     * Method that starts visiting every element of the rootVisitable.
     */
    protected void generateReport() {
        for (Node p : rootVisitable.getActivities()) {
            p.accept(this);
        }
    }

    /**
     * Method that recalculates, if needed, the new StartDate.
     * @param startDate Date to recalculate
     * @return startDate or the new date (Period startDate)
     */
    protected Date getNewStartDate(Date startDate) {
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
    protected Date getNewEndDate(Date endDate) {
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
