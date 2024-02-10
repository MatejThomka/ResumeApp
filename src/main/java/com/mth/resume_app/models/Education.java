package com.mth.resume_app.models;

import com.mth.resume_app.models.dtos.EducationDTO;
import com.mth.resume_app.models.enums.EducationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String fieldOfStudy;
    Long yearFrom;
    Long yearTill;
    String description;
    boolean isStudying;
    EducationType educationType;
    String city;

    @ManyToOne
    User user;

    public EducationDTO toDTO() {
        EducationDTO dto = new EducationDTO();
        dto.setId(this.id);
        dto.setEducationType(this.educationType);
        dto.setName(this.getName());
        dto.setFieldOfStudy(this.fieldOfStudy);
        dto.setYearFrom(this.yearFrom);
        dto.setYearTill(this.yearTill);
        dto.setDescription(this.description);
        dto.setStudying(this.isStudying);
        dto.setCity(this.city);
        return dto;
    }
}
