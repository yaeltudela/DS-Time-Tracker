
package core.ds.ds_project_timetracker;

import java.util.Date;

public class HTMLReportGenerator extends ReportGenerator {
    HTMLReportGenerator(Report report) {
        super(report);
    }

    @Override
    protected String createFileName() {
        return "reportHTML - " + (new Date().toString()) + ".html";
    }


    @Override
    protected void saveReportToDisk() {

    }
}
