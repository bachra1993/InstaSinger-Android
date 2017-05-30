package com.esprit.instasinger.Data;

/**
 * Created by bechirkaddech on 12/7/16.
 */

public class Comment {

    String text ;
    String userPostedName ;
    String userPostedPicture ;
    String userPostedUsername ;
    String date ;
    String userId ;

    public void setText(String text) {
        this.text = text;
    }

    public void setUserPostedName(String userPostedName) {
        this.userPostedName = userPostedName;
    }

    public void setUserPostedPicture(String userPostedPicture) {
        this.userPostedPicture = userPostedPicture;
    }

    public void setUserPostedUsername(String userPostedUsername) {
        this.userPostedUsername = userPostedUsername;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {

        return text;
    }

    public String getUserPostedName() {
        return userPostedName;
    }

    public String getUserPostedPicture() {
        return userPostedPicture;
    }

    public String getUserPostedUsername() {
        return userPostedUsername;
    }

    public String getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public Comment(String text, String userPostedName, String userPostedPicture, String userPostedUsername, String date, String userId) {

        this.text = text;
        this.userPostedName = userPostedName;
        this.userPostedPicture = userPostedPicture;
        this.userPostedUsername = userPostedUsername;
        this.date = date;
        this.userId = userId;
    }

    public Comment() {

    }
}
