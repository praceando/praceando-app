package com.firstclass.praceando.API.postgresql.entities;

public class NicknameIsInUse {
    private boolean nicknameEmUso;

    public NicknameIsInUse(boolean nicknameEmUso) {
        this.nicknameEmUso = nicknameEmUso;
    }

    public boolean getNicknameEmUso() {
        return nicknameEmUso;
    }

}
