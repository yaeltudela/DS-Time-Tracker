package core.ds.ds_project_timetracker;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

/**
 * Concrete class to save to disk reports as an HTML file.
 */
public class HTMLReportGenerator extends ReportGenerator implements ReportVisitor {

    private final WebPage webPage;

    /**
     * Constructor for the HTML report Generator.
     */
    HTMLReportGenerator() {
        super();
        this.webPage = new WebPage();
    }


    @Override
    public void visitTitle(final Title title) {
        this.webPage.addHeader(title.getText(), 1, true);
    }

    @Override
    public void visitSubtitle(final Subtitle subtitle) {
        this.webPage.addHeader(subtitle.getText(), 2, false);
    }

    @Override
    public void visitTable(final Table table) {
        this.webPage.addTable(table.getData(), true, false);
    }

    @Override
    public void visitText(final Text text) {
        this.webPage.addText(text.getText());
    }

    @Override
    public void visitSeparator(final Separator separator) {
        this.webPage.addSeparationLine();
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
            this.webPage.printWebpage();
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
