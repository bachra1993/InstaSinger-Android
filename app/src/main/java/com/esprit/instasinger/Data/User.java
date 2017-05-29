package com.esprit.instasinger.Data;

/**
 * Created by Firovski on 11/15/2016.
 */

public class User {
    private String email;
    private int followersCount;
    private int followingCount;
    private String fullname;
    private String profilePictureURL;
    private String username;

    public User(int followersCount, String email, int followingCount, String fullname, String profilePictureURL, String username) {
        this.followersCount = followersCount;
        this.email = email;
        this.followingCount = followingCount;
        this.fullname = fullname;
        this.profilePictureURL = profilePictureURL;
        this.username = username;
    }


    public User(String email, String fullname, String profilePictureURL, String username) {
        this.email = email;
        this.fullname = fullname;
        this.profilePictureURL = profilePictureURL;
        this.username = username;
    }


    public User(String fullname, String profilePictureURL) {
        this.fullname = fullname;
        this.profilePictureURL = profilePictureURL;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }
}
