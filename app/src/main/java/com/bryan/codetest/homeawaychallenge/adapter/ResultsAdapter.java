package com.bryan.codetest.homeawaychallenge.adapter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bryan.codetest.homeawaychallenge.R;
import com.bryan.codetest.homeawaychallenge.model.Event;

import org.w3c.dom.Text;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private List<Event> eventList;
    private AdapterView.OnItemClickListener onItemClickListener;


    public ResultsAdapter(List<Event> eventList, AdapterView.OnItemClickListener listener){
        this.eventList = eventList;
        this.onItemClickListener = listener;
    }

    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View resultsView = layoutInflater.inflate(R.layout.event_result_item, parent, false);

        return new ResultsAdapter.ViewHolder(resultsView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ResultsAdapter.ViewHolder holder, int position) {
        Event event = eventList.get(position);

        TextView titleView = holder.eventResultsTitle;
        titleView.setText(event.getEventTitle());

        TextView dateView = holder.eventResultsDate;
        dateView.setText(event.getDateOfEvent());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public Event getItem(int i){
        return eventList.get(i);
    }

    public void setDataset(List<Event> items){
        eventList = items;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView eventResultsTitle, eventResultsDate;
        private AdapterView.OnItemClickListener onItemClickListener;

        public ViewHolder (View itemView, AdapterView.OnItemClickListener listener) {
            super(itemView);

            eventResultsTitle = (TextView) itemView.findViewById(R.id.event_results_title);
            eventResultsDate = (TextView) itemView.findViewById(R.id.event_results_date);

            onItemClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }
}
