package core.ds.ds_project_timetracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Class that manages the data interaction.
 * Used for load and save the main tree with all the data
 */
public class DataManager implements Serializable {

    private String filename;

    /**
     * Constructor for DataManager. It checks if the file exists and creates if not (in root folder)
     *
     * @param file name of the file to load and save the data
     */
    DataManager(String file) {
        this.filename = file;
        File f = new File(this.filename);
        if (!f.exists()) {
            try {
                boolean error = f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Generic method for save an unique object to the file associated to the DataManager object
     *
     * @param object The object to save
     */
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

    /**
     * Generic method to load a single object from the associated file to the DataManager object.
     *
     * @return The loaded Object. (Must be casted to the correct type)
     */
    public Object loadData() {
        Object data = null;
        try {
            FileInputStream fInStream = new FileInputStream(this.filename);
            if (fInStream.available() > 0) {
                ObjectInputStream inputStream = new ObjectInputStream(fInStream);
                if (fInStream.available() > 0) {
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
