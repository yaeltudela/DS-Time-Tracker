package com.dstimetracker.devsodin.ds_timetracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dstimetracker.devsodin.core.Interval;
import com.dstimetracker.devsodin.core.Node;
import com.dstimetracker.devsodin.core.Project;
import com.dstimetracker.devsodin.core.Task;
import com.dstimetracker.devsodin.core.TreeVisitor;

import java.util.ArrayList;

public class ActiveNodesActivity extends AppCompatActivity implements TreeVisitor {


    private ArrayList<Node> activeTasks;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayout;
    private TextView defaultText;

    private Node rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_nodes);
        this.recyclerView = findViewById(R.id.active_tasks_recycler);
        this.defaultText = findViewById(R.id.defaultTextActive);

        this.rootNode = (Node) getIntent().getSerializableExtra("rootNode");
        this.activeTasks = new ArrayList<>();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(R.string.active_task);

        getActiveTasks();
        if (!activeTasks.isEmpty()) {
            this.defaultText.setVisibility(View.INVISIBLE);
            this.recyclerView.setVisibility(View.VISIBLE);
            this.recyclerLayout = new LinearLayoutManager(this);
            this.recyclerAdapter = new NodeAdapter(this.activeTasks);
            this.recyclerView.setLayoutManager(recyclerLayout);
            this.recyclerView.setAdapter(recyclerAdapter);

        } else {
            this.recyclerView.setVisibility(View.INVISIBLE);
            this.defaultText.setVisibility(View.VISIBLE);
        }


    }

    private synchronized void getActiveTasks() {
        this.rootNode.accept(this);
    }

    @Override
    public void visitProject(Project project) {
        for (Node n : project.getActivities()) {
            n.accept(this);
        }
    }

    @Override
    public void visitTask(Task task) {
        if (task.isActive()) {
            this.activeTasks.add(task);
        }
    }

    @Override
    public void visitInterval(Interval interval) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.active_tasks_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.pauseAll:
                for (Node n : this.activeTasks) {
                    if (n.isTask()) {
                        ((Task) n).stopInterval();
                    }
                }
                break;
            case R.id.resumeAll:
                for (Node n : this.activeTasks) {
                    if (n.isTask()) {
                        ((Task) n).startInterval();
                    }
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
