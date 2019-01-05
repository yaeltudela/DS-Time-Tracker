package com.dstimetracker.devsodin.ds_timetracker;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.dstimetracker.devsodin.core.BaseTask;
import com.dstimetracker.devsodin.core.Clock;
import com.dstimetracker.devsodin.core.DataManager;
import com.dstimetracker.devsodin.core.Node;
import com.dstimetracker.devsodin.core.Project;
import com.dstimetracker.devsodin.core.Task;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DataHolderService extends Service implements Observer {

    public static final String UPDATE_DATA = "updateData";
    public static final String STOP = "stop";
    private DataManager dataManager;
    private Node rootNode;
    private Node currentNode;
    private ArrayList<Task> activeTasks;
    private BroadcastReceiver receiver;
    private boolean isLevelChanged = false;


    public DataHolderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Clock.getInstance().addObserver(this);
        Clock.getInstance().setRefreshTicks(2);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("edit", "editing");
                Bundle intentData = intent.getExtras();
                if (intentData != null && intentData.containsKey("type")) {
                    String type = intentData.getString("type");
                    Node n;
                    int nodePosition;
                    switch (type) {
                        case NodeAdapter.CHILDREN:
                            nodePosition = intentData.getInt("nodePosition");
                            Node newNode = (Node) ((Project) currentNode).getActivities().toArray()[nodePosition];
                            currentNode = newNode;
                            isLevelChanged = true;
                            sendNewData();
                            break;
                        case TreeViewerActivity.PARENT:
                            if (currentNode.getParent() != null) {
                                currentNode = currentNode.getParent();
                            } else {
                                Intent broadcast = new Intent(STOP);
                                broadcast.putExtra("stop", 0);
                                sendBroadcast(broadcast);
                                stopSelf();
                            }
                            isLevelChanged = true;
                            sendNewData();
                            break;
                        case NewNodeDialog.NEW_TASK:
                            Task task = new BaseTask(intentData.getString("taskName"), intentData.getString("taskDescription"), (Project) currentNode);
                            break;
                        case NewNodeDialog.NEW_PROJECT:
                            Project project = new Project(intentData.getString("projectName"), intentData.getString("projectDescription"), (Project) currentNode);
                            break;
                        case NodeAdapter.START:
                            nodePosition = intentData.getInt("nodePosition");
                            ((Task) ((Project) currentNode).getActivities().toArray()[nodePosition]).startInterval();
                            break;
                        case NodeAdapter.STOP:
                            nodePosition = intentData.getInt("nodePosition");
                            ((Task) ((Project) currentNode).getActivities().toArray()[nodePosition]).stopInterval();
                            break;
                        case NodeAdapter.REMOVE:

                            break;
                        case NodeAdapter.EDIT:

                            break;
                        default:
                            break;

                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(TreeViewerActivity.PARENT);
        filter.addAction(NodeAdapter.CHILDREN);
        filter.addAction(NodeAdapter.REMOVE);
        filter.addAction(NodeAdapter.EDIT);
        filter.addAction(NewNodeDialog.NEW_PROJECT);
        filter.addAction(NewNodeDialog.NEW_TASK);
        filter.addAction(NodeAdapter.START);
        filter.addAction(NodeAdapter.STOP);
        registerReceiver(this.receiver, filter);


        if (rootNode == null) {
            if (dataManager == null) {
                dataManager = new DataManager(getFilesDir() + "/save.db");
                this.rootNode = (Node) dataManager.loadData();
                currentNode = rootNode;
                sendNewData();
            }
        }


        return Service.START_STICKY;
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.i("updating", "sending data");
        Intent broadcast = new Intent(UPDATE_DATA);
        if (isLevelChanged) {
            broadcast.putExtra("updateDial", 0);
            isLevelChanged = false;
        }
        broadcast.putExtra("node", currentNode);
        sendBroadcast(broadcast);
    }

    private void sendNewData() {
        Log.i("updating", "sending data");
        Intent broadcast = new Intent(UPDATE_DATA);
        if (isLevelChanged) {
            broadcast.putExtra("updateDial", 0);
            isLevelChanged = false;
        }
        broadcast.putExtra("node", currentNode);
        sendBroadcast(broadcast);
    }
}
