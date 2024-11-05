package com.firstclass.praceando.API.mongo.entities;

import java.time.LocalDateTime;

public class Avaliacao {
    private String id;
    private Long idAvaliacao;
    private Long cdEvento;
    private Long cdUsuario;
    private float nrNota;
    private String dsComentario;

    public Avaliacao(String dsComentario, Long nrNota, Long cdUsuario, Long cdEvento, Long idAvaliacao, String id) {
        this.dsComentario = dsComentario;
        this.nrNota = nrNota;
        this.cdUsuario = cdUsuario;
        this.cdEvento = cdEvento;
        this.idAvaliacao = idAvaliacao;
        this.id = id;
    }

    public Avaliacao(Long cdEvento, Long cdUsuario, float nrNota, String dsComentario) {
        this.cdEvento = cdEvento;
        this.cdUsuario = cdUsuario;
        this.nrNota = nrNota;
        this.dsComentario = dsComentario;
    }

    public Avaliacao(Long cdEvento, Long cdUsuario, float nrNota) {
        this.cdEvento = cdEvento;
        this.cdUsuario = cdUsuario;
        this.nrNota = nrNota;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Long getCdEvento() {
        return cdEvento;
    }

    public void setCdEvento(Long cdEvento) {
        this.cdEvento = cdEvento;
    }

    public Long getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Long cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public float getNrNota() {
        return nrNota;
    }

    public void setNrNota(float nrNota) {
        this.nrNota = nrNota;
    }

    public String getDsComentario() {
        return dsComentario;
    }

    public void setDsComentario(String dsComentario) {
        this.dsComentario = dsComentario;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "id='" + id + '\'' +
                ", idAvaliacao=" + idAvaliacao +
                ", cdEvento=" + cdEvento +
                ", cdUsuario=" + cdUsuario +
                ", nrNota=" + nrNota +
                ", dsComentario='" + dsComentario + '\'' +
                '}';
    }
}
