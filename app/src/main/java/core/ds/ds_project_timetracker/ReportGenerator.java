package core.ds.ds_project_timetracker;

import java.io.File;
import java.io.IOException;

public abstract class ReportGenerator implements ReportVisitor {

    protected final File file;
    protected Report report;
    protected final String filename;

    ReportGenerator() {
        this.filename = createFileName();
        this.file = createFile();
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
