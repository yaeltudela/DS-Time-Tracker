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

    private static final Logger LOGGER = LoggerFactory.getLogger(TXTReportGenerator.class);
    private PrintWriter printWriter;

    /**
     * Default constructor. It initialices the printWriter to print to
     * the output file.
     */
    TXTReportGenerator() {
        super();
        try {
            this.printWriter = new PrintWriter(new BufferedWriter(
                    new FileWriter(this.file)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected String createFileName() {
        return "reportTXT - " + (new Date().toString()) + ".txt";
    }

    @Override
    protected void saveReportToDisk() {
        LOGGER.info("TXT report saved to disk");
        this.printWriter.close();
    }


    @Override
    public void visitTable(final Table table) {
        for (ArrayList<String> line : table.getData()) {
            for (String word : line) {
                this.printWriter.print(word);
                this.printWriter.print("\t");
            }
            this.printWriter.print("\n");
        }
    }

    @Override
    public void visitTitle(final Title title) {
        this.printWriter.println(title.getText());

    }

    @Override
    public void visitSubtitle(final Subtitle subtitle) {
        this.printWriter.println(subtitle.getText());

    }

    @Override
    public void visitText(final Text text) {
        this.printWriter.println(text.getText());

    }

    @Override
    public void visitSeparator(final Separator separator) {
        this.printWriter.println(separator.getText());
    }
}
