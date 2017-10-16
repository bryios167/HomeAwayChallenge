package com.bryan.codetest.homeawaychallenge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bryan.codetest.homeawaychallenge.R;
import com.bryan.codetest.homeawaychallenge.model.Event;
import com.bryan.codetest.homeawaychallenge.model.Performer;

import java.util.ArrayList;
import java.util.List;

public class PerformersAdapter extends RecyclerView.Adapter<PerformersAdapter.ViewHolder> {

    private ArrayList<Performer> performersList;

    public PerformersAdapter(ArrayList<Performer> performersList){
        this.performersList = performersList;
    }

    @Override
    public PerformersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View resultsView = layoutInflater.inflate(R.layout.performer_details_item, parent, false);

        return new PerformersAdapter.ViewHolder(resultsView);
    }

    @Override
    public void onBindViewHolder(PerformersAdapter.ViewHolder holder, int position) {

        Performer performer = performersList.get(position);

        ImageView performerImageView = holder.performerImageView;

        if(performer.getPerformerImage() != null){
            performerImageView.setImageBitmap(performer.getPerformerImage());
        }

        TextView performerNameView = holder.performerName;
        performerNameView.setText(performer.getPerformerName());

        TextView performerUrlView = holder.performerUrl;
        performerUrlView.setText(performer.getPerformerUrl());

    }

    @Override
    public int getItemCount() {
        return performersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView performerImageView;
        private TextView performerName, performerUrl;

        public ViewHolder (View itemView) {
            super(itemView);

            performerImageView = (ImageView) itemView.findViewById(R.id.performer_image);

            performerName = (TextView) itemView.findViewById(R.id.performer_name);
            performerUrl = (TextView) itemView.findViewById(R.id.performer_url);

            itemView.setTag(itemView);
        }
    }
}
