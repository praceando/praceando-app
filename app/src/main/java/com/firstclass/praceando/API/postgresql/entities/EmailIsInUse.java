package com.firstclass.praceando.API.postgresql.entities;

public class EmailIsInUse {
    private boolean emailEmUso;

    public EmailIsInUse(boolean emailEmUso) {
        this.emailEmUso = emailEmUso;
    }

    public boolean getEmailEmUso() {
        return emailEmUso;
    }

}
