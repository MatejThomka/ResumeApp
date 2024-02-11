package com.mth.resume_app.models;

import com.mth.resume_app.models.enums.Roles;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;
  String username;
  String name;
  String lastname;
  String email;
  String password;
  String phoneNumber;
  Roles role;

  @OneToOne
  PersonalDetails personalDetails;

  @OneToMany
  List<Education> educations;

  @OneToMany
  List<Skill> skills;

  @OneToMany
  List<Experience> experiences;
}
