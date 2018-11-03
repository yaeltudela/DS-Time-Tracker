package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Date;

public abstract class Report implements Visitor {

    protected final Project rootVisitable;
    protected final Period reportPeriod;

    protected Report(Project rootVisitable, Period reportPeriod) {
        this.rootVisitable = rootVisitable;
        this.reportPeriod = reportPeriod;
    }

    protected abstract void generateReport();

    protected ArrayList<String> createEntry(String name, String description, Date startDate, Date endDate, long duration) {
        ArrayList<String> entry = new ArrayList<>();

        entry.add(name);
        entry.add(description);
        entry.add(startDate.toString());
        entry.add(endDate.toString());
        entry.add(String.valueOf(duration));


        return entry;
    }

    protected ArrayList<String> createEntry(String s, Date date) {
        ArrayList<String> entry = new ArrayList<>();

        entry.add(s);
        entry.add(date.toString());
        return entry;
    }

    protected void createReportEntries() {

        this.reportPeriod.getData().add(createEntry("Start Date", reportPeriod.getStartDate()));
        this.reportPeriod.getData().add(createEntry("End Date", reportPeriod.getEndDate()));
        this.reportPeriod.getData().add(createEntry("Report created at", reportPeriod.getReportDate()));
    }

}
