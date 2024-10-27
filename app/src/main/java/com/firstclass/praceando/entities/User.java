package com.firstclass.praceando.entities;

public class User {
    private String bio;
    private String nome;
    private int tipoUsuario;
    private long id;
    private int iventario;

    public User(String bio, String nome, int tipoUsuario, long id, int iventario) {
        this.bio = bio;
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
        this.id = id;
        this.iventario = iventario;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIventario() {
        return iventario;
    }

    public void setIventario(int iventario) {
        this.iventario = iventario;
    }

    @Override
    public String toString() {
        return "User{" +
                "bio='" + bio + '\'' +
                ", nome='" + nome + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", id=" + id +
                ", iventario=" + iventario +
                '}';
    }
}
