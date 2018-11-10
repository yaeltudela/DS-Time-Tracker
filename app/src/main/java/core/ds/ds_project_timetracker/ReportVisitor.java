package core.ds.ds_project_timetracker;

/**
 * Interface that contains the method to visit akk the containers of a report.
 */
public interface ReportVisitor extends Visitor {

    /**
     * Method to visit a Subtitle.
     *
     * @param subtitle the subtitle to visit.
     */
    void visitSubtitle(Subtitle subtitle);

    /**
     * Method to visit a Text.
     *
     * @param text the text to visit.
     */
    void visitText(Text text);

    /**
     * Method to visit a Separator.
     *
     * @param separator the separator to visit.
     */
    void visitSeparator(Separator separator);

    /**
     * Method to visit a Table.
     *
     * @param table the table to visit.
     */
    void visitTable(Table table);

    /**
     * Method to visit a Title.
     *
     * @param title the title to visit.
     */
    void visitTitle(Title title);

}
