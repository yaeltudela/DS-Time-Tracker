package core.ds.ds_project_timetracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DataManager implements Serializable {

    private String filename;
    boolean error;
    public DataManager(String file) {
        this.filename = file;
        File f = new File(this.filename);
        if (!f.exists()) {
            try {
                error = f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void saveData(Object object) {
        try {
            FileOutputStream fOutStream = new FileOutputStream(this.filename);
            ObjectOutputStream outputStream = new ObjectOutputStream(fOutStream);
            outputStream.writeObject(object);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Object loadData() {
        Object data = null;
        try {
            FileInputStream fInStream = new FileInputStream(this.filename);
            if (fInStream.available() > 0) {
                ObjectInputStream inputStream = new ObjectInputStream(fInStream);
                while (fInStream.available() > 0) {
                    data = inputStream.readObject();
                }

                inputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return data;
    }
}
