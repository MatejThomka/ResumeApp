package com.mth.resume_app.models.dtos;

import com.mth.resume_app.models.enums.EducationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class EducationDTO {

    Integer id;
    String name;
    EducationType educationType;
    String fieldOfStudy;
    Long yearFrom;
    Long yearTill;
    String description;
    boolean isStudying;
    String city;
    boolean schoolLeavingExam;
    String faculty;
    String nameOfInstitution;
}
