package com.firstclass.praceando.API.postgresql.entities;

import java.util.Arrays;

public class Evento2 {
    private Evento evento;
    private String[] tags;

    public Evento2(Evento evento, String[] tags) {
        this.evento = evento;
        this.tags = tags;
    }

    public Evento getEvento() {
        return evento;
    }

    public String[] getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Evento2{" +
                "evento=" + evento +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
