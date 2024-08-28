package com.firstclass.praceando.entities;

public class Event {
    private String title;
    private String locale;
    private String date;
    private String time;

    public Event(String time, String locale, String date, String title) {
        this.date = date;
        this.title = title;
        this.locale = locale;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
