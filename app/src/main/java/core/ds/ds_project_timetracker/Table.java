package core.ds.ds_project_timetracker;

import java.util.ArrayList;

/**
 * Class that represents a Table for the report representation.
 * It usually contains tree data.
 */
public class Table extends Container {

    private final int cols;
    private final ArrayList<ArrayList<String>> data;
    private int rows;


    Table(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(null);
            }
            this.data.add(row);
        }
    }

    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    public void addRow() {
        this.rows++;
        ArrayList<String> row = new ArrayList<>();

        for (int i = 0; i < this.getCols(); i++) {
            row.add(null);
        }
        this.data.add(row);
    }

    public void setCell(int row, int col, String data) {
        this.data.get(row).set(col, data);
    }


    public int getCols() {
        return cols;
    }

    public void addRow(final ArrayList<String> entry) {
        this.data.add(entry);
        this.rows++;
    }

    @Override
    public String getText() {
        return null;
    }

    public void printTable() {
        System.out.println(this.getData());
    }

    @Override
    public void accept(final Visitor visitor) {
        ((ReportVisitor) visitor).visitTable(this);
    }

    public int getRows() {
        return rows;
    }
}
