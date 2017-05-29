package com.esprit.instasinger.Data;

/**
 * Created by bechirkaddech on 11/16/16.
 */

public class Videos {

    private String URL ;
    private String comments ;
    private String likes ;
    private String singer ;
    private String song ;
    private String songPicture ;





    public Videos(String URL, String comments, String likes) {
        this.URL = URL;
        this.comments = comments;
        this.likes = likes;
    }


    public Videos() {
    }

    public Videos(String URL, String comments, String likes, String singer, String song, String sonPicture) {
        this.URL = URL;
        this.comments = comments;
        this.likes = likes;
        this.singer = singer;
        this.song = song;
        this.songPicture = sonPicture;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public void setSongPicture(String sonPicture) {
        this.songPicture = sonPicture;
    }

    public String getSinger() {

        return singer;
    }

    public String getSong() {
        return song;
    }

    public String getSongPicture() {
        return songPicture;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getURL() {

        return URL;
    }

    public String getComments() {
        return comments;
    }

    public String getLikes() {
        return likes;
    }
}
