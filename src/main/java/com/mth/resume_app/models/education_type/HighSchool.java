package com.mth.resume_app.models.education_type;

import com.mth.resume_app.models.Education;
import com.mth.resume_app.models.dtos.EducationDTO;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class HighSchool extends Education {

    boolean schoolLeavingExam;

    @Override
    public EducationDTO toDTO() {
        EducationDTO dto = super.toDTO();
        dto.setSchoolLeavingExam(this.schoolLeavingExam);
        return dto;
    }
}
