package com.firstclass.praceando.API.postgresql.entities;

import java.util.List;

public class Interesse {
    private long idUsuario;
    private Long idEvento;
    private List<String> tags;

    public Interesse(long idUsuario, Long idEvento, List<String> tags) {
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Interesse{" +
                "idUsuario=" + idUsuario +
                ", idEvento=" + idEvento +
                ", tags=" + tags +
                '}';
    }
}
