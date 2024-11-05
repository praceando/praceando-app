package com.firstclass.praceando.API.mongo.entities;

public class Mean {
    private float media;

    public Mean(float media) {
        this.media = media;
    }

    public float getMedia() {
        return media;
    }

    @Override
    public String toString() {
        return "Mean{" +
                "media=" + media +
                '}';
    }
}
