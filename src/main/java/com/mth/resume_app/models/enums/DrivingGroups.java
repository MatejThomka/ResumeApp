package com.mth.resume_app.models.enums;

import com.mth.resume_app.models.PersonalDetails;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public enum DrivingGroups {
  AM ("AM"),
  A1 ("A1"),
  A2 ("A2"),
  A ("A"),
  B1 ("B1"),
  B ("B"),
  C1 ("C1"),
  C ("C"),
  D1 ("D1"),
  D ("D"),
  BE ("BE"),
  C1E ("C1E"),
  CE ("CE"),
  D1E ("D1E"),
  DE ("DE"),
  T ("T");

  @ManyToOne
  PersonalDetails personalDetails;

  private final String value;
  DrivingGroups(String value){
    this.value = value;
  }
}
