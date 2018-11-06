
package core.ds.ds_project_timetracker;

import java.util.ArrayList;

public class Table {

    private int rows;
    private int cols;
    private ArrayList<ArrayList<String>> data;


    Table(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(null);
            }
            this.data.add(row);
        }
        this.data = new ArrayList<>();

    }

    public ArrayList<ArrayList<String>> getData() {
        return data;
    }


    public void addRow(final ArrayList<String> entry) {
        this.data.add(entry);
        this.rows++;
    }
}
