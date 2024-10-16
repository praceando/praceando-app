package com.firstclass.praceando.entities;

public class Avatar {
    private String url;
    private long id;

    public Avatar( long id, String url ) {
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public long getId() {
        return id;
    }
}
