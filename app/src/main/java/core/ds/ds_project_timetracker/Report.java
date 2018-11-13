package core.ds.ds_project_timetracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Abstract class that represents a Report.
 * A report takes some tables with the tree data during a Period.
 */
public abstract class Report implements TreeVisitor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(Report.class);
    protected static final int ZERO = 0;
    protected static final int ONE = 1;
    protected static final int TWO = 2;
    protected static final int THREE = 3;
    protected static final int FOUR = 4;
    protected static final int FIVE = 5;
    protected static final int SIX = 6;
    protected static long currentDuration;
    protected final Date reportDate;
    protected final Project rootVisitable;
    protected final Period reportPeriod;
    protected final ReportGenerator reportGenerator;
    protected Container title;
    protected Container subtitleReports;
    protected Container reportTable;
    protected Container subtitleRootProjects;
    protected Container rootProjectsTable;
    protected Container footer;


    /**
     * Report constructor. It set's up the main table with all the contents.
     *
     * @param rootNode The first node to be visited.
     * @param period   The associated period with the report.
     * @param strategy The strategy to generate the report file.
     */
    Report(final Project rootNode, final Period period, final ReportGenerator strategy) {
        this.reportDate = new Date();
        this.rootVisitable = rootNode;
        this.reportPeriod = period;
        this.reportGenerator = strategy;

        this.subtitleReports = new Subtitle("Period");
        this.reportTable = createReportTable();

        this.subtitleRootProjects = new Subtitle("Root Projects");
        this.rootProjectsTable = new Table(ZERO, FIVE);

        this.footer = new Text("Time Tracker 1.0");

    }

    /**
     * Method that creates tables according to the subclass Report.
     */
    protected abstract void createTables();


    /**
     * Method that start the visits (visiting root projects)
     * and fills the tables.
     */
    protected void fillTables() {
        for (Node n : rootVisitable.getActivities()) {
            n.accept(this);
        }
    }

    /**
     * Abstract method to implement the create the tables by type.
     */
    public abstract void createReport();


    protected Table createReportTable() {

        Table table = new Table(FOUR, Report.TWO);

        table.setCell(ZERO, ZERO, "");
        table.setCell(ONE, ZERO, "From: ");
        table.setCell(TWO, ZERO, "To: ");
        table.setCell(THREE, ZERO, "Report generated at: ");
        table.setCell(ZERO, ONE, "Date");
        table.setCell(ONE, ONE, this.reportPeriod.getStartDate().toString());
        table.setCell(TWO, ONE, this.reportPeriod.getEndDate().toString());
        table.setCell(THREE, ONE, this.reportDate.toString());

        return table;
    }

    protected void createRootProjectTables() {
        ((Table) this.rootProjectsTable).addRow();
        ((Table) this.rootProjectsTable).setCell(ZERO, ZERO, "Id");
        ((Table) this.rootProjectsTable).setCell(ZERO, ONE, "Project Name");
        ((Table) this.rootProjectsTable).setCell(ZERO, TWO, "Start Date");
        ((Table) this.rootProjectsTable).setCell(ZERO, THREE, "End Date");
        ((Table) this.rootProjectsTable).setCell(ZERO, FOUR, "Duration");
    }

    /**
     * Method that recalculates, if needed, the new StartDate.
     *
     * @param startDate startDate to recalculate
     * @param endDate endDate to recalculate
     * @return startDate or the new date (Period startDate)
     */
    protected Date calcStartDate(final Date startDate, final Date endDate) {
        if (isPreviousIntersection(startDate, endDate)) {
            return this.reportPeriod.getStartDate();
        } else {
            return startDate;
        }
    }

    /**
     * Method that recalculates, if needed, the new endDate.
     *
     * @param startDate startDate to recalculate
     * @param endDate   endDate to recalculate
     * @return endDate or the new date (Period endDate)
     */
    protected Date calcEndDate(final Date startDate, final Date endDate) {
        if (isPostIntersection(startDate, endDate)) {
            return this.reportPeriod.getEndDate();
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
    protected long calcDuration(final Date startDate, final Date endDate, final long duration) {
        long newDuration = duration;
        if (!isWhole(startDate, endDate)) {
            if (isPreviousIntersection(startDate, endDate)) {
                long diff = (this.reportPeriod.getStartDate().getTime() - startDate.getTime()) / Clock.MS_IN_SEC;
                newDuration -= diff;
            }
            if (isPostIntersection(startDate, endDate)) {
                long diff = (endDate.getTime() - this.reportPeriod.getEndDate().getTime()) / Clock.MS_IN_SEC;
                newDuration -= diff;
            }
            return newDuration;
        } else {
            return duration;
        }
    }

    /**
     * Method to check if the Node or Interval needs to be evaluated.
     *
     * @param startDate start date to verify.
     * @param endDate   final date to verify.
     * @return True if is inside or intersect the period; false otherwise.
     */
    boolean isOnPeriod(final Date startDate, final Date endDate) {

        Date pStartDate = this.reportPeriod.getStartDate();
        Date pEndDate = this.reportPeriod.getEndDate();

        return ((startDate.before(pStartDate) && endDate.after(pStartDate))
                || (startDate.after(pStartDate) && endDate.before(pEndDate))
                || (startDate.before(pEndDate) && endDate.after(pEndDate))
                || (startDate.before(pStartDate) && endDate.after(pEndDate))
                || (startDate.equals(pStartDate)) || endDate.equals(pEndDate));

    }

    /**
     * Method that checks if the dates are completely inside the Report period.
     *
     * @param startDate Starting date to check.
     * @param endDate   Ending date to check.
     * @return true if is inside the period, false otherwise.
     */
    private boolean isWhole(final Date startDate, final Date endDate) {
        return ((startDate.after(this.reportPeriod.getStartDate())
                || startDate.equals(this.reportPeriod.getStartDate()))
                && (endDate.before(this.reportPeriod.getEndDate())
                || endDate.before(this.reportPeriod.getEndDate())));
    }

    /**
     * Method that checks if the dates are intersecting the Period startDate.
     *
     * @param startDate Starting date to check.
     * @param endDate   Ending date to check.
     * @return true if is intersecting the startDate period, false otherwise.
     */
    protected boolean isPreviousIntersection(final Date startDate, final Date endDate) {
        boolean startOk = (startDate.before(this.reportPeriod.getStartDate())
                || startDate.equals(this.reportPeriod.getStartDate()));
        boolean endOk = (endDate.after(this.reportPeriod.getStartDate()));
        return (startOk && endOk);
    }

    /**
     * Method that checks if the dates are intersecting the Period endDate.
     *
     * @param startDate Starting date to check.
     * @param endDate   Ending date to check.
     * @return true if is intersecting the endDate period, false otherwise.
     */
    protected boolean isPostIntersection(final Date startDate, final Date endDate) {

        boolean startOk = (startDate.before(this.reportPeriod.getEndDate()));
        boolean endOk = (endDate.after(this.reportPeriod.getEndDate())
                || endDate.equals(this.reportPeriod.getEndDate()));
        return (startOk && endOk);

    }

}
