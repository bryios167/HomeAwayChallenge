package com.bryan.codetest.homeawaychallenge.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Performer implements Parcelable{

    private String performerId, performerName, performerUrl;
    private Bitmap performerImage;

    public Performer(String performerId, String performerName, String performerUrl, Bitmap performerImage) {
        this.performerId = performerId;
        this.performerName = performerName;
        this.performerUrl = performerUrl;
        this.performerImage = performerImage;
    }

    public Performer(String performerId, String performerName, String performerUrl){
        this(performerId, performerName, performerUrl, null);
    }

    protected Performer(Parcel in) {
        performerId = in.readString();
        performerName = in.readString();
        performerUrl = in.readString();
        performerImage = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(performerId);
        dest.writeString(performerName);
        dest.writeString(performerUrl);
        dest.writeParcelable(performerImage, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Performer> CREATOR = new Creator<Performer>() {
        @Override
        public Performer createFromParcel(Parcel in) {
            return new Performer(in);
        }

        @Override
        public Performer[] newArray(int size) {
            return new Performer[size];
        }
    };

    public String getPerformerId() {
        return performerId;
    }

    public void setPerformerId(String performerId) {
        this.performerId = performerId;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public String getPerformerUrl() {
        return performerUrl;
    }

    public void setPerformerUrl(String performerUrl) {
        this.performerUrl = performerUrl;
    }

    public Bitmap getPerformerImage() {
        return performerImage;
    }

    public void setPerformerImage(Bitmap performerImage) {
        this.performerImage = performerImage;
    }
}
