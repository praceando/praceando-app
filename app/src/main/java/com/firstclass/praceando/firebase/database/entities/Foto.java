package com.firstclass.praceando.firebase.database.entities;

import java.util.Date;

public class Foto {
    private int id;
    private int eventoId;
    private String fotoUrl;
    private Date dataAtualizacao = new Date();

    public Foto(int id, int eventoId, String fotoUrl) {
        this.id = id;
        this.eventoId = eventoId;
        this.fotoUrl = fotoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
