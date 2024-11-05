package com.firstclass.praceando.API.mongo.entities;

import java.util.List;

public class AvaliacoesUsuarios {

    private boolean usuarioJaAvaliou;
    private List<Avaliacao> avaliacoes;

    public AvaliacoesUsuarios(boolean usuarioJaAvaliou, List<Avaliacao> avaliacoes) {
        this.usuarioJaAvaliou = usuarioJaAvaliou;
        this.avaliacoes = avaliacoes;
    }

    public boolean isUsuarioJaAvaliou() {
        return usuarioJaAvaliou;
    }

    public void setUsuarioJaAvaliou(boolean usuarioJaAvaliou) {
        this.usuarioJaAvaliou = usuarioJaAvaliou;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    @Override
    public String toString() {
        return "AvaliacoesUsuarios{" +
                "usuarioJaAvaliou=" + usuarioJaAvaliou +
                ", avaliacoes=" + avaliacoes +
                '}';
    }
}
