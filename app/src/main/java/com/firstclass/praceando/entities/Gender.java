package com.firstclass.praceando.entities;

import androidx.annotation.NonNull;

public class Gender {
    private long id;
    private String dsGenero;

    public Gender(long id, String dsGenero) {
        this.id = id;
        this.dsGenero = dsGenero;
    }

    public Gender(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getDsGenero() {
        return dsGenero;
    }

    @NonNull
    @Override
    public String toString() {
        return dsGenero;
    }
}
