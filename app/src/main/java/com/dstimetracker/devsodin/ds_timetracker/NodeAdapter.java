package com.dstimetracker.devsodin.ds_timetracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dstimetracker.devsodin.core.Node;

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
        nodeViewHolder.bindData(this.nodes.get(i), i);

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

        ImageButton optionsButton;


        public NodeViewHolder(@NonNull View itemView) {
            super(itemView);

            nodeName = itemView.findViewById(R.id.nodeNameText);
            nodeDuration = itemView.findViewById(R.id.nodeDurationText);
            nodeStartDate = itemView.findViewById(R.id.nodeStartDateText);
            nodeEndDate = itemView.findViewById(R.id.nodeEndDateText);

        }

        private void bindData(final Node node, final int i) {
            nodeName.setText(node.getName());
            nodeDuration.setText(Double.toString(node.getDuration()));
            if (node.getStartDate() != null) {
                nodeStartDate.setText(node.getStartDate().toString());
            } else {
                nodeStartDate.setText("---");
            }
            nodeStartDate.setVisibility(View.GONE);
            if (node.getEndDate() != null) {
                nodeEndDate.setText(node.getEndDate().toString());
            } else {
                nodeEndDate.setText("---");
            }
            nodeEndDate.setVisibility(View.GONE);

            if (!node.isTask()) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra("NEWNODE", node);
                        v.getContext().startActivity(intent);
                    }
                });
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    nodeEndDate.setVisibility(View.VISIBLE);
                    nodeEndDate.setVisibility(View.VISIBLE);
                    return true;
                }
            });
        }



    }

}
