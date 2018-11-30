package com.dstimetracker.devsodin.ds_timetracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dstimetracker.devsodin.core.Node;

import java.util.ArrayList;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeViewHolder> {
    private ArrayList<Node> nodes = new ArrayList<>();
    private Context context;


    public NodeAdapter(Context context, ArrayList<Node> nodes) {
        this.context = context;
        this.nodes = nodes;
    }

    @NonNull
    @Override
    public NodeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.node_layout, viewGroup, false);
        return new NodeViewHolder(view, nodes.get(i));
    }

    @Override
    public void onBindViewHolder(@NonNull NodeViewHolder nodeViewHolder, int i) {
        Node node = nodes.get(i);
        nodeViewHolder.nodeName.setText(node.getName());
        nodeViewHolder.nodeDuration.setText(Double.toString(node.getDuration()));
    }


    @Override
    public int getItemCount() {
        return nodes.size();
    }


    public class NodeViewHolder extends RecyclerView.ViewHolder {

        TextView nodeName;
        TextView nodeDuration;

        public NodeViewHolder(@NonNull final View itemView, final Node node) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("NEWNODE", node);
                    v.getContext().startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(), nodeName.getText(), Toast.LENGTH_LONG).show();
                    return true;
                }
            });

            nodeName = itemView.findViewById(R.id.nodeNameText);
            nodeDuration = itemView.findViewById(R.id.nodeDurationText);

        }
    }

}
