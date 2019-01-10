package com.dstimetracker.devsodin.ds_timetracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dstimetracker.devsodin.core.Node;

import java.util.ArrayList;

public class ActiveNodesActivity extends AppCompatActivity {


    private ArrayList<Node> activeTasks;
    private RecyclerView recyclerView;
    public static final String ACTIVE_TASKS = "activeTasks";
    private RecyclerView.LayoutManager recyclerLayout;
    private TextView defaultText;
    public static final String RESUME_ALL = "resumeTasks";
    public static final String PAUSE_ALL = "pauseTasks";
    private RecyclerView.Adapter<NodeAdapter.NodeViewHolder> recyclerAdapter;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_nodes);
        this.recyclerView = findViewById(R.id.active_tasks_recycler);
        this.defaultText = findViewById(R.id.defaultTextActive);
        this.activeTasks = new ArrayList<>();
        this.recyclerLayout = new LinearLayoutManager(this);
        this.recyclerAdapter = new NodeAdapter(this.activeTasks);
        this.recyclerView.setLayoutManager(recyclerLayout);
        this.recyclerView.setAdapter(recyclerAdapter);
        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    if (bundle.containsKey("activeTasks")) {
                        activeTasks = (ArrayList<Node>) bundle.getSerializable("activeTasks");
                        recyclerAdapter = new NodeAdapter(activeTasks);
                        updateScreenData();
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(DataHolderService.ACTIVE_TASKS_DATA);
        registerReceiver(receiver, filter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(R.string.active_task);

        getActiveTasks();
        updateScreenData();



    }

    /**
     * Method that updates the fields from the linked layout.
     */
    private void updateScreenData() {
        if (!activeTasks.isEmpty()) {
            this.defaultText.setVisibility(View.INVISIBLE);
            this.recyclerView.setVisibility(View.VISIBLE);
            recyclerAdapter.notifyDataSetChanged();
        } else {
            this.recyclerView.setVisibility(View.INVISIBLE);
            this.defaultText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method to ask DataHolderService for the activeTasks via broadcast.
     */
    private void getActiveTasks() {
        Intent intent = new Intent(ACTIVE_TASKS);
        intent.putExtra("type", ACTIVE_TASKS);
        sendBroadcast(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.active_tasks_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.pauseAll:
                intent = new Intent(PAUSE_ALL);
                intent.putExtra("type", PAUSE_ALL);
                break;
            case R.id.resumeAll:
                intent = new Intent(RESUME_ALL);
                intent.putExtra("type", RESUME_ALL);
                break;
        }
        sendBroadcast(intent);

        return super.onOptionsItemSelected(item);
    }
}
