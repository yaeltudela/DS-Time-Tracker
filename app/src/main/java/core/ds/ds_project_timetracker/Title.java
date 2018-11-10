package core.ds.ds_project_timetracker;

/**
 * Class that represents Title for the report representation.
 */
public class Title extends Container {

    private final String title;

    /**
     * Constructor for the Title class. It sets up the text of the Title.
     *
     * @param s String with the Title text.
     */
    Title(final String s) {
        this.title = s;
    }

    /**
     * Method that accept the visitors.
     *
     * @param visitor The visitor itself
     */
    @Override
    public void accept(final Visitor visitor) {
        ((ReportVisitor) visitor).visitTitle(this);
    }

    /**
     * Getter for the Title text.
     *
     * @return String with the Title text.
     */
    @Override
    public String getText() {
        return this.title;
    }
}
