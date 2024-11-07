package com.firstclass.praceando.API.postgresql.entities;

public class ProfileUser {
    private long id;
    private String name;
    private String bio;

    public ProfileUser(long id, String name, String bio) {
        this.id = id;
        this.name = name;
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "ProfileUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
