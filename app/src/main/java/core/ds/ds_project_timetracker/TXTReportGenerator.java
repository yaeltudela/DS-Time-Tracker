package core.ds.ds_project_timetracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Concrete class for saving the generated reports in TXT format.
 */
public class TXTReportGenerator extends ReportGenerator implements ReportVisitor {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(TXTReportGenerator.class);
    private PrintWriter printWriter;

    /**
     * Default constructor. It initializes the printWriter to print to
     * the output file.
     */
    TXTReportGenerator() {
        super();
        try {
            this.printWriter = new PrintWriter(new BufferedWriter(
                    new FileWriter(this.getFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method that creates a filename with txt extension.
     *
     * @return String with the filename
     */
    @Override
    protected String createFileName() {
        return "reportTXT - " + (new Date().toString()) + ".txt";
    }


    /**
     * Method to save the report to the associated file.
     */
    @Override
    protected void saveReportToDisk() {
        LOGGER.info("TXT report saved to disk");
        this.getPrintWriter().close();
    }


    /**
     * Method that adds to the associated PrintWriter the table content.
     * @param table the table to visit.
     */
    @Override
    public void visitTable(final Table table) {
        for (ArrayList<String> line : table.getData()) {
            for (String word : line) {
                this.getPrintWriter().print(word);
                this.getPrintWriter().print("\t");
            }
            this.getPrintWriter().print("\n");
        }
    }

    /**
     * Method that adds to the associated PrintWriter the title content.
     * @param title the title to visit.
     */
    @Override
    public void visitTitle(final Title title) {
        this.getPrintWriter().println(title.getText());

    }

    /**
     * Method that adds to the associated PrintWriter the subtitle content.
     * @param subtitle the subtitle to visit.
     */
    @Override
    public void visitSubtitle(final Subtitle subtitle) {
        this.getPrintWriter().println(subtitle.getText());

    }

    /**
     * Method that adds to the associated PrintWriter the text content.
     * @param text the text to visit.
     */
    @Override
    public void visitText(final Text text) {
        this.getPrintWriter().println(text.getText());

    }

    /**
     * Method that adds to the associated PrintWriter the separator content.
     * @param separator the separator to visit.
     */
    @Override
    public void visitSeparator(final Separator separator) {
        this.getPrintWriter().println(separator.getText());
    }


    /**
     * Getter for the printWriter field.
     *
     * @return PrintWriter object associated to the ReportGenerator.
     */
    public PrintWriter getPrintWriter() {
        return this.printWriter;
    }
}
