package com.firstclass.praceando.entities;

public class Goal {
    private String description;
    private String score;
    private String title;

    public Goal(String description, String score, String title) {
        this.description = description;
        this.score = score;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getScore() {
        return score;
    }

    public String getTitle() {
        return title;
    }
}
