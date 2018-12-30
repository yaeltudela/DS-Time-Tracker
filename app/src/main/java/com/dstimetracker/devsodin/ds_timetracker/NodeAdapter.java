package com.dstimetracker.devsodin.ds_timetracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dstimetracker.devsodin.core.Node;
import com.dstimetracker.devsodin.core.Project;
import com.dstimetracker.devsodin.core.Task;

import java.util.ArrayList;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeViewHolder> {
    private ArrayList<Node> nodes;


    public NodeAdapter(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    @NonNull
    @Override
    public NodeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.node_layout, viewGroup, false);
        return new NodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NodeViewHolder nodeViewHolder, int i) {
        nodeViewHolder.bindData(this.nodes, i);

    }


    @Override
    public int getItemCount() {
        return nodes.size();
    }


    public static class NodeViewHolder extends RecyclerView.ViewHolder {

        TextView nodeName;
        TextView nodeDuration;
        TextView nodeStartDate;
        TextView nodeEndDate;

        ImageButton changeStatus;
        TextView threeDots;
        ImageView activeBar;


        public NodeViewHolder(@NonNull View itemView) {
            super(itemView);

            nodeName = itemView.findViewById(R.id.nodeNameText);
            nodeDuration = itemView.findViewById(R.id.nodeDurationText);
            nodeStartDate = itemView.findViewById(R.id.nodeStartDateText);
            nodeEndDate = itemView.findViewById(R.id.nodeEndDateText);
            changeStatus = itemView.findViewById(R.id.taskStatus);
            threeDots = itemView.findViewById(R.id.threeDots);
            activeBar = itemView.findViewById(R.id.status);


        }

        private void bindData(ArrayList<Node> nodes, final int i) {
            updateIcon(nodes.get(i));
            nodeName.setText(nodes.get(i).getName());
            nodeDuration.setText(Double.toString(nodes.get(i).getDuration()));
            if (nodes.get(i).getStartDate() != null) {
                nodeStartDate.setText(nodes.get(i).getStartDate().toString());
            } else {
                nodeStartDate.setText("---");
            }
            nodeStartDate.setVisibility(View.GONE);
            if (nodes.get(i).getEndDate() != null) {
                nodeEndDate.setText(nodes.get(i).getEndDate().toString());
            } else {
                nodeEndDate.setText("---");
            }
            nodeEndDate.setVisibility(View.GONE);

            if (!nodes.get(i).isTask()) {
                changeStatus.setVisibility(View.GONE);
            } else {
                changeStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Node n = TreeViewerActivity.rootNode;
                        if (TreeViewerActivity.path.isEmpty()) {
                            n = (Node) ((ArrayList) ((Project) n).getActivities()).get(i);
                        } else {
                            for (int index : TreeViewerActivity.path) {
                                n = (Node) ((ArrayList) ((Project) n).getActivities()).get(index);
                            }
                            n = (Node) ((ArrayList) ((Project) n).getActivities()).get(i);
                        }


                        if (n.isActive()) {
                            ((Task) n).stopInterval();
                            Toast.makeText(itemView.getContext(), "stoping task", Toast.LENGTH_SHORT).show();
                        } else {
                            ((Task) n).startInterval();
                            Toast.makeText(itemView.getContext(), "starting task", Toast.LENGTH_SHORT).show();
                        }
                        updateIcon(n);

                    }
                });
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), TreeViewerActivity.class);

                    Node n = TreeViewerActivity.rootNode;
                    if (TreeViewerActivity.path.isEmpty()) {
                        n = (Node) ((ArrayList) ((Project) n).getActivities()).get(i);
                    } else {
                        for (int index : TreeViewerActivity.path) {
                            n = (Node) ((ArrayList) ((Project) n).getActivities()).get(index);
                        }
                        n = (Node) ((ArrayList) ((Project) n).getActivities()).get(i);
                    }
                    TreeViewerActivity.path.add(i);

                    intent.putExtra(TreeViewerActivity.ACTUAL_NODE, n);
                    v.getContext().startActivity(intent);
                }
            });

            threeDots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.showContextMenu();
                }
            });
        }

        private void updateIcon(Node node) {
            if (node.isActive()) {
                changeStatus.setImageResource(R.drawable.ic_media_pause_light);
                activeBar.setVisibility(View.VISIBLE);
                nodeName.setTypeface(Typeface.DEFAULT_BOLD);
                nodeDuration.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                changeStatus.setImageResource(R.drawable.ic_media_play_light);
                activeBar.setVisibility(View.GONE);
                nodeName.setTypeface(Typeface.DEFAULT);
                nodeDuration.setTypeface(Typeface.DEFAULT);

            }
        }



    }

}

