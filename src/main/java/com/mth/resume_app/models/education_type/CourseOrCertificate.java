package com.mth.resume_app.models.education_type;

import com.mth.resume_app.models.Education;
import com.mth.resume_app.models.dtos.EducationDTO;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CourseOrCertificate extends Education {

    String nameOfInstitution;

    @Override
    public EducationDTO toDTO() {
        EducationDTO dto = super.toDTO();
        dto.setNameOfInstitution(this.nameOfInstitution);
        return dto;
    }
}
