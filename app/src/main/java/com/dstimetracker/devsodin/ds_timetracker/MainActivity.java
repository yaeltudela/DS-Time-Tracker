package com.dstimetracker.devsodin.ds_timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.dstimetracker.devsodin.core.Node;
import com.dstimetracker.devsodin.core.Project;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Node node = null;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("NEWNODE")) {
                node = (Node) extras.get("NEWNODE");

            }
        }

        if (node == null) {
            Project project = new Project("test", "desc", null);
            Project p1 = new Project("testi134124231", "desc", project);
            Project p12 = new Project("sdkfjg ñljsdlkj", "desc", p1);

            Project p2 = new Project("testkjfsdhlfksdkdhdhldkfasdhk", "desc", project);
            Project p3 = new Project("larsh ", "desc", project);
            Project p4 = new Project("fsdñlk", "desc", project);
            Project p5 = new Project("dsnvm", "desc", project);
            Project p6 = new Project("raopiwr", "desc", project);
            Project p7 = new Project("425' dsjlk", "desc", project);
            Project p8 = new Project("dsak", "desc", project);
            Project p9 = new Project("dsalkj  dsñfal alkjd ", "desc", project);

            RecyclerView rv = findViewById(R.id.recyclerView);
            //ListView lv = findViewById(R.id.listViewRoot);


            ArrayList<Node> nodes = (ArrayList<Node>) project.getActivities();
            NodeAdapter nodeAdapter = new NodeAdapter(this, (ArrayList<Node>) project.getActivities());
            rv.setAdapter(nodeAdapter);
            rv.setLayoutManager(new LinearLayoutManager(this));


            ArrayList<String> example = new ArrayList<>(Arrays.asList("test1", "test4534", "test4", "test6", "test1", "test4534", "test4", "test6"));
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, example);

            //lv.setAdapter(arrayAdapter);
        } else {
            RecyclerView rv = findViewById(R.id.recyclerView);
            ArrayList<Node> nodes = (ArrayList<Node>) ((Project) node).getActivities();
            NodeAdapter nodeAdapter = new NodeAdapter(this, nodes);
            rv.setAdapter(nodeAdapter);
            rv.setLayoutManager(new LinearLayoutManager(this));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.searchMenuItem:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
