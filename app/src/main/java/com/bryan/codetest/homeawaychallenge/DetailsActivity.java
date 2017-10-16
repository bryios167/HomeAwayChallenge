package com.bryan.codetest.homeawaychallenge;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bryan.codetest.homeawaychallenge.adapter.PerformersAdapter;
import com.bryan.codetest.homeawaychallenge.database.EventDBHelper;
import com.bryan.codetest.homeawaychallenge.model.Event;
import com.bryan.codetest.homeawaychallenge.model.Performer;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    public static final String EVENT_EXTRA = "event_extra";
    public static final String PERFORMER_EXTRA = "performer_extra";
    private final int FAVORITE = 1, NONFAVORITE = 2;

    private Event theEvent;

    private EventDBHelper db;

    private RecyclerView recyclerView;
    private PerformersAdapter performersAdapter;

    private ArrayList<Performer> mPerformerList;
    private FloatingActionButton mButtonFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle = getIntent().getExtras();

        theEvent = (Event) bundle.get(EVENT_EXTRA);

        mButtonFavorite = (FloatingActionButton) findViewById(R.id.fab_favorited);
        db = new EventDBHelper(this);

        String something = theEvent.getEventID();

        if(db.eventExists(something)){
            mButtonFavorite.setImageResource(R.drawable.ic_favorite_white_24dp);
            mButtonFavorite.setTag(FAVORITE);
        }
        else {
            mButtonFavorite.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            mButtonFavorite.setTag(NONFAVORITE);
        }

        mButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((int) mButtonFavorite.getTag() == NONFAVORITE) {
                    mButtonFavorite.setImageResource(R.drawable.ic_favorite_white_24dp);
                    mButtonFavorite.setTag(FAVORITE);
                    db.addEvent(theEvent.getEventID(),theEvent.getEventTitle());
                    Toast.makeText(DetailsActivity.this,"Event Added To Favorites",Toast.LENGTH_SHORT).show();
                } else {
                    mButtonFavorite.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    mButtonFavorite.setTag(NONFAVORITE);

                    db.deleteEvent(theEvent.getEventID());
                    Toast.makeText(DetailsActivity.this,"Event Removed From Favorites",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        TextView eventDate = (TextView) findViewById(R.id.event_details_date);
        TextView eventUrl = (TextView) findViewById(R.id.event_details_url);

        TextView venueName = (TextView) findViewById(R.id.venue_details_name);
        TextView venueAddress = (TextView) findViewById(R.id.venue_details_address);
        TextView venueAddressS = (TextView) findViewById(R.id.venue_details_address_second);

        TextView statsLow = (TextView) findViewById(R.id.stats_details_low);
        TextView statsHigh = (TextView) findViewById(R.id.stats_details_high);
        TextView statsAverage = (TextView) findViewById(R.id.stats_details_average);

        toolbar.setTitle(theEvent.getEventTitle());
        eventDate.setText(theEvent.getDateOfEvent());
        eventUrl.setText(theEvent.getEventUrl());

        venueName.setText(theEvent.getVenueName());
        venueAddress.setText(theEvent.getVenueAddress());
        venueAddressS.setText(theEvent.getVenueLocation());

        if (!theEvent.getLowestPrice().equals("N/A")) {
            statsLow.setText("Lowest Price: $" + Double.parseDouble(theEvent.getLowestPrice()));
        }
        else {
            statsLow.setText("Lowest Price: " + theEvent.getLowestPrice());
        }

        if (!theEvent.getHighestPrice().equals("N/A")){
            statsHigh.setText("Highest Price: $" + Double.parseDouble(theEvent.getHighestPrice()));
        }
        else {
            statsHigh.setText("Highest Price: N/A");
        }

        if (!theEvent.getAveragePrice().equals("N/A")) {
            statsAverage.setText("Average Price: $" + Double.parseDouble(theEvent.getAveragePrice()));
        }
        else {
            statsAverage.setText("Average Price: N/A");
        }



        mPerformerList = new ArrayList<>();

        mPerformerList = (ArrayList<Performer>) bundle.get(PERFORMER_EXTRA);

        if(mPerformerList.size() > 1){
            TextView performerTitle = (TextView) findViewById(R.id.performer_title);
            performerTitle.setText(R.string.performer_title_b);
        }

        recyclerView = (RecyclerView) findViewById(R.id.performer_details_list);
        recyclerView.setNestedScrollingEnabled(false);

        performersAdapter = new PerformersAdapter(mPerformerList);

        recyclerView.setAdapter(performersAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}
