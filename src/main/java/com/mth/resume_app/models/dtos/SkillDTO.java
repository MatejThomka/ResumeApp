package com.mth.resume_app.models.dtos;

import com.mth.resume_app.models.enums.Level;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {

    Integer id;
    String name;
    Level level;
}
