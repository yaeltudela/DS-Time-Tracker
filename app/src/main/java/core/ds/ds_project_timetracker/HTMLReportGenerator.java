
package core.ds.ds_project_timetracker;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

/**
 * Concrete class to save to disk reports as an HTML file.
 */
public class HTMLReportGenerator extends ReportGenerator {

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
    protected String createFileName() {
        return "reportHTML - " + (new Date().toString()) + ".html";
    }


    @Override
    protected void saveReportToDisk() {
        for (Table t : this.report.getTables()) {
            if (t == null) {
                this.webpage.afegeixLiniaSeparacio();
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
