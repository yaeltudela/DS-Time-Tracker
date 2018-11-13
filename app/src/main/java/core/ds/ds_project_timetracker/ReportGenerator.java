package core.ds.ds_project_timetracker;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public abstract class ReportGenerator implements ReportVisitor {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(ReportGenerator.class);
    private final File file;
    private final String filename;
    private Report report;

    /**
     * Constructor for ReportGenerator. It construct the filename according
     * to the strategy and creates the file.
     */
    ReportGenerator() {
        this.filename = createFileName();
        this.file = createFile();
    }

    /**
     * Method that creates a file with the given filename.
     *
     * @return the file descriptor of the created file.
     */
    protected File createFile() {

        File f = new File(this.filename);

        if (!f.isFile()) {
            try {
                boolean error = f.createNewFile();
                LOGGER.info("Created file " + this.getFilename());
            } catch (IOException e) {
                LOGGER.error("Error creating file");
                e.printStackTrace();

            }
        }
        return f;
    }

    /**
     * Abstract method to create a filename according to the used strategy.
     *
     * @return String with the filename
     */
    protected abstract String createFileName();

    /**
     * Abstract method to save the report to the
     * ReportGenerator associated file.
     */
    protected abstract void saveReportToDisk();

    /**
     * Getter for the File field.
     *
     * @return File descriptor associated to the ReportGenerator.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Getter for the filename field.
     *
     * @return String with the filename field.
     */
    public String getFilename() {
        return this.filename;
    }
}
