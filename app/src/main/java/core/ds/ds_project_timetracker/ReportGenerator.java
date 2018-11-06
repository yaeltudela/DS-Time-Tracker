
package core.ds.ds_project_timetracker;

import java.io.File;
import java.io.IOException;

public abstract class ReportGenerator {

    protected File file;
    protected Report report;
    protected String filename;

    ReportGenerator(Report report) {
        this.report = report;
        this.filename = createFileName();
        this.file = createFile();
        saveReportToDisk();
    }

    protected File createFile() {
        File f = new File(this.filename);

        if (!f.isFile()) {
            try {
                boolean error = f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    protected abstract String createFileName();

    protected abstract void saveReportToDisk();

}
