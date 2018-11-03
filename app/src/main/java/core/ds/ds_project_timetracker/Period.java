package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

class Period {
    private Date startDate;
    private Date endDate;
    private Collection<Collection<String>> data;
    private Date reportDate;

    Period(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.data = new ArrayList<>();
        this.reportDate = new Date();
    }

    public ArrayList<ArrayList<String>> getData() {
        return (ArrayList) data;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getReportDate() {
        return reportDate;
    }
}