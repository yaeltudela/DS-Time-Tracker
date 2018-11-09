package core.ds.ds_project_timetracker;

/**
 * Abstract class that represents any kind of info that should be on a report.
 */
public abstract class Container implements Visitable {

    /**
     * Method that gets the Text.
     *
     * @return the string with the corresponding text.
     */
    public abstract String getText();

}
