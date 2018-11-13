package core.ds.ds_project_timetracker;

/**
 * Class that represents a Subtitle for the report representation.
 */
public class Subtitle extends Container {

    private final String subtitle;

    /**
     * Constructor for the Subtitle class. It sets up the text of the subtitle.
     *
     * @param s String with the subtitle text.
     */
    Subtitle(final String s) {
        this.subtitle = s;
    }

    /**
     * Method that accepts the visitors.
     *
     * @param visitor The visitor itself
     */
    @Override
    public void accept(final Visitor visitor) {
        ((ReportVisitor) visitor).visitSubtitle(this);
    }

    /**
     * Getter for the Subtitle text.
     *
     * @return String with the subtitle text.
     */
    public String getText() {
        return subtitle;
    }


}
