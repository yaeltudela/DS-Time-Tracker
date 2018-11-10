package core.ds.ds_project_timetracker;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

/**
 * Concrete class to save to disk reports as an HTML file.
 */
public class HTMLReportGenerator extends ReportGenerator implements ReportVisitor {

    private final Webpage webpage;

    /**
     * Constructor for the HTML report Generator.
     */
    HTMLReportGenerator() {
        super();
        this.webpage = new Webpage();
    }


    @Override
    public void visitTitle(final Title title) {
        this.webpage.addHeader(title.getText(), 1, true);
    }

    @Override
    public void visitSubtitle(final Subtitle subtitle) {
        this.webpage.addHeader(subtitle.getText(), 2, false);
    }

    @Override
    public void visitTable(final Table table) {
        this.webpage.addTable(table.getData(), true, true);
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
    protected String createFileName() {
        return "reportHTML - " + (new Date().toString()) + ".html";
    }

    @Override
    protected void saveReportToDisk() {

        PrintStream file;
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
