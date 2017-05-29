package com.esprit.instasinger.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bechirkaddech on 11/21/16.
 */

public class Music implements Parcelable {
    int id ;
    String singer ;
    String singerPicture ;
    String song ;
    String preview ;


    public Music() {
    }

    protected Music(Parcel in) {
        singer = in.readString();
        singerPicture = in.readString();
        song = in.readString();
        preview = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setSingerPicture(String singerPicture) {
        this.singerPicture = singerPicture;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getSinger() {

        return singer;
    }

    public String getSingerPicture() {
        return singerPicture;
    }

    public String getSong() {
        return song;
    }

    public String getPreview() {
        return preview;
    }

    public Music(String singer, String singerPicture, String song, String preview) {

        this.singer = singer;
        this.singerPicture = singerPicture;
        this.song = song;
        this.preview = preview;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public Music(int id, String singer, String singerPicture, String song, String preview) {

        this.id = id;
        this.singer = singer;
        this.singerPicture = singerPicture;
        this.song = song;
        this.preview = preview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(singer);
        parcel.writeString(singerPicture);
        parcel.writeString(song);
        parcel.writeString(preview);
    }
}
