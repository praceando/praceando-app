package com.firstclass.praceando.API.postgresql.entities;

public class InteresseResponse {
    private boolean userInteressou;

    public InteresseResponse(boolean userInteressou) {
        this.userInteressou = userInteressou;
    }

    public boolean isUserInteressou() {
        return userInteressou;
    }

    public void setUserInteressou(boolean userInteressou) {
        this.userInteressou = userInteressou;
    }

}
