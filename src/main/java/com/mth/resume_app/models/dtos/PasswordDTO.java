package com.mth.resume_app.models.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {

    String currentPassword;
    String password;
    String confirmPassword;

}
