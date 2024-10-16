package com.firstclass.praceando.entities;

public class Review {
    private String nickname;
    private String userImage;
    private float rating;
    private String comment;

    public Review(String nickname, String userImage, float rating, String comment) {
        this.nickname = nickname;
        this.userImage = userImage;
        this.rating = rating;
        this.comment = comment;
    }

    public Review(String nickname, String userImage, float rating) {
        this.nickname = nickname;
        this.userImage = userImage;
        this.rating = rating;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserImage() {
        return userImage;
    }

    public float getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
