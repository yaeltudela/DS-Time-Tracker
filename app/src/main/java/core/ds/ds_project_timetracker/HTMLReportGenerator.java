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
     * It calls the ReportGenerator constructor and inits a webPage.
     */
    HTMLReportGenerator() {
        super();
        this.webPage = new WebPage();
    }

    /**
     * Method that adds to the associated webPage the visited Title.
     *
     * @param title the title to visit.
     */
    @Override
    public void visitTitle(final Title title) {
        this.getWebPage().addHeader(title.getText(), 1, true);
    }

    /**
     * Method that adds to the associated webPage the visited Subtitle.
     *
     * @param subtitle the subtitle to visit.
     */
    @Override
    public void visitSubtitle(final Subtitle subtitle) {
        this.getWebPage().addHeader(subtitle.getText(), 2, false);
    }

    /**
     * Method that adds to the associated webPage the visited Table.
     *
     * @param table the table to visit.
     */
    @Override
    public void visitTable(final Table table) {
        this.getWebPage().addTable(table.getData(), true, false);
    }

    /**
     * Method that adds to the associated webPage the visited Text.
     *
     * @param text the text to visit.
     */
    @Override
    public void visitText(final Text text) {
        this.getWebPage().addText(text.getText());
    }

    /**
     * Method that adds to the associated webPage a separator.
     *
     * @param separator the separator to visit.
     */
    @Override
    public void visitSeparator(final Separator separator) {
        this.getWebPage().addSeparationLine();
    }

    /**
     * Method that creates a filename with html extension.
     *
     * @return String with the filename
     */
    @Override
    protected String createFileName() {
        return "reportHTML - " + (new Date().toString()) + ".html";
    }

    /**
     * Abstract method to save the report to the
     * ReportGenerator associated file.
     */
    @Override
    protected void saveReportToDisk() {

        PrintStream file;
        try {
            file = new PrintStream(this.getFilename());
            System.setOut(file);
            this.getWebPage().printWebpage();
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for the webPage field.
     *
     * @return webPage object associated to the ReportGenerator.
     */
    public WebPage getWebPage() {
        return webPage;
    }
}
