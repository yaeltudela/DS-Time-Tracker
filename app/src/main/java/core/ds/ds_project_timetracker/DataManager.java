package core.ds.ds_project_timetracker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DataManager implements Serializable {

    private String filename = null;

    public DataManager(String file) {
        try {
            this.filename = file;

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public boolean saveData(Object object) {
        boolean error = false;

        try {
            FileOutputStream fOutStream = new FileOutputStream(this.filename);
            ObjectOutputStream outputStream = new ObjectOutputStream(fOutStream);
            outputStream.writeObject(object);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            error = true;
            e.printStackTrace();
        }
        return error;

    }

    public Object loadData() {
        Object data = null;
        try {
            FileInputStream fInStream = new FileInputStream(this.filename);
            ObjectInputStream inputStream = new ObjectInputStream(fInStream);
            data = inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return data;
    }
}
