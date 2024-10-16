package com.firstclass.praceando.entities;

public class Goal {
    private String description;
    private String score;

    public Goal(String description, String score) {
        this.description = description;
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public String getScore() {
        return score;
    }
}
