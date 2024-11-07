package com.firstclass.praceando.API.postgresql.entities;

import com.firstclass.praceando.entities.Gender;

public class UsuarioAnunciante {

    private long cdInventarioAvatar;
    private Gender genero;
    private String nmUsuario;
    private String dsEmail;
    private String dsSenha;
    private String dtNascimento;
    private String nmEmpresa;
    private String dsUsuario;
    private long id;
    private String nrCnpj;
    private String telefone = "11987654325";

    public UsuarioAnunciante(long cdInventarioAvatar, Gender genero, String nmUsuario, String dsEmail, String dsSenha, String dtNascimento, String nmEmpresa, String dsUsuario, String nrCnpj) {
        this.cdInventarioAvatar = cdInventarioAvatar;
        this.genero = genero;
        this.nmUsuario = nmUsuario;
        this.dsEmail = dsEmail;
        this.dsSenha = dsSenha;
        this.dtNascimento = dtNascimento;
        this.nmEmpresa = nmEmpresa;
        this.dsUsuario = dsUsuario;
        this.nrCnpj = nrCnpj;
    }

    public long getId() {
        return id;
    }

    public String getNmNickname() {
        return nmEmpresa;
    }

    public String getDsUsuario() {
        return dsUsuario;
    }

    @Override
    public String toString() {
        return "UsuarioConsumidor{" +
                "cdInventarioAvatar=" + cdInventarioAvatar +
                ", genero=" + genero +
                ", nmUsuario='" + nmUsuario + '\'' +
                ", dsEmail='" + dsEmail + '\'' +
                ", dsSenha='" + dsSenha + '\'' +
                ", dtNascimento='" + dtNascimento + '\'' +
                ", nmNickname='" + nmEmpresa + '\'' +
                ", dsUsuario='" + dsUsuario + '\'' +
                ", nrCnpj='" + nrCnpj + '\'' +
                '}';
    }
}

