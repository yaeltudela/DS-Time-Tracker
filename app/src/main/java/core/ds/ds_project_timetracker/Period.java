package core.ds.ds_project_timetracker;

import java.util.Date;

class Period {
    private Date startDate;
    private Date endDate;
    private Date reportDate;
    private long duration;

    Period(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reportDate = new Date();
        this.duration = (endDate.getTime() - startDate.getTime()) / 1000;
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

    public long getDuration() {
        return duration;
    }
}