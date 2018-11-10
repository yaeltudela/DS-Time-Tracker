package core.ds.ds_project_timetracker;

/**
 * Class that represents Text for the report representation.
 */
public class Text extends Container {
    private String text;

    /**
     * Constructor for the Text class. It sets up the text of the Text.
     *
     * @param s String with the Text text.
     */
    public Text(final String s) {
        this.text = s;
    }

    /**
     * Getter for the separator text.
     *
     * @return String with a line.
     */
    @Override
    public String getText() {
        return this.text;
    }


    /**
     * Method that accept the visitors.
     *
     * @param visitor The visitor itself
     */
    @Override
    public void accept(final Visitor visitor) {

    }
}
