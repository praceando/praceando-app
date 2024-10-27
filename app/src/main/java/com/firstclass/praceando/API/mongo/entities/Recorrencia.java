package com.firstclass.praceando.API.mongo.entities;

import java.util.List;

public class Recorrencia {
    private String id;
    private Long idRecorrencia;
    private String dsTipo;
    private List<Integer> dsDiasDaSemana;
    private Long cdEvento;

    public Recorrencia(String id, Long idRecorrencia, String dsTipo, List<Integer> dsDiasDaSemana, Long cdEvento) {
        this.id = id;
        this.idRecorrencia = idRecorrencia;
        this.dsTipo = dsTipo;
        this.dsDiasDaSemana = dsDiasDaSemana;
        this.cdEvento = cdEvento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdRecorrencia() {
        return idRecorrencia;
    }

    public void setIdRecorrencia(Long idRecorrencia) {
        this.idRecorrencia = idRecorrencia;
    }

    public String getDsTipo() {
        return dsTipo;
    }

    public void setDsTipo(String dsTipo) {
        this.dsTipo = dsTipo;
    }

    public List<Integer> getDsDiasDaSemana() {
        return dsDiasDaSemana;
    }

    public void setDsDiasDaSemana(List<Integer> dsDiasDaSemana) {
        this.dsDiasDaSemana = dsDiasDaSemana;
    }

    public Long getCdEvento() {
        return cdEvento;
    }

    public void setCdEvento(Long cdEvento) {
        this.cdEvento = cdEvento;
    }
}
