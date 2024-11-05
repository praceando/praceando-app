package com.firstclass.praceando.API.mongo.entities;

import java.util.List;

public class Recorrencia {
    private String nmTipo;
    private long idEvento;

    public Recorrencia(String nmTipo, long idEvento) {
        this.nmTipo = nmTipo;
        this.idEvento = idEvento;
    }

    @Override
    public String toString() {
        return "Recorrencia{" +
                "nmTipo='" + nmTipo + '\'' +
                ", idEvento=" + idEvento +
                '}';
    }
}
