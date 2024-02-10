package com.mth.resume_app.models.enums;

import lombok.Getter;

@Getter
public enum Level {
    BEGINNER ("Beginner"),
    BASIC ("Basic"),
    SKILLFUL ("Skillful"),
    ADVANCED ("Advanced"),
    EXPERT ("Expert");

    private final String value;
    Level(String value) {
        this.value = value;
    }
}
