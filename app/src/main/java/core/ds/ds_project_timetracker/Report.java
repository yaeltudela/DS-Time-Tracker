
package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public abstract class Report implements Visitor {

    protected String name;
    protected final Project rootVisitable;
    protected final Period reportPeriod;
    protected Collection<Table> tables;

    protected Table reportTable;
    protected Table rootProjectsTable;

    protected Report(Project rootVisitable, Period reportPeriod) {
        this.rootVisitable = rootVisitable;
        this.reportPeriod = reportPeriod;
        this.tables = new ArrayList<>();

        createCommonTables();


    }

    protected void createCommonTables() {

        this.reportTable = new Table(0, 2);
        this.tables.add(this.reportTable);
        createReportEntries();

        this.rootProjectsTable = new Table(0, 4);
        this.tables.add(this.rootProjectsTable);
        ArrayList<String> rootProjectHeader = new ArrayList<>(Arrays.asList("Name", "Start Date", "End Date", "Duration"));

        this.rootProjectsTable.addRow(rootProjectHeader);
    }

    protected void createReportEntries() {

        ArrayList<String> reportStartEntry = new ArrayList<>(Arrays.asList("From: ", reportPeriod.getStartDate().toString()));
        this.reportTable.addRow(reportStartEntry);
        ArrayList<String> reportEndEntry = new ArrayList<>(Arrays.asList("To: ", reportPeriod.getEndDate().toString()));
        this.reportTable.addRow(reportEndEntry);
        ArrayList<String> reportGenerationDateEntry = new ArrayList<>(Arrays.asList("Report generated at: ", reportPeriod.getReportDate().toString()));
        this.reportTable.addRow(reportGenerationDateEntry);
    }

    protected void generateReport() {
        for (Node p : rootVisitable.getActivities()) {
            p.accept(this);
        }
    }

    public ArrayList<Table> getTables() {
        return (ArrayList) tables;
    }

    protected boolean isInPeriod(Date startDate, Date endDate) {
        return endDate.after(this.reportPeriod.getStartDate()) || startDate.before(this.reportPeriod.getEndDate());
    }

    protected Date getNewStartDate(Date startDate) {
        if (startDate.before(this.reportPeriod.getStartDate())) {
            return this.reportPeriod.getStartDate();
        } else {
            return startDate;
        }
    }

    protected Date getNewEndDate(Date endDate) {
        if (endDate.after(this.reportPeriod.getEndDate())) {
            return this.reportPeriod.getStartDate();
        } else {
            return endDate;
        }
    }

    protected long getNewDuration(Date startDate, Date endDate, long duration) {
        if (startDate.before(this.reportPeriod.getStartDate()) && endDate.after(this.reportPeriod.getEndDate())) {
            return this.reportPeriod.getMaxDuration();
        } else {
            return duration;
        }
    }


}
