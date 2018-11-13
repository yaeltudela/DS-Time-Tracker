package core.ds.ds_project_timetracker;

/**
 * Class that represent a pseudo-unique id for the nodes or intervals.
 */
public class Id {
    private static int idCounter = -1;
    private String id;

    /**
     * Getter for the id.
     *
     * @return String with the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for the String id.
     *
     * @param s String with the id.
     */
    public void setId(final String s) {
        this.id = s;
    }

    /**
     * Method that generates a new Id and sets to new idCounter.
     */
    public void generateId() {
        Id.idCounter++;
        this.id = String.valueOf(Id.idCounter);
    }
}
