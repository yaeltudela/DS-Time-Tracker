
package core.ds.ds_project_timetracker;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Concrete class for saving the generated reports in TXT format.
 */
public class TXTReportGenerator extends ReportGenerator implements ReportVisitor {

    /**
     * Default constructor.
     *
     * @param report Report to save
     */
    TXTReportGenerator(final Report report) {
        super(report);
    }

    @Override
    protected String createFileName() {
        return "reportTXT - " + (new Date().toString()) + ".txt";
    }

    @Override
    protected void saveReportToDisk() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (Table t : this.report.getTables()) {
                if (t == null) {
                    fileOutputStream.write(("------------------------------------------------------------------------------------------------------\n").getBytes());
                } else {
                    for (ArrayList<String> line : t.getData()) {
                        for (String w : line) {
                            fileOutputStream.write((w + "\t").getBytes());
                        }
                        fileOutputStream.write(("\n").getBytes());
                    }
                }
            }


            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void visitTable(Table table) {

    }

    @Override
    public void visitTitle(Title title) {
        this.printWriter.println(title.getText());

    }

    @Override
    public void visitSubtitle(Subtitle subtitle) {
        this.printWriter.println(subtitle.getText());

    }

    @Override
    public void visitText(Text text) {
        this.printWriter.println(text.getText());

    }

    @Override
    public void visitSeparator(Separator separator) {
        this.printWriter.println(separator.getText());
    }
}
