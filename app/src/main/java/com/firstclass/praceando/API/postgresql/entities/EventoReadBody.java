package com.firstclass.praceando.API.postgresql.entities;

import java.util.List;

public class EventoReadBody {
    private List<Integer> ids;

    public EventoReadBody(List<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "EventoReadBody{" +
                "ids=" + ids +
                '}';
    }
}
