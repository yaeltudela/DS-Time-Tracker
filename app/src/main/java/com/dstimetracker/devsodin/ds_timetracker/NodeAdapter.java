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

import com.dstimetracker.devsodin.core.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeViewHolder> {
    public static final String START = "start";
    public static final String STOP = "stop";
    public static final String REMOVE = "remove";
    public static final String EDIT = "edit";
    public static final String CHILDREN = "CHILDREN";


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
                        removeNode();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        };


        TextView nodeName;
        TextView nodeDuration;

        ImageButton changeStatus;
        TextView threeDots;
        ImageView activeBar;

        int nodePosition;
        Node node;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM - kk:mm", Locale.ENGLISH);

        public NodeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);

            nodeName = itemView.findViewById(R.id.nodeNameText);
            nodeDuration = itemView.findViewById(R.id.nodeDurationText);
            changeStatus = itemView.findViewById(R.id.taskStatus);
            threeDots = itemView.findViewById(R.id.threeDots);
            activeBar = itemView.findViewById(R.id.status);


        }

        private void removeNode() {

            Intent broadcast = new Intent(REMOVE);
            broadcast.putExtra("type", REMOVE);
            broadcast.putExtra("nodePosition", nodePosition);

            itemView.getContext().sendBroadcast(broadcast);
            notifyItemRemoved(getAdapterPosition());

        }

        private void bindData(ArrayList<Node> nodes, final int i) {
            this.node = nodes.get(i);
            updateIcon(nodes.get(i));
            nodeName.setText(nodes.get(i).getName());
            nodeDuration.setText(Double.toString(nodes.get(i).getDuration()) + " secs.");
            nodePosition = getAdapterPosition();

            if (!nodes.get(i).isTask()) {
                changeStatus.setVisibility(View.GONE);
            } else {
                changeStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        if (node.isActive()) {
                            intent = new Intent(STOP);
                            intent.putExtra("type", STOP);
                        } else {
                            intent = new Intent(START);
                            intent.putExtra("type", START);
                        }
                        intent.putExtra("nodePosition", getAdapterPosition());
                        itemView.getContext().sendBroadcast(intent);
                        updateIcon(node);

                    }
                });
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CHILDREN);
                    intent.putExtra("type", CHILDREN);
                    intent.putExtra("nodePosition", getAdapterPosition());
                    v.getContext().sendBroadcast(intent);
                    notifyDataSetChanged();
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
            if (node.getStartDate() != null) {
                startDate.setText(node.getStartDate().toString());
                endDate.setText(node.getEndDate().toString());
            }
            duration.setText(Long.toString(node.getDuration()));

        }

        private void showEditDialog() {
            AlertDialog.Builder editDialog = new AlertDialog.Builder(itemView.getContext());
            editDialog.setTitle(R.string.editMenuEdit);

            final View editView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.edit_node, null);
            editDialog.setView(editView);

            editDialog.create();

            final TextView name = editView.findViewById(R.id.editName);
            final TextView description = editView.findViewById(R.id.editDescription);
            name.setText(node.getName());
            description.setText(node.getDescription());

            editDialog.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent broadcast = new Intent(EDIT);
                    broadcast.putExtra("type", EDIT);
                    broadcast.putExtra("nodeName", name.getText().toString());
                    broadcast.putExtra("nodeDescription", description.getText().toString());
                    broadcast.putExtra("nodePosition", nodePosition);
                    itemView.getContext().sendBroadcast(broadcast);

                    dialog.dismiss();
                    notifyDataSetChanged();
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

