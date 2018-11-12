package core.ds.ds_project_timetracker;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public abstract class ReportGenerator implements ReportVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportGenerator.class);
    protected final File file;
    protected Report report;
    protected final String filename;

    /**
     * Constructor for ReportGenerator. It construct the filename according to the strategy
     * and creates the file.
     */
    ReportGenerator() {
        this.filename = createFileName();
        this.file = createFile();
    }

    protected File createFile() {

        File f = new File(this.filename);

        if (!f.isFile()) {
            try {
                boolean error = f.createNewFile();
                LOGGER.info("Created file " + this.filename);
            } catch (IOException e) {
                LOGGER.error("Error creating file");
                e.printStackTrace();

            }
        }
        return f;
    }

    protected abstract String createFileName();

    protected abstract void saveReportToDisk();

}
