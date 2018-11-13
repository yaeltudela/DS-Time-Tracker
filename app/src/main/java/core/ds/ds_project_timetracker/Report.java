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
    protected static final int nZERO = 0;
    protected static final int nONE = 1;
    protected static final int nTWO = 2;
    protected static final int nTHREE = 3;
    protected static final int nFOUR = 4;
    protected static final int nFIVE = 5;
    protected static final int nSIX = 6;
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


    /**
     * Report constructor. It set's up the main table with all the contents.
     *
     * @param rootVisitable The first node to be visited.
     * @param reportPeriod  The associated period with the report.
     */
    Report(final Project rootVisitable, final Period reportPeriod, ReportGenerator reportGenerator) {
        this.reportDate = new Date();
        this.rootVisitable = rootVisitable;
        this.reportPeriod = reportPeriod;
        this.reportGenerator = reportGenerator;
    }

    /**
     * Method that creates tables according to the subclass Report.
     */
    protected abstract void createTables();


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

        Table table = new Table(4, Report.nTWO);

        table.setCell(0, 0, "");
        table.setCell(1, 0, "From: ");
        table.setCell(2, 0, "To: ");
        table.setCell(3, 0, "Report generated at: ");
        table.setCell(0, 1, "Date");
        table.setCell(1, 1, this.reportPeriod.getStartDate().toString());
        table.setCell(2, 1, this.reportPeriod.getEndDate().toString());
        table.setCell(3, 1, this.reportDate.toString());

        return table;
    }

    protected void createRootProjectTables() {
        ((Table) this.rootProjectsTable).addRow();
        ((Table) this.rootProjectsTable).setCell(0, 0, "Id");
        ((Table) this.rootProjectsTable).setCell(0, 1, "Project Name");
        ((Table) this.rootProjectsTable).setCell(0, 2, "Start Date");
        ((Table) this.rootProjectsTable).setCell(0, 3, "End Date");
        ((Table) this.rootProjectsTable).setCell(0, 4, "Duration");
    }

    /**
     * Method that recalculates, if needed, the new StartDate.
     *
     * @param startDate Date to recalculate
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
     * @param endDate Date to recalculate
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

    private boolean isWhole(final Date startDate, final Date endDate) {
        return ((startDate.after(this.reportPeriod.getStartDate()) || startDate.equals(this.reportPeriod.getStartDate())) &&
                (endDate.before(this.reportPeriod.getEndDate()) || endDate.before(this.reportPeriod.getEndDate())));
    }

    protected boolean isPreviousIntersection(final Date startDate, final Date endDate) {
        boolean startOk = (startDate.before(this.reportPeriod.getStartDate()) || startDate.equals(this.reportPeriod.getStartDate()));
        boolean endOk = (endDate.after(this.reportPeriod.getStartDate()));
        return (startOk && endOk);
    }

    protected boolean isPostIntersection(final Date startDate, final Date endDate) {

        boolean startOk = (startDate.before(this.reportPeriod.getEndDate()));
        boolean endOk = (endDate.after(this.reportPeriod.getEndDate()) || endDate.equals(this.reportPeriod.getEndDate()));
        return (startOk && endOk);

    }

}
