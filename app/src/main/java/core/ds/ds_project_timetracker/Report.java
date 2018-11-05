package core.ds.ds_project_timetracker;

import java.util.ArrayList;
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

        this.reportTable = new Table(2, 2);
        this.rootProjectsTable = new Table(2, 2);
        this.tables.add(this.reportTable);
        this.tables.add(this.rootProjectsTable);
        createReportEntries();


    }

    protected void createReportEntries() {
        this.reportTable.addRow("Start Date", reportPeriod.getStartDate());
        this.reportTable.addRow("End Date", reportPeriod.getEndDate());
        this.reportTable.addRow("Report created at", reportPeriod.getReportDate());
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
