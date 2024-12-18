package com.firstclass.praceando;

import android.app.Application;

public class Globals extends Application {
    private String nickname;
    private String bio;
    private String userProfileImage;
    private int userRole; // 2 - anunciante 1 - consumidor
    private long id;
    private String token;
    private boolean alreadyNotified = false;
    private boolean isPremium;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public boolean isAlreadyNotified() {
        return alreadyNotified;
    }

    public void setAlreadyNotified(boolean alreadyNotified) {
        this.alreadyNotified = alreadyNotified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getPremium() {return this.isPremium; }

    @Override
    public String toString() {
        return "Globals{" +
                "nickname='" + nickname + '\'' +
                ", bio='" + bio + '\'' +
                ", userProfileImage='" + userProfileImage + '\'' +
                ", userRole=" + userRole +
                ", id=" + id +
                ", token='" + token + '\'' +
                ", alreadyNotified=" + alreadyNotified +
                '}';
    }
}
