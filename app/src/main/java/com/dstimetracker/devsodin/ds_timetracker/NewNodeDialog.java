package com.dstimetracker.devsodin.ds_timetracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.dstimetracker.devsodin.core.BaseTask;
import com.dstimetracker.devsodin.core.Node;
import com.dstimetracker.devsodin.core.Project;

public class NewNodeDialog extends DialogFragment {
    private EditText nodeName;
    private EditText nodeDescription;
    private boolean isTask;

    public static NewNodeDialog newInstance(boolean isTask) {

        Bundle args = new Bundle();
        args.putBoolean("isTask", isTask);
        NewNodeDialog fragment = new NewNodeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("isTask")) {
            this.isTask = getArguments().getBoolean("isTask");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View nodeDialogView = inflater.inflate(R.layout.new_node_layout, container, false);

        nodeName = nodeDialogView.findViewById(R.id.nameEditText);
        nodeDescription = nodeDialogView.findViewById(R.id.descriptionEditText);

        setHasOptionsMenu(true);
        Toolbar toolbar = nodeDialogView.findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        if (this.isTask) {
            toolbar.setTitle(R.string.newTaskString);

        } else {
            toolbar.setTitle(R.string.newProjectString);

        }

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);


        }


        return nodeDialogView;

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        if (getActivity() != null) {
            TreeViewerActivity treeViewerActivity = (TreeViewerActivity) getActivity();
            treeViewerActivity.adapter.notifyItemInserted(treeViewerActivity.adapter.getItemCount());

        }
        super.onDismiss(dialog);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_node_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveMenu:
                if (isDataOk()) {
                    if (isTask) {
                        Node node = new BaseTask(nodeName.getText().toString(), nodeDescription.getText().toString(), (Project) TreeViewerActivity.node);
                    } else {
                        Node node = new Project(nodeName.getText().toString(), nodeDescription.getText().toString(), (Project) TreeViewerActivity.node);
                    }
                    Toast.makeText(this.getContext(), "Project added succesfully", Toast.LENGTH_LONG).show();
                    dismiss();
                } else {
                    Toast.makeText(this.getContext(), "Project needs a name", Toast.LENGTH_LONG).show();

                }
                break;

            default:
                dismiss();
        }


        return super.onOptionsItemSelected(item);
    }

    private boolean isDataOk() {
        return !nodeName.getText().toString().isEmpty();

    }
}
