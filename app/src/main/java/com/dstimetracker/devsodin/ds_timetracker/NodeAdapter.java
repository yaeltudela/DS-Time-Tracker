package com.dstimetracker.devsodin.ds_timetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dstimetracker.devsodin.core.Node;
import com.dstimetracker.devsodin.core.Project;
import com.dstimetracker.devsodin.core.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeViewHolder> {
    private static ArrayList<Node> nodes;


    public NodeAdapter(ArrayList<Node> nodes) {
        NodeAdapter.nodes = nodes;
    }

    @NonNull
    @Override
    public NodeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.node_layout, viewGroup, false);
        return new NodeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NodeViewHolder nodeViewHolder, int i) {
        nodeViewHolder.bindData(nodes, i);

    }


    @Override
    public int getItemCount() {
        return nodes.size();
    }

    private void removeNode(Node node) {
        int index = nodes.indexOf(node);
        nodes.remove(node);
        notifyItemRemoved(index);
    }

    public class NodeViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private final MenuItem.OnMenuItemClickListener onMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        showDetailsDialog();
                        break;
                    case 2:
                        showEditDialog();
                        break;
                    case 3:
                        removeNode(node);
                        notifyDataSetChanged();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        };


        TextView nodeName;
        TextView nodeDuration;
        TextView nodeStartDate;
        TextView nodeEndDate;

        ImageButton changeStatus;
        TextView threeDots;
        ImageView activeBar;

        Node node;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM - kk:mm", Locale.ENGLISH);

        public NodeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);

            nodeName = itemView.findViewById(R.id.nodeNameText);
            nodeDuration = itemView.findViewById(R.id.nodeDurationText);
            nodeStartDate = itemView.findViewById(R.id.nodeStartDateText);
            nodeEndDate = itemView.findViewById(R.id.nodeEndDateText);
            changeStatus = itemView.findViewById(R.id.taskStatus);
            threeDots = itemView.findViewById(R.id.threeDots);
            activeBar = itemView.findViewById(R.id.status);


        }

        private void bindData(ArrayList<Node> nodes, final int i) {
            this.node = nodes.get(i);
            updateIcon(nodes.get(i));
            nodeName.setText(nodes.get(i).getName());
            nodeDuration.setText(Double.toString(nodes.get(i).getDuration()) + " secs.");
            if (nodes.get(i).getStartDate() != null) {
                nodeStartDate.setText(simpleDateFormat.format(nodes.get(i).getStartDate()));
                nodeEndDate.setText(simpleDateFormat.format(nodes.get(i).getEndDate()));
            } else {
                nodeStartDate.setText("---");
                nodeEndDate.setText("---");
            }

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
                            Toast.makeText(itemView.getContext(), "stopping task", Toast.LENGTH_SHORT).show();
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

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem details = menu.add(Menu.NONE, 1, 1, R.string.detailsMenuEdit);
            MenuItem edit = menu.add(Menu.NONE, 2, 2, R.string.editMenuEdit);
            MenuItem delete = menu.add(Menu.NONE, 3, 3, R.string.removeMenuEdit);

            details.setOnMenuItemClickListener(onMenuItemClickListener);
            edit.setOnMenuItemClickListener(onMenuItemClickListener);
            delete.setOnMenuItemClickListener(onMenuItemClickListener);

        }

        private void showDetailsDialog() {
            AlertDialog.Builder detailsDialog = new AlertDialog.Builder(itemView.getContext());
            detailsDialog.setTitle(R.string.detailsMenuEdit);

            final View detailsView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.node_details, null);
            detailsDialog.setView(detailsView);

            detailsDialog.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });


            detailsDialog.create();


            detailsDialog.show();

            TextView name = detailsView.findViewById(R.id.nameDetails);
            TextView description = detailsView.findViewById(R.id.descriptionDetails);
            TextView startDate = detailsView.findViewById(R.id.startDateDetails);
            TextView endDate = detailsView.findViewById(R.id.endDateDetails);
            TextView duration = detailsView.findViewById(R.id.durationDetails);

            name.setText(node.getName());
            description.setText(node.getDescription());
            startDate.setText(node.getStartDate().toString());
            endDate.setText(node.getEndDate().toString());
            duration.setText(Long.toString(node.getDuration()));

        }

        private void showEditDialog() {
            AlertDialog.Builder editDialog = new AlertDialog.Builder(itemView.getContext());
            editDialog.setTitle(R.string.editMenuEdit);

            final View detailsView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.edit_node, null);
            editDialog.setView(detailsView);

            editDialog.create();

            final TextView name = detailsView.findViewById(R.id.editName);
            final TextView description = detailsView.findViewById(R.id.editDescription);
            name.setText(node.getName());
            description.setText(node.getDescription());

            editDialog.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    node.setName(name.getText().toString());
                    node.setDescription(description.getText().toString());
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            });

            editDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });


            editDialog.show();


        }

    }


}

