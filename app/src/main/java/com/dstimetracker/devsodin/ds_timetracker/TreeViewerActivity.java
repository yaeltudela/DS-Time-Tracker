package com.dstimetracker.devsodin.ds_timetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    RecyclerView.Adapter adapter;
    private SpeedDialView mSpeedDialView;
    public static Node rootNode;
    private Toolbar toolbar;
    private SpeedDialOverlayLayout speedDialOverlayLayout;
    private SharedPreferences sharedPreferences;
    public static ArrayList<Integer> path;
    private static DataManager dataManager;
    public static Node node;
    boolean isWatching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_viewer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String language = sharedPreferences.getString(SettingsActivity.KEY_PREFERENCE_LANGUAGE, "en");
        String refresh_Rate = sharedPreferences.getString(SettingsActivity.KEY_PREFERENCE_REFRESH_RATE, "2");

        PreferenceManager.setDefaultValues(this, R.xml.app_preferences, false);


        if (dataManager == null) {
            path = new ArrayList<>();
            dataManager = new DataManager(getFilesDir() + "/save.db");
            node = (Project) dataManager.loadData();
            if (node == null) {
                node = createTreeProjects();
                dataManager.saveData((Project) node);
            }

        }

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(ACTUAL_NODE)) {
                node = (Node) extras.get(ACTUAL_NODE);

            }
        }
        setUpScreenElements();
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

        if (rootNode == null) {
            rootNode = node;
        }
    }

    void setUpScreenElements() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.rv = findViewById(R.id.recyclerView);

        this.rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        this.rv.setItemAnimator(new DefaultItemAnimator());
        registerForContextMenu(this.rv);

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
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, NewNodeDialog.newInstance(false)).addToBackStack(null).commit();
        adapter.notifyDataSetChanged();
    }

    private void makeNewTask() {
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, NewNodeDialog.newInstance(true)).addToBackStack(null).commit();
        adapter.notifyDataSetChanged();
    }

    private void makeNewInterval() {
        Toast.makeText(TreeViewerActivity.this, "Not implemented yet. TODO, dialog for manual intervals", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!TreeViewerActivity.path.isEmpty()) {
                TreeViewerActivity.path.remove(TreeViewerActivity.path.size() - 1);
            }
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {

        if (TreeViewerActivity.dataManager != null) {
            TreeViewerActivity.dataManager.saveData((Project) rootNode);
        }

        super.onDestroy();
    }


    //Actionbar menu
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

    //Navbar

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_active:
                startActivity(new Intent(this, ActiveNodesActivity.class));
                break;
            case R.id.nav_reports:
                startActivity(new Intent(this, ReportSettingsActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Auxiliary
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
