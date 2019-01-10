package com.dstimetracker.devsodin.ds_timetracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dstimetracker.devsodin.core.Node;
import com.dstimetracker.devsodin.core.Project;
import com.dstimetracker.devsodin.core.Task;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;

/**
 * Activity that shows the tree structure on recycler view.
 * Also contains a speedDial to add more Nodes.
 * <p>
 * This activity use a broadcastReceiver to update the data.
 */
public class TreeViewerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String HOME = "home";
    private RecyclerView rv;
    public static final String PARENT = "parent";
    RecyclerView.Adapter nodeAdapter;
    RecyclerView.Adapter intervalAdapter;
    private SpeedDialView mSpeedDialView;
    private Toolbar toolbar;
    private SpeedDialOverlayLayout speedDialOverlayLayout;
    public static ArrayList<Integer> path;
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    private TextView defaultText;
    private Node node;
    private Intent dataHolderService;
    private BroadcastReceiver receiver;
    private ImageButton homeButton;
    private TextView pathText;
    private boolean visiblePath = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_viewer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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

        PreferenceManager.setDefaultValues(this, R.xml.app_preferences, false);

        this.dataHolderService = new Intent(this, DataHolderService.class);
        this.startService(dataHolderService);
        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {

                    if (bundle.containsKey("stop")) {
                        finish();
                    } else {
                        node = (Node) bundle.getSerializable("node");
                        if (bundle.containsKey("updateDial")) {
                            initSpeedDial();
                        }
                        String newPath = bundle.getString("path");
                        updateScreenData(newPath);
                    }

                }
            }
        };

        setUpScreenElements();
        initRootSpeedDial();
        displayPath();

    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DataHolderService.UPDATE_DATA);
        filter.addAction(DataHolderService.STOP);
        registerReceiver(this.receiver, filter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(this.receiver);
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        stopService(this.dataHolderService);
        super.onDestroy();
    }


    //Actionbar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchMenu:
                Toast.makeText(this, "TODO, show a search", Toast.LENGTH_LONG).show();
                return true;
            case R.id.showPath:
                this.visiblePath = !this.visiblePath;
                displayPath();

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Method that updates the fields from the linked layout.
     * @param newPath new path to show (if needed)
     */
    private void updateScreenData(String newPath) {

        if (node != null) {
            rv.setVisibility(View.VISIBLE);
            defaultText.setVisibility(View.GONE);
            if (node.isTask()) {
                intervalAdapter = new IntervalAdapter(((Task) node).getIntervals());
                rv.setAdapter(intervalAdapter);
                intervalAdapter.notifyDataSetChanged();
            } else {
                if (((Project) node).getActivities().size() == 0) {
                    rv.setVisibility(View.INVISIBLE);
                    defaultText.setVisibility(View.VISIBLE);
                    rv.setAdapter(null);
                } else {
                    ArrayList<Node> nodes = ((ArrayList<Node>) ((Project) node).getActivities());
                    nodeAdapter = new NodeAdapter(nodes);
                    rv.setAdapter(nodeAdapter);
                    nodeAdapter.notifyDataSetChanged();
                }
            }
        } else {
            rv.setVisibility(View.INVISIBLE);
            defaultText.setVisibility(View.VISIBLE);
            rv.setAdapter(null);
        }

        pathText.setText(newPath);
    }


    /**
     * Method called on onCreate method. It finds by id the elements and add the common clickListener.
     */
    void setUpScreenElements() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.rv = findViewById(R.id.recyclerView);

        this.pathText = findViewById(R.id.path);
        this.homeButton = findViewById(R.id.goHome);
        this.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HOME);
                intent.putExtra("type", HOME);
                sendBroadcast(intent);

            }
        });

        this.rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        this.rv.setItemAnimator(new DefaultItemAnimator());
        registerForContextMenu(this.rv);
        rv.setLayoutManager(layoutManager);

        this.mSpeedDialView = findViewById(R.id.speedDial);
        this.speedDialOverlayLayout = findViewById(R.id.speedDialOverlay);

    }

    /**
     * Method called on onCreate. It sets up the speeddial according to tree level
     */
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

    /**
     * Method that add listener for speedDial (used only on root level)
     */
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

    /**
     * Method that add listeners to menu items for speedDial (used on non-root level)
     */
    private void initTreeSpeedDial() {
        //Add task or project menu items
        mSpeedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_new_project, R.drawable.ic_add_black_24dp).setLabel(R.string.newProjectString)
                        .create());
        mSpeedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_new_task, R.drawable.ic_add_black_24dp).setLabel(R.string.newTaskString)
                        .create());
    }

    /**
     * Method that add listener for speedDial (used only inside tasks (interval levels))
     */
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


    /**
     * Method that put a newNodeDialog (a fragment) on top. Used to create a project.
     */
    private void makeNewProject() {

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.sd_fade_and_translate_in, R.anim.sd_fade_and_translate_out).add(R.id.drawer_layout, NewNodeDialog.newInstance(false)).addToBackStack("new").commit();
    }

    /**
     * Method that put a newNodeDialog (a fragment) on top. Used to create a task.
     */
    private void makeNewTask() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.sd_fade_and_translate_in, R.anim.sd_fade_and_translate_out).add(R.id.drawer_layout, NewNodeDialog.newInstance(true)).addToBackStack("new").commit();
    }

    /**
     * Method that shows a toast because is an unimplemented function.
     */
    private void makeNewInterval() {
        Toast.makeText(TreeViewerActivity.this, "Not implemented yet. TODO, dialog for manual intervals", Toast.LENGTH_LONG).show();

    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if(node.getParent() == null){
                    finish();
                }else{
                    Intent intent = new Intent(PARENT);
                    intent.putExtra("type", PARENT);
                    sendBroadcast(intent);
                }
            }
        }
    }


    /**
     * Method to change layout element visibility (display path and home button)
     */
    private void displayPath() {
        if (this.visiblePath) {
            pathText.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
        }else{
            pathText.setVisibility(View.GONE);
            homeButton.setVisibility(View.GONE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tree_viewer, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
