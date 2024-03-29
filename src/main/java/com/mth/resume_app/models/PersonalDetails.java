package com.mth.resume_app.models;

import com.mth.resume_app.models.enums.DrivingGroups;
import com.mth.resume_app.models.enums.Gender;
import jakarta.persistence.*;

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
  String city;
  String streetAndNumber;
  String postalCode;
  String country;
  Gender gender;
  boolean drivingLicence;

  @ElementCollection(targetClass = DrivingGroups.class, fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  List<DrivingGroups> drivingGroups;

  @OneToOne
  User user;
}
