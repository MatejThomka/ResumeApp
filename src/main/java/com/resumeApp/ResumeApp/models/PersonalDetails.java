package com.resumeApp.ResumeApp.models;

import com.resumeApp.ResumeApp.models.enums.DrivingGroups;
import com.resumeApp.ResumeApp.models.enums.Gender;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonalDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;
  String dateOfBirth;
  String placeOfBirth;
  boolean driverLicense;
  Gender gender;

  @ElementCollection(targetClass = DrivingGroups.class)
  @Enumerated(EnumType.STRING)
  List<DrivingGroups> drivingGroups;

  @OneToOne
  User user;
}
