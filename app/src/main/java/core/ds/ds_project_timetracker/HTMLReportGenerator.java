
package core.ds.ds_project_timetracker;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

/**
 * Concrete class to save to disk reports as an HTML file.
 */
public class HTMLReportGenerator extends ReportGenerator implements ReportVisitor {

    private Webpage webpage;

    /**
     * Constructor for the HTML report Generator.
     *
     * @param report The report to save.
     */
    HTMLReportGenerator(final Report report) {
        super(report);
        this.webpage = new Webpage();
    }


    @Override
    public void visitSubtitle(final Subtitle subtitle) {
        this.webpage.addHeader(subtitle.getText(), 3, false);
    }

    @Override
    public void visitText(final Text text) {
        this.webpage.addText(text.getText());
    }

    @Override
    public void visitSeparator(final Separator separator) {
        this.webpage.addSeparationLine();
    }

    @Override
    public void visitTable(final Table table) {
        this.webpage.addTable(table.getData(), false, false);
    }

    @Override
    public void visitTitle(final Title title) {
        this.webpage.addHeader(title.getText(), 1, true);
    }


    @Override
    protected String createFileName() {
        return "reportHTML - " + (new Date().toString()) + ".html";
    }

    @Override
    protected void saveReportToDisk() {
        for (Table t : this.report.getTables()) {
            if (t == null) {
                this.webpage.addSeparationLine();
            } else {
                this.webpage.addTable(t.getData(), false, false);
            }
        }
        PrintStream file = null;
        try {
            file = new PrintStream(this.filename);
            System.setOut(file);
            this.webpage.printWebpage();
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
