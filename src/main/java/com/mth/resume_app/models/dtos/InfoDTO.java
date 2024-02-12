package com.mth.resume_app.models.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class InfoDTO {

    String interestsOrHobbies;
    String personalSummary;
    String linkedin;
    String gitHub;
    String facebook;
}
