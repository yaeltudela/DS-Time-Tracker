package core.ds.ds_project_timetracker;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class TXTReportGenerator extends ReportGenerator {

    TXTReportGenerator(Report report) {
        super(report);
    }

    @Override
    protected String createFileName() {
        return "report - " + (new Date().toString()) + ".txt";
    }

    @Override
    protected void saveReportToDisk() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            for (ArrayList<String> line : this.report.reportPeriod.getData()) {
                for (String w : line) {
                    fileOutputStream.write((w + "\t").getBytes());
                }
                fileOutputStream.write(("\n").getBytes());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
