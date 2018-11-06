
package core.ds.ds_project_timetracker;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Concrete class for saving the generated reports in TXT format.
 */
public class TXTReportGenerator extends ReportGenerator {

    /**
     * Default constructor.
     *
     * @param report Report to save
     */
    TXTReportGenerator(final Report report) {
        super(report);
    }

    @Override
    protected String createFileName() {
        return "reportTXT - " + (new Date().toString()) + ".txt";
    }

    @Override
    protected void saveReportToDisk() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (Table t : this.report.getTables()) {
                if (t == null) {
                    fileOutputStream.write(("------------------------------------------------------------------------------------------------------\n").getBytes());
                } else {
                    for (ArrayList<String> line : t.getData()) {
                        for (String w : line) {
                            fileOutputStream.write((w + "\t").getBytes());
                        }
                        fileOutputStream.write(("\n").getBytes());
                    }
                }
            }


            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
