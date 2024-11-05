package com.firstclass.praceando.API.postgresql.entities;

import com.firstclass.praceando.entities.Gender;

public class UsuarioAnunciante {

    private long cdInventarioAvatar;
    private Gender genero;
    private String nmUsuario;
    private String dsEmail;
    private String dsSenha;
    private String dtNascimento;
    private String nmNickname;
    private String dsUsuario;
    private long id;
    private String nrCnpj;
    private String telefone = "11987654325";

    public UsuarioAnunciante(long cdInventarioAvatar, Gender genero, String nmUsuario, String dsEmail, String dsSenha, String dtNascimento, String nmNickname, String dsUsuario, String nrCnpj) {
        this.cdInventarioAvatar = cdInventarioAvatar;
        this.genero = genero;
        this.nmUsuario = nmUsuario;
        this.dsEmail = dsEmail;
        this.dsSenha = dsSenha;
        this.dtNascimento = dtNascimento;
        this.nmNickname = nmNickname;
        this.dsUsuario = dsUsuario;
        this.nrCnpj = nrCnpj;
    }

    public long getCdInventarioAvatar() {
        return cdInventarioAvatar;
    }

    public void setCdInventarioAvatar(long cdInventarioAvatar) {
        this.cdInventarioAvatar = cdInventarioAvatar;
    }

    public long getId() {
        return id;
    }

    public Gender getGenero() {
        return genero;
    }

    public void setGenero(Gender genero) {
        this.genero = genero;
    }

    public String getNmUsuario() {
        return nmUsuario;
    }

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public String getDsSenha() {
        return dsSenha;
    }

    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(String dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getNmNickname() {
        return nmNickname;
    }

    public void setNmNickname(String nmNickname) {
        this.nmNickname = nmNickname;
    }

    public String getDsUsuario() {
        return dsUsuario;
    }

    public String getNrCnpj() {
        return nrCnpj;
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
                ", nmNickname='" + nmNickname + '\'' +
                ", dsUsuario='" + dsUsuario + '\'' +
                ", nrCnpj='" + nrCnpj + '\'' +
                '}';
    }
}

