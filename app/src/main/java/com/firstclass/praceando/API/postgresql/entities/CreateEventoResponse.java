package com.firstclass.praceando.API.postgresql.entities;

public class CreateEventoResponse {
    private long idEvento;

    public long getIdEvento() {
        return idEvento;
    }

    public CreateEventoResponse(long idEvento) {
        this.idEvento = idEvento;
    }
}
