package com.bryan.codetest.homeawaychallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Event implements Parcelable {

    private String eventID, eventTitle, dateOfEvent, eventUrl;
    private String venueName, venueAddress, venueLocation, venueUrl;
    private String lowestPrice, highestPrice, averagePrice;
    private ArrayList<Performer> performerList;

    public Event(String eventID, String eventTitle, String dateOfEvent, String eventUrl,
                 String venueName, String venueAddress, String venueLocation, String venueUrl,
                 String lowestPrice, String highestPrice, String averagePrice,
                 ArrayList<Performer> performerList) {
        this.eventID = eventID;
        this.eventTitle = eventTitle;
        this.dateOfEvent = dateOfEvent;
        this.eventUrl = eventUrl;
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.venueLocation = venueLocation;
        this.venueUrl = venueUrl;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
        this.averagePrice = averagePrice;
        this.performerList = performerList;
    }

    protected Event(Parcel in) {
        eventID = in.readString();
        eventTitle = in.readString();
        dateOfEvent = in.readString();
        eventUrl = in.readString();
        venueName = in.readString();
        venueAddress = in.readString();
        venueLocation = in.readString();
        venueUrl = in.readString();
        lowestPrice = in.readString();
        highestPrice = in.readString();
        averagePrice = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventID);
        dest.writeString(eventTitle);
        dest.writeString(dateOfEvent);
        dest.writeString(eventUrl);
        dest.writeString(venueName);
        dest.writeString(venueAddress);
        dest.writeString(venueLocation);
        dest.writeString(venueUrl);
        dest.writeString(lowestPrice);
        dest.writeString(highestPrice);
        dest.writeString(averagePrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getEventID() {
        return eventID;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getDateOfEvent() {
        return dateOfEvent;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public String getVenueLocation() {
        return venueLocation;
    }

    public String getVenueUrl() {
        return venueUrl;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public ArrayList<Performer> getPerformerList() {
        return performerList;
    }
}
