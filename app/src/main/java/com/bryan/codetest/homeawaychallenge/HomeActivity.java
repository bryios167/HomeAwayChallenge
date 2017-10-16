package com.bryan.codetest.homeawaychallenge;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.bryan.codetest.homeawaychallenge.adapter.ResultsAdapter;
import com.bryan.codetest.homeawaychallenge.model.Event;
import com.bryan.codetest.homeawaychallenge.model.Performer;
import com.bryan.codetest.homeawaychallenge.networking.EventResultsInterface;
import com.bryan.codetest.homeawaychallenge.networking.EventTask;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements EventResultsInterface {

    private final String BASE_URL = "https://api.seatgeek.com/2/events?";
    private final String API = "OTI1NDY1NHwxNTA3OTE0NTMxLjg2";
    private final String SECRET = "4748db215f98d66b3d7298643d9dc714cbfa71ce5b97c0aed08d9bfafbae3588";

    private SearchView searchView;

    private String query;

    private ResultsAdapter resultsAdapter;
    private RecyclerView recyclerView;

    private ProgressBar spinner;
    private static Handler mHandler = new Handler();
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        startProcess(new String[]{});
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            String[] search = query.split("\\s+");
            startProcess(search);
        }
    }

    private void startProcess(String[] entry){
        getUrl(entry);
        this.setContentView(R.layout.activity_home);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
    }

    private void getUrl(String[] search){
        StringBuilder eventsearch = new StringBuilder(BASE_URL);

        eventsearch.append("client_id=").append(API);
        eventsearch.append("&client_secret=").append(SECRET);
        eventsearch.append("&q=");

        for (int i = 0; i < search.length; i++){
            eventsearch.append(search[i]);
            if(i != search.length-1){
                eventsearch.append("+");
            }
        }

        String eventSearchURL = eventsearch.toString();

        getEventResults(eventSearchURL);
    }

    public void getEventResults(String url){
        new EventTask(HomeActivity.this).execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final int delay = 250;
                    if (mRunnable != null) {
                    restartQuery();
                }
                mRunnable = createEventFetchTask(newText);
                mHandler.postDelayed(mRunnable, delay);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void restartQuery() {
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onEventResults(List<Event> eventList) {

        spinner.setVisibility(View.GONE);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = (Event) resultsAdapter.getItem(i);

                ArrayList<Performer> performerList = event.getPerformerList();

                Intent intent = new Intent (HomeActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.EVENT_EXTRA, event);
                intent.putParcelableArrayListExtra(DetailsActivity.PERFORMER_EXTRA, performerList);
                startActivity(intent);
            }
        };

        recyclerView = (RecyclerView) findViewById(R.id.event_results_list);

        resultsAdapter = new ResultsAdapter(eventList, listener);

        recyclerView.setAdapter(resultsAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private Runnable createEventFetchTask(final String query){
        return new Runnable() {
            @Override
            public void run() {
                String[] search = query.split("\\s+");
                startProcess(search);
            }
        };
    }

}
