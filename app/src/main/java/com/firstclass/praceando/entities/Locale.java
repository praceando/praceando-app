package com.firstclass.praceando.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Locale implements Parcelable {
    private Long id;
    private String name;
    private String openingTime;
    private String closingTime;

    public Locale(Long id, String name, String openingTime, String closingTime) {
        this.name = name;
        this.id = id;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    protected Locale(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        openingTime = in.readString();
        closingTime = in.readString();
    }

    public static final Creator<Locale> CREATOR = new Creator<Locale>() {
        @Override
        public Locale createFromParcel(Parcel in) {
            return new Locale(in);
        }

        @Override
        public Locale[] newArray(int size) {
            return new Locale[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(name);
        dest.writeString(openingTime);
        dest.writeString(closingTime);
    }
}
