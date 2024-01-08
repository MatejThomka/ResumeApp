package com.resumeApp.ResumeApp.models.dtos;

import com.resumeApp.ResumeApp.models.enums.DrivingGroups;
import com.resumeApp.ResumeApp.models.enums.Gender;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDetailsDTO {

  String dateOfBirth;
  String placeOfBirth;
  boolean driverLicense;
  Gender gender;
  List<DrivingGroups> drivingGroup;
}
