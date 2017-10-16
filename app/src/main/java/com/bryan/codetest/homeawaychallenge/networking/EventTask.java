package com.bryan.codetest.homeawaychallenge.networking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.bryan.codetest.homeawaychallenge.model.Event;
import com.bryan.codetest.homeawaychallenge.model.Performer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class EventTask extends AsyncTask<String,String,List<Event>> {

    private WeakReference<EventResultsInterface> observer;
    private String url, eventData;

    private String evId = "", evTitle = "", evDate = "", evUrl = "";
    private String veName = "", veAddress = "", veLocation = "", veUrl = "";
    private String stPriceLow = "", stPriceHigh = "", stPriceAvg = "";
    private Double LowestPrice, HighestPrice, AveragePrice;
    private String peId = "", peName = "", peUrl = "", peImage = "";

    private Bitmap peImageBitmap;

    private List<Event> eventList;

    public EventTask(EventResultsInterface theInterface){
        this.observer = new WeakReference<EventResultsInterface>(theInterface);
    }

    @Override
    protected List<Event> doInBackground(String... urls) {
        url = urls[0];

        DownloadURL downloadURL = new DownloadURL();

        eventList = new ArrayList<>();

        try {
            eventData = downloadURL.readUrl(url);

            JSONObject jsonObject = new JSONObject(eventData);
            JSONArray jsonArray = jsonObject.getJSONArray("events");

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject getEvent = jsonArray.getJSONObject(i);

                evId = getEvent.getString("id");
                evTitle = getEvent.getString("title");
                evDate = getEvent.getString("datetime_local");
                evUrl = getEvent.getString("url");

                JSONObject venueLevel = getEvent.getJSONObject("venue");

                veName = venueLevel.getString("name");
                veAddress = venueLevel.getString("address");
                veLocation = venueLevel.getString("extended_address");
                veUrl = venueLevel.getString("url");

                JSONObject statsLevel = getEvent.getJSONObject("stats");

                stPriceLow = statsLevel.getString("lowest_price");
                if (stPriceLow.equalsIgnoreCase("null")){
                    stPriceLow = "N/A";
                }
                stPriceHigh = statsLevel.getString("highest_price");
                if (stPriceHigh.equalsIgnoreCase("null")){
                    stPriceHigh = "N/A";
                }
                stPriceAvg = statsLevel.getString("average_price");
                if (stPriceAvg.equalsIgnoreCase("null")){
                    stPriceAvg = "N/A";
                }

                JSONArray performersLevel = getEvent.getJSONArray("performers");
                ArrayList<Performer> performerList = new ArrayList<>(performersLevel.length() + 1);

                for (int p = 0; p < performersLevel.length(); p++){
                    JSONObject getPerformer = performersLevel.getJSONObject(p);

                    peId = getPerformer.getString("id");
                    peName = getPerformer.getString("name");
                    peUrl = getPerformer.getString("url");
                    peImage = getPerformer.getString("image");

                    if (!peImage.equalsIgnoreCase("null")) {
                        InputStream in = new java.net.URL(peImage).openStream();
                        peImageBitmap = BitmapFactory.decodeStream(in);
                    }
                    else {
                        peImageBitmap = null;
                    }

                    Performer performer = new Performer(peId, peName, peUrl, peImageBitmap);

                    performerList.add(performer);
                }

                Event anEvent = new Event (
                        evId, evTitle, evDate, evUrl,
                        veName, veAddress, veLocation, veUrl,
                        stPriceLow, stPriceHigh, stPriceAvg,
                        performerList);

                eventList.add(anEvent);
            }

            return eventList;


        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Event> eventList) {
        super.onPostExecute(eventList);

        EventResultsInterface eventResultsInterface = observer.get();

        if(observer != null){
            eventResultsInterface.onEventResults(eventList);
        }
    }
}
