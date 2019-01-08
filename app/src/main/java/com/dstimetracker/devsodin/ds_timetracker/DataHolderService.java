package com.dstimetracker.devsodin.ds_timetracker;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;

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
    public static final String ACTIVE_TASKS_DATA = "activeTasks";
    private DataManager dataManager;
    private Node rootNode;
    private Node currentNode;
    private ArrayList<Task> activeTasks = new ArrayList<>();
    private ArrayList<Node> path = new ArrayList<>();
    private BroadcastReceiver receiver;
    private boolean isLevelChanged = false;
    private int refreshRate;


    public DataHolderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        refreshRate = Integer.parseInt(preferences.getString(SettingsActivity.KEY_PREFERENCE_REFRESH_RATE, "1"));

        Clock.getInstance().setRefreshTicks(refreshRate);
        Clock.getInstance().addObserver(this);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle intentData = intent.getExtras();
                if (intentData != null && intentData.containsKey("type")) {
                    String type = intentData.getString("type");
                    Node n;
                    int nodePosition;
                    switch (type) {
                        case NodeAdapter.CHILDREN:
                            nodePosition = intentData.getInt("nodePosition");
                            Node newNode = (Node) ((Project) currentNode).getActivities().toArray()[nodePosition];
                            path.add(currentNode);
                            currentNode = newNode;
                            isLevelChanged = true;
                            break;
                        case TreeViewerActivity.PARENT:
                            if (currentNode.getParent() != null) {
                                currentNode = currentNode.getParent();
                                path.remove(path.size() - 1);

                            } else {
                                dataManager.saveData((Project) rootNode);
                                Intent broadcast = new Intent(STOP);
                                broadcast.putExtra("stop", 0);
                                sendBroadcast(broadcast);
                            }
                            isLevelChanged = true;
                            break;
                        case NewNodeDialog.NEW_TASK:
                            Task task = new BaseTask(intentData.getString("taskName"), intentData.getString("taskDescription"), (Project) currentNode);
                            break;
                        case NewNodeDialog.NEW_PROJECT:
                            Project project = new Project(intentData.getString("projectName"), intentData.getString("projectDescription"), (Project) currentNode);
                            break;
                        case NodeAdapter.START:
                            nodePosition = intentData.getInt("nodePosition");
                            activeTasks.add(((Task) ((Project) currentNode).getActivities().toArray()[nodePosition]));
                            ((Task) ((Project) currentNode).getActivities().toArray()[nodePosition]).startInterval();
                            break;
                        case NodeAdapter.STOP:
                            nodePosition = intentData.getInt("nodePosition");
                            activeTasks.remove(((Project) currentNode).getActivities().toArray()[nodePosition]);
                            ((Task) ((Project) currentNode).getActivities().toArray()[nodePosition]).stopInterval();
                            break;
                        case NodeAdapter.REMOVE:
                            nodePosition = intentData.getInt("nodePosition");
                            ((ArrayList) ((Project) currentNode).getActivities()).remove(nodePosition);
                            break;
                        case NodeAdapter.EDIT:
                            nodePosition = intentData.getInt("nodePosition");
                            ((Node) ((ArrayList) ((Project) currentNode).getActivities()).get(nodePosition)).setName(intentData.getString("nodeName"));
                            ((Node) ((ArrayList) ((Project) currentNode).getActivities()).get(nodePosition)).setDescription(intentData.getString("nodeDescription"));
                            break;
                        case ActiveNodesActivity.ACTIVE_TASKS:
                            sendActiveTasks();
                            break;
                        case ActiveNodesActivity.PAUSE_ALL:
                            for (Task t : activeTasks) {
                                if (t.isActive()) {
                                    t.stopInterval();
                                }
                            }
                            sendActiveTasks();
                            break;
                        case ActiveNodesActivity.RESUME_ALL:
                            for (Task t : activeTasks) {
                                if (!t.isActive()) {
                                    t.startInterval();
                                }
                            }
                            sendActiveTasks();
                            break;
                        default:
                            break;

                    }
                    sendNewData();

                }
            }
        };

        if (rootNode == null) {
            if (dataManager == null) {
                dataManager = new DataManager(getFilesDir() + "/save.db");
                this.rootNode = (Node) dataManager.loadData();
                if (this.rootNode == null) {
                    rootNode = new Project("root", "", null);
                }
                currentNode = rootNode;
                sendNewData();
            }
        }


        IntentFilter filter = new IntentFilter();
        filter.addAction(TreeViewerActivity.PARENT);
        filter.addAction(NodeAdapter.CHILDREN);
        filter.addAction(NodeAdapter.REMOVE);
        filter.addAction(NodeAdapter.EDIT);
        filter.addAction(NewNodeDialog.NEW_PROJECT);
        filter.addAction(NewNodeDialog.NEW_TASK);
        filter.addAction(NodeAdapter.START);
        filter.addAction(NodeAdapter.STOP);
        filter.addAction(ActiveNodesActivity.ACTIVE_TASKS);
        filter.addAction(ActiveNodesActivity.PAUSE_ALL);
        filter.addAction(ActiveNodesActivity.RESUME_ALL);
        registerReceiver(this.receiver, filter);

        return Service.START_STICKY;
    }

    private void sendActiveTasks() {
        Intent broadcast = new Intent(ACTIVE_TASKS_DATA);
        broadcast.putExtra("activeTasks", activeTasks);
        sendBroadcast(broadcast);
    }

    @Override
    public void update(Observable o, Object arg) {
        Intent broadcast = new Intent(UPDATE_DATA);
        if (isLevelChanged) {
            broadcast.putExtra("updateDial", 0);
            isLevelChanged = false;
        }
        broadcast.putExtra("node", currentNode);
        broadcast.putExtra("path", getNewPath());
        sendBroadcast(broadcast);
    }

    private String getNewPath() {
        StringBuilder newPath = new StringBuilder();
        newPath.append("/");
        if (path.size() > 0) {
            for (int i = 1; i < path.size(); i++) {
                newPath.append(path.get(i).getName());
                newPath.append("/");
            }
            if (currentNode != null) {
                newPath.append(currentNode.getName());
            }
        }
        return newPath.toString();
    }

    private void sendNewData() {
        Intent broadcast = new Intent(UPDATE_DATA);
        if (isLevelChanged) {
            broadcast.putExtra("updateDial", 0);
            isLevelChanged = false;
        }
        broadcast.putExtra("node", currentNode);
        broadcast.putExtra("path", getNewPath());
        sendBroadcast(broadcast);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(this.receiver);
        super.onDestroy();
    }

}
