package core.ds.ds_project_timetracker;


import java.io.Serializable;
import java.util.Date;

/**
 * Abstract class that represent a generic node on the tree (Task and Projects).
 * It's serializable and Visitable so it can be saved to
 * a file and can accept visitors.
 */
public abstract class Node implements Serializable, Visitable {

    protected String name;
    protected String description;
    protected Date endDate;
    protected Node parent;
    private long duration;
    private Date startDate;

    /**
     * Abstract method used for the refresh of all the modified data.
     *
     * @param time time to do the update. Usually the actual Clock time
     */
    public abstract void updateData(Date time);

    /**
     * Getter for the name field.
     *
     * @return String of the node's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name node.
     *
     * @param title String with name of the Node
     */
    public void setName(final String title) {
        this.name = title;
    }

    /**
     * Getter for the description field.
     *
     * @return String of the node's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description node.
     *
     * @param desc String with description of the Node
     */
    public void setDescription(final String desc) {
        this.description = desc;
    }

    /**
     * Getter for the duration field.
     *
     * @return long of the node's duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Setter for the duration node.
     *
     * @param time The duration of the Node
     */
    public void setDuration(final long time) {
        this.duration = time;
    }

    /**
     * Getter for the startDate field.
     *
     * @return Date of the node's startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Setter for the startDate node.
     *
     * @param start The startDate of the Node
     */
    public void setStartDate(final Date start) {
        this.startDate = start;
    }

    /**
     * Getter for the endDate field.
     *
     * @return Date of the node's endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter for the endDate node.
     *
     * @param end The endDate of the Node
     */
    public void setEndDate(final Date end) {
        this.endDate = end;
    }

    /**
     * Getter for the parent field.
     *
     * @return Node parent of the node. Null if is the root Node.
     * Must be casted to Project or Task
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Setter for the parent node.
     *
     * @param father the parent of the node (Node, Task or Project)
     */
    public void setParent(final Node father) {
        this.parent = father;
    }

    /**
     * Method that checks if the project is a Root Project.
     *
     * @return ture if it's root, false otherwise
     */
    public boolean isRootNode() {
        return (this.getParent().getParent() == null);
    }

}
