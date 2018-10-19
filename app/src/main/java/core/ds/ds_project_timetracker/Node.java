package core.ds.ds_project_timetracker;


import java.io.Serializable;
import java.util.Date;

/**
 * Abstract class that represent a generic node on the tree (Task and Projects).
 * It's serializable and Visitable so it can be saved to a file and can accept visitors.
 */
public abstract class Node implements Serializable, Visitable {

    protected String name;
    protected String description;
    protected long duration;
    protected Date startDate;
    protected Date endDate;
    protected Node parent;

    /**
     * Empty constructor
     */
    public Node() {
        //TODO ask if it's necessary or correct
    }

    /**
     * Abstract method used for the refresh of all the modified data
     *
     * @param time time to do the update. Usually the actual Clock time
     */
    public abstract void updateData(Date time);

    /**
     * Getter for the name field
     *
     * @return String of the node's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name node
     *
     * @param name String with name of the Node
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the description field
     *
     * @return String of the node's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description node
     * @param description String with description of the Node
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the duration field
     *
     * @return long of the node's duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Setter for the duration node
     * @param duration The duration of the Node
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * Getter for the startDate field
     * @return Date of the node's startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Setter for the startDate node
     * @param startDate The startDate of the Node
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter for the endDate field
     * @return Date of the node's endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter for the endDate node
     * @param endDate The endDate of the Node
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter for the parent field
     * @return Node parent of the node. Null if is the root Node. Must be casted to Project or Task
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Setter for the parent node
     * @param parent the parent of the node (Node, Task or Project)
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }
}


