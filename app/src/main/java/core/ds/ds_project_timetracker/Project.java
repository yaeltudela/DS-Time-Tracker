package core.ds.ds_project_timetracker;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Project extends Activity_{

   private List<Activity_> activities;


    public Project(String name, String description){
        this.name = name;
        this.description = description;

        activities = new ArrayList<Activity_>();
    }

}
