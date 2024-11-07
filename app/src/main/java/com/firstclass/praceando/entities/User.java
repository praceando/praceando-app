package com.firstclass.praceando.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String bio;
    private String nome;
    private int tipoUsuario;
    private long id;
    private int iventario;
    private boolean isPremium;

    public User(String bio, String nome, int tipoUsuario, long id, int iventario, boolean isPremium) {
        this.bio = bio;
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
        this.id = id;
        this.iventario = iventario;
        this.isPremium = isPremium;
    }

    public User(long id) {
        this.id = id;
    }

    protected User(Parcel in) {
        bio = in.readString();
        nome = in.readString();
        tipoUsuario = in.readInt();
        id = in.readLong();
        iventario = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public boolean isPremium() {
        return isPremium;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bio);
        dest.writeString(nome);
        dest.writeInt(tipoUsuario);
        dest.writeLong(id);
        dest.writeInt(iventario);
    }

    @Override
    public String toString() {
        return "User{" +
                "bio='" + bio + '\'' +
                ", nome='" + nome + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", id=" + id +
                ", iventario=" + iventario +
                ", premium=" + isPremium +
                '}';
    }
}
