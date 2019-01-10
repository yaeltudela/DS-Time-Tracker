package com.dstimetracker.devsodin.ds_timetracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dstimetracker.devsodin.core.Interval;

import java.util.ArrayList;

/**
 * Adapter class for Intervals.
 * It extends from RecyclerView.Adapter (because this adapter is for RecyclerView)
 * It binds the data from the task intervals.
 */
class IntervalAdapter extends RecyclerView.Adapter<IntervalAdapter.IntervalViewHolder> {

    private ArrayList<Interval> intervals;

    public IntervalAdapter(ArrayList intervals) {
        this.intervals = intervals;
    }

    @NonNull
    @Override
    public IntervalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.interval_layout, viewGroup, false);
        return new IntervalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntervalViewHolder intervalViewHolder, int i) {
        intervalViewHolder.bindData(this.intervals.get(i), i);

    }


    @Override
    public int getItemCount() {
        return intervals.size();
    }


    public static class IntervalViewHolder extends RecyclerView.ViewHolder {

        TextView intervalId;
        TextView intervalStartDate;
        TextView intervalEndDate;
        TextView intervalDuration;

        public IntervalViewHolder(@NonNull View itemView) {
            super(itemView);

            intervalId = itemView.findViewById(R.id.intervalIdText);
            intervalStartDate = itemView.findViewById(R.id.intervalStartDateText);
            intervalEndDate = itemView.findViewById(R.id.intervalEndDateText);
            intervalDuration = itemView.findViewById(R.id.intervalDurationText);
        }


        /**
         * Method that links all the data from the Interval to the layout.
         *
         * @param interval interval structure to get data.
         * @param i        position on array
         */
        private void bindData(final Interval interval, final int i) {
            intervalId.setText(interval.getIdName());
            intervalStartDate.setText(interval.getStartDate().toString());
            intervalEndDate.setText(interval.getEndDate().toString());
            intervalDuration.setText(Long.toString(interval.getDuration()));

        }


    }

}

