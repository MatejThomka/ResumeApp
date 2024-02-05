package com.mth.resume_app.models.enums;

import lombok.Getter;

@Getter
public enum EducationType {
    HIGH_SCHOOL ("High School"),
    UNIVERSITY ("University"),
    COURSE_OR_CERTIFICATE ("Course or Certificate");

    private final String value;

    EducationType(String value) {
        this.value = value;
    }
}
