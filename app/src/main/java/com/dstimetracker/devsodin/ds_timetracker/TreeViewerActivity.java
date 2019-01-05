package com.dstimetracker.devsodin.ds_timetracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

    private RecyclerView rv;
    public static final String PARENT = "parent";
    RecyclerView.Adapter nodeAdapter;
    RecyclerView.Adapter intervalAdapter;
    private SpeedDialView mSpeedDialView;
    public static Node rootNode;
    private Toolbar toolbar;
    private SpeedDialOverlayLayout speedDialOverlayLayout;
    private SharedPreferences sharedPreferences;
    public static ArrayList<Integer> path;
    private static DataManager dataManager;
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    private TextView defaultText;
    private Node node;
    private Intent dataHolderService;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_viewer);

        defaultText = findViewById(R.id.noRootProjects);
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

        this.dataHolderService = new Intent(this, DataHolderService.class);
        this.startService(dataHolderService);
        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("reciving", "reciving data");
                Bundle bundle = intent.getExtras();
                if (bundle != null) {

                    if (bundle.containsKey("stop")) {
                        finish();
                    } else {
                        node = (Node) bundle.getSerializable("node");
                        if (bundle.containsKey("updateDial")) {
                            initSpeedDial();
                        }
                        updateScreenData();
                    }

                }
            }
        };

        setUpScreenElements();

    }

    private void updateScreenData() {
        if (node != null) {
            rv.setVisibility(View.VISIBLE);
            defaultText.setVisibility(View.INVISIBLE);
            if (node.isTask()) {
                intervalAdapter = new IntervalAdapter(((Task) node).getIntervals());
                rv.setAdapter(intervalAdapter);
                intervalAdapter.notifyDataSetChanged();
            } else {
                nodeAdapter = new NodeAdapter((ArrayList<Node>) ((Project) node).getActivities());
                rv.setAdapter(nodeAdapter);
                nodeAdapter.notifyDataSetChanged();
            }
        } else {
            rv.setVisibility(View.INVISIBLE);
            defaultText.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DataHolderService.UPDATE_DATA);
        registerReceiver(this.receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.receiver);
    }

    void setUpScreenElements() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.rv = findViewById(R.id.recyclerView);

        this.rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        this.rv.setItemAnimator(new DefaultItemAnimator());
        registerForContextMenu(this.rv);
        rv.setLayoutManager(layoutManager);

        this.mSpeedDialView = findViewById(R.id.speedDial);
        this.speedDialOverlayLayout = findViewById(R.id.speedDialOverlay);

    }

    private void initRootSpeedDial() {
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

    private void initTreeSpeedDial() {
        //Add task or project menu items
        mSpeedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_new_project, R.drawable.ic_audiotrack_light).setLabel(R.string.newProjectString)
                        .create());
        mSpeedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_new_task, R.drawable.ic_dialog_close_dark).setLabel(R.string.newTaskString)
                        .create());
    }

    private void initIntervalSpeedDial() {
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
    }


    private void initSpeedDial() {
        mSpeedDialView.setOverlayLayout(speedDialOverlayLayout);
        mSpeedDialView.clearActionItems();
        mSpeedDialView.setOnChangeListener(null);

        if (node.getParent() == null) {
            initRootSpeedDial();
        } else if (node.isTask()) {
            initIntervalSpeedDial();
        } else {
            initTreeSpeedDial();
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

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.sd_fade_and_translate_in, R.anim.sd_fade_and_translate_out).replace(android.R.id.content, NewNodeDialog.newInstance(false)).addToBackStack(null).commit();
    }

    private void makeNewTask() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.sd_fade_and_translate_in, R.anim.sd_fade_and_translate_out).replace(android.R.id.content, NewNodeDialog.newInstance(true)).addToBackStack(null).commit();
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
            Intent intent = new Intent(PARENT);
            intent.putExtra("type", PARENT);
            sendBroadcast(intent);
        }
    }


    @Override
    protected void onDestroy() {

        if (TreeViewerActivity.dataManager != null) {
            TreeViewerActivity.dataManager.saveData((Project) rootNode);
        }

        stopService(this.dataHolderService);
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
                Intent activeActivity = new Intent(this, ActiveNodesActivity.class);
                activeActivity.putExtra("rootNode", TreeViewerActivity.rootNode);
                startActivity(activeActivity);
                break;
            case R.id.nav_reports:
                startActivity(new Intent(this, ReportGenerator.class));
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
