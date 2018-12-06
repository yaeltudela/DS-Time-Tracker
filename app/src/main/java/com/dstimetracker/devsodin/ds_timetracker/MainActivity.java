package com.dstimetracker.devsodin.ds_timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dstimetracker.devsodin.core.BaseTask;
import com.dstimetracker.devsodin.core.Node;
import com.dstimetracker.devsodin.core.Project;
import com.dstimetracker.devsodin.core.Task;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SpeedDialView mSpeedDialView;


    private Node node = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("NEWNODE")) {
                node = (Node) extras.get("NEWNODE");

            }
        }
        RecyclerView rv = findViewById(R.id.recyclerView);

        if (node == null) {
            node = createTreeProjects();
        }
        initSpeedDial();


        layoutManager = new LinearLayoutManager(this);
        ArrayList nodes;
        if (node.isTask()) {
            nodes = ((Task) node).getIntervals();
            adapter = new IntervalAdapter(nodes);
        } else {
            nodes = (ArrayList<Node>) ((Project) node).getActivities();
            adapter = new NodeAdapter(nodes);
        }
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());


    }

    private void initSpeedDial() {
        mSpeedDialView = findViewById(R.id.speedDial);
        SpeedDialOverlayLayout overlayLayout = findViewById(R.id.speedDialOverlay);
        mSpeedDialView.setOverlayLayout(overlayLayout);

        if (!(node.getParent() == null && !node.isTask())) {
            if (node.isTask()) {
                //Only Add interval menu
                mSpeedDialView.setOnChangeListener(new SpeedDialView.OnChangeListener() {
                    @Override
                    public boolean onMainActionSelected() {
                        makeNewInterval();
                        return false;
                    }

                    @Override
                    public void onToggleChanged(boolean isOpen) {

                    }
                });
            } else {

                //Add task or project menu items
                mSpeedDialView.addActionItem(
                        new SpeedDialActionItem.Builder(R.id.fab_new_project, R.drawable.ic_audiotrack_light).setLabel(R.string.newProjectString)
                                .create());
                mSpeedDialView.addActionItem(
                        new SpeedDialActionItem.Builder(R.id.fab_new_task, R.drawable.ic_dialog_close_dark).setLabel(R.string.newTaskString)
                                .create());


            }

        } else {
            mSpeedDialView.setOnChangeListener(new SpeedDialView.OnChangeListener() {
                @Override
                public boolean onMainActionSelected() {
                    makeNewProject();
                    return false;
                }

                @Override
                public void onToggleChanged(boolean isOpen) {

                }

            });
        }


        mSpeedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.fab_new_project:
                        makeNewProject();
                        return false;
                    case R.id.fab_new_task:
                        makeNewTask();
                        return false;
                    case R.id.fab_new_interval:
                        makeNewInterval();
                        return false;

                }
                return true;
            }
        });

        mSpeedDialView.setExpansionMode(SpeedDialView.ExpansionMode.TOP);

        mSpeedDialView.setUseReverseAnimationOnClose(true);


    }

    private void makeNewProject() {
        Toast.makeText(MainActivity.this, "new project", Toast.LENGTH_SHORT).show();
    }

    private void makeNewTask() {
        Toast.makeText(MainActivity.this, "new task", Toast.LENGTH_SHORT).show();
    }

    private void makeNewInterval() {
        Toast.makeText(MainActivity.this, "new interval", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.searchMenu:
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
        t3.startInterval();

        return root;
    }


}
