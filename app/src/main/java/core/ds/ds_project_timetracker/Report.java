package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public abstract class Report implements Visitor {

    protected final Project rootVisitable;
    protected final Period reportPeriod;
    protected Collection<Table> tables;

    protected Table reportTable;
    protected Table rootProjectsTable;
    protected Table subrojectsTable = null;
    protected Table tasksTable = null;
    protected Table intervalsTable = null;



    protected Report(Project rootVisitable, Period reportPeriod) {
        this.rootVisitable = rootVisitable;
        this.reportPeriod = reportPeriod;
        this.tables = new ArrayList<>();

        this.reportTable = new Table(3, 2);
        this.rootProjectsTable = new Table(0, 4);
        this.tables.add(this.reportTable);
        this.tables.add(this.rootProjectsTable);
        createReportEntries();
        ArrayList<String> rootProjectHeader = new ArrayList<>(Arrays.asList("Name", "Start Date", "End Date", "Duration"));
        this.rootProjectsTable.addRow(rootProjectHeader);
    }

    protected void createReportEntries() {

        ArrayList<String> reportStartEntry = new ArrayList<>(Arrays.asList("From: ", reportPeriod.getStartDate().toString()));
        this.rootProjectsTable.addRow(reportStartEntry);
        ArrayList<String> reportEndEntry = new ArrayList<>(Arrays.asList("To: ", reportPeriod.getEndDate().toString()));
        this.rootProjectsTable.addRow(reportEndEntry);
        ArrayList<String> reportGenerationDateEntry = new ArrayList<>(Arrays.asList("Report generated at: ", reportPeriod.getReportDate().toString()));
        this.rootProjectsTable.addRow(reportGenerationDateEntry);
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


}
