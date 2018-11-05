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
            for (Table t : this.report.getTables()) {

                for (ArrayList<String> line : t.getData()) {
                    for (String w : line) {
                        fileOutputStream.write((w + "\t").getBytes());
                    }
                    fileOutputStream.write(("\n").getBytes());
                }
                fileOutputStream.write(("\n\n").getBytes());

            }


            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
