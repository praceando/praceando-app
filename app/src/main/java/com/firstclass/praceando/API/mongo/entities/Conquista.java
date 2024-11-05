package com.firstclass.praceando.API.mongo.entities;

public class Conquista {
    private String id;
    private Long idConquista;
    private String nmConquista;
    private String dsConquista;
    private String nmTipo;
    private Long qtPolen;

    public Conquista(String id, Long idConquista, String nmConquista, String dsConquista, String nmTipo, Long qtPolen) {
        this.id = id;
        this.idConquista = idConquista;
        this.nmConquista = nmConquista;
        this.dsConquista = dsConquista;
        this.nmTipo = nmTipo;
        this.qtPolen = qtPolen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdConquista() {
        return idConquista;
    }

    public void setIdConquista(Long idConquista) {
        this.idConquista = idConquista;
    }

    public String getNmConquista() {
        return nmConquista;
    }

    public void setNmConquista(String nmConquista) {
        this.nmConquista = nmConquista;
    }

    public String getDsConquista() {
        return dsConquista;
    }

    public void setDsConquista(String dsConquista) {
        this.dsConquista = dsConquista;
    }

    public String getNmTipo() {
        return nmTipo;
    }

    public void setNmTipo(String nmTipo) {
        this.nmTipo = nmTipo;
    }

    public Long getQtPolen() {
        return qtPolen;
    }

    public void setQtPolen(Long qtPolen) {
        this.qtPolen = qtPolen;
    }
}
