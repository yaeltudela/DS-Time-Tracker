
package core.ds.ds_project_timetracker;

import java.util.Date;

/**
 * Fragment of time delimited by two dates.
 */
class Period {
    private Date startDate;
    private Date endDate;
    private Date reportDate;
    private final int MS_ON_SEC = 1000;
    private long maxDuration;

    Period(final Date startDate, final Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reportDate = new Date();
        this.maxDuration = (endDate.getTime() - startDate.getTime()) / MS_ON_SEC;
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

    public long getMaxDuration() {
        return maxDuration;
    }
}