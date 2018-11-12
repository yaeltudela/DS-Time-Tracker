package core.ds.ds_project_timetracker;

public class Id {
    private static int idCounter = -1;

    String id;

    public Id() {

    }

    public String getId() {
        return id;
    }

    public void setId(final String s) {
        this.id = s;
    }

    public void generateid() {
        Id.idCounter++;
        this.id = String.valueOf(Id.idCounter);

    }

}
