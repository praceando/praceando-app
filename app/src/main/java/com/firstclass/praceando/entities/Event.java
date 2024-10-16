package com.firstclass.praceando.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private String title;
    private String locale;
    private String date;
    private String time;
    private String imageUrl;
    private Tag[] tags;

    public Event(String time, String locale, String date, String title) {
        this.date = date;
        this.title = title;
        this.locale = locale;
        this.time = time;
    }

    public Event(String time, String locale, String date, String title, String imageUrl, Tag[] tags) {
        this.date = date;
        this.title = title;
        this.locale = locale;
        this.time = time;
        this.tags = tags;
        this.imageUrl = imageUrl;
    }

    protected Event(Parcel in) {
        title = in.readString();
        locale = in.readString();
        date = in.readString();
        time = in.readString();
        imageUrl = in.readString();
        tags = in.createTypedArray(Tag.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(locale);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(imageUrl);
        dest.writeTypedArray(tags, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }
}
