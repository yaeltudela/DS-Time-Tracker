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

import com.dstimetracker.devsodin.core.BaseTask;
import com.dstimetracker.devsodin.core.Node;
import com.dstimetracker.devsodin.core.Project;
import com.dstimetracker.devsodin.core.Task;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

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
        RecyclerView rv = findViewById(R.id.recyclerView);

        if (node == null) {
            Project project = createTreeProjects();

            layoutManager = new LinearLayoutManager(this);
            rv.setLayoutManager(layoutManager);
            adapter = new NodeAdapter((ArrayList<Node>) project.getActivities());
            rv.setAdapter(adapter);


            ArrayList<String> example = new ArrayList<>(Arrays.asList("test1", "test4534", "test4", "test6", "test1", "test4534", "test4", "test6"));
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, example);

            //lv.setAdapter(arrayAdapter);
        } else {
            ArrayList<Node> nodes = (ArrayList<Node>) ((Project) node).getActivities();
            NodeAdapter nodeAdapter = new NodeAdapter(nodes);
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

    private Project createTreeProjects() {
        Project root = new Project("root", "", null);
        Project p1 = new Project("P1", "P1 desc", root);
        Task t3 = new BaseTask("T3", "T3 desc", p1);
        Project p2 = new Project("P2", "P1 desc", p1);
        Task t1 = new BaseTask("T1", "T1 desc", p2);
        Task t2 = new BaseTask("T2", "T2 desc", p2);
        Project p3 = new Project("P3", "p3 desc", root);
        Project p4 = new Project("P4", "p4 desc", root);
        Project p5 = new Project("P5", "p5 desc", root);
        Project p6 = new Project("P6", "p6 desc", root);
        Project p7 = new Project("P7", "p7 desc", root);
        Project p8 = new Project("P8", "p8 desc", root);
        Project p9 = new Project("P9", "p9 desc", root);
        Project p1_1 = new Project("P1_1", "p1-1 desc", p1);
        Project p1_2 = new Project("P1_2", "p1-2 desc", p1);
        Project p1_3 = new Project("P1_3", "p1-3 desc", p1);

        return root;
    }

}
