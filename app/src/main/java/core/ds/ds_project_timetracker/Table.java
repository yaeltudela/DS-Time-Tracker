package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Table {

    private int rows;
    private int cols;
    private Collection<Collection<String>> data;


    Table(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new ArrayList<>();
    }

    public ArrayList<ArrayList<String>> getData() {
        return (ArrayList) data;
    }

    public void addRow(String s, Date date) {
        ArrayList<String> entry = new ArrayList<>();
        entry.add(s);
        entry.add(date.toString());

        this.data.add(entry);
    }


    public void addRow(String name, String description, Date startDate, Date endDate, long duration) {
        ArrayList<String> entry = new ArrayList<>();

        entry.add(name);
        entry.add(description);
        entry.add(startDate.toString());
        entry.add(endDate.toString());
        entry.add(String.valueOf(duration));


        this.data.add(entry);
    }
}
