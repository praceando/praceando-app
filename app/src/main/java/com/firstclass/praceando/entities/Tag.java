package com.firstclass.praceando.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable {
    private Long id;
    private String nmTag;

    public Tag(Long id, String nmTag) {
        this.nmTag = nmTag;
        this.id = id;
    }

    protected Tag(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        nmTag = in.readString();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return nmTag;
    }

    public void setName(String nmTag) {
        this.nmTag = nmTag;
    }

    @Override
    public String toString() {
        return nmTag;
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
        dest.writeString(nmTag);
    }
}
