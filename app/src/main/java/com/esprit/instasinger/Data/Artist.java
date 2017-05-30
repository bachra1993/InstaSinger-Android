package com.esprit.instasinger.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bechirkaddech on 11/27/16.
 */

public class Artist implements Parcelable {

    String name ;
    String artist_picture ;
    String top_url;


    protected Artist(Parcel in) {
        name = in.readString();
        artist_picture = in.readString();
        top_url = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist_picture(String artist_picture) {
        this.artist_picture = artist_picture;
    }

    public void setTop_url(String top_url) {
        this.top_url = top_url;
    }

    public String getName() {

        return name;
    }

    public String getArtist_picture() {
        return artist_picture;
    }

    public String getTop_url() {
        return top_url;
    }

    public Artist(String name, String artist_picture, String top_url) {

        this.name = name;
        this.artist_picture = artist_picture;
        this.top_url = top_url;
    }

    public Artist() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(artist_picture);
        parcel.writeString(top_url);
    }
}
