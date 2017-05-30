package com.esprit.instasinger.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bechirkaddech on 12/5/16.
 */

public class FavoriteMusic implements Parcelable {
    int id ;
    String singer ;
    String singerPicture ;
    String song ;
    String preview ;

    protected FavoriteMusic(Parcel in) {
        id = in.readInt();
        singer = in.readString();
        singerPicture = in.readString();
        song = in.readString();
        preview = in.readString();
    }

    public static final Creator<FavoriteMusic> CREATOR = new Creator<FavoriteMusic>() {
        @Override
        public FavoriteMusic createFromParcel(Parcel in) {
            return new FavoriteMusic(in);
        }

        @Override
        public FavoriteMusic[] newArray(int size) {
            return new FavoriteMusic[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

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

    public int getId() {
        return id;
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

    public FavoriteMusic(int id, String singer, String singerPicture, String song, String preview) {

        this.id = id;
        this.singer = singer;
        this.singerPicture = singerPicture;
        this.song = song;
        this.preview = preview;
    }

    public FavoriteMusic() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(singer);
        parcel.writeString(singerPicture);
        parcel.writeString(song);
        parcel.writeString(preview);
    }
}
