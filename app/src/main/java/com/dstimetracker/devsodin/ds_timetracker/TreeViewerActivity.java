package com.dstimetracker.devsodin.ds_timetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dstimetracker.devsodin.core.BaseTask;
import com.dstimetracker.devsodin.core.DataManager;
import com.dstimetracker.devsodin.core.Node;
import com.dstimetracker.devsodin.core.Project;
import com.dstimetracker.devsodin.core.Task;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;

public class TreeViewerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ACTUAL_NODE = "ACTUAL_NODE";
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SpeedDialView mSpeedDialView;
    private DataManager dataManager;
    private Toolbar toolbar;
    private SpeedDialOverlayLayout speedDialOverlayLayout;
    private SharedPreferences sharedPreferences;
    private Node node = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_viewer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String language = sharedPreferences.getString(SettingsActivity.KEY_PREFERENCE_LANGUAGE, "en");
        String refresh_Rate = sharedPreferences.getString(SettingsActivity.KEY_PREFERENCE_REFRESH_RATE, "2");

        PreferenceManager.setDefaultValues(this, R.xml.app_preferences, false);

        Toast.makeText(this, language + refresh_Rate, Toast.LENGTH_LONG).show();

        if (this.dataManager == null) {
            this.dataManager = new DataManager(getFilesDir() + "/save.db");
            this.node = (Project) this.dataManager.loadData();
            if (node == null) {
                node = createTreeProjects();
                dataManager.saveData((Project) node);
            }
        }
        setUpScreenElements();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(ACTUAL_NODE)) {
                node = (Node) extras.get(ACTUAL_NODE);

            }
        }



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

    @Override
    protected void onDestroy() {

        if (this.dataManager != null && node.getParent() == null) {
            this.dataManager.saveData((Project) node);
        }

        super.onDestroy();
    }

    void setUpScreenElements() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.rv = findViewById(R.id.recyclerView);

        this.mSpeedDialView = findViewById(R.id.speedDial);
        this.speedDialOverlayLayout = findViewById(R.id.speedDialOverlay);
        initSpeedDial();

    }

    private void initSpeedDial() {

        mSpeedDialView.setOverlayLayout(speedDialOverlayLayout);

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
        Toast.makeText(TreeViewerActivity.this, "new project", Toast.LENGTH_SHORT).show();
    }

    private void makeNewTask() {
        Toast.makeText(TreeViewerActivity.this, "new task", Toast.LENGTH_SHORT).show();
    }

    private void makeNewInterval() {
        Toast.makeText(TreeViewerActivity.this, "new interval", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tree_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchMenu:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
