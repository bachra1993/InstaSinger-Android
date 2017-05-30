package com.esprit.instasinger.Data;

/**
 * Created by bechirkaddech on 11/17/16.
 */

public class UserFollow {

    private String fullname ;
    private String profilePictureURL ;

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getFullname() {

        return fullname;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public UserFollow() {

    }

    public UserFollow(String fullname, String profilePictureURL) {

        this.fullname = fullname;
        this.profilePictureURL = profilePictureURL;
    }
}
