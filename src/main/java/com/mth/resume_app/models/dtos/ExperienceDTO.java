package com.mth.resume_app.models.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceDTO {

    Integer id;
    String title;
    String employer;
    String country;
    String city;
    String dateFrom;
    String dateTill;
    String description;
}
