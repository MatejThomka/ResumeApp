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
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class University extends Education {

    String Faculty;

    @Override
    public EducationDTO toDTO() {
        EducationDTO dto = super.toDTO();
        dto.setFaculty(this.getFaculty());
        return dto;
    }
}
