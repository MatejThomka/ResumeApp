package com.mth.resume_app.models.enums;

import lombok.Getter;

@Getter
public enum Roles {
  USER("USER"),
  ADMIN("ADMIN"),
  COMPANY("COMPANY");

  private final String value;

  Roles(String value) {
    this.value = value;
  }
}
