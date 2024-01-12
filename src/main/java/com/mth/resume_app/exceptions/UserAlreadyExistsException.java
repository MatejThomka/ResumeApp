package com.mth.resume_app.exceptions;

public class UserAlreadyExistsException extends ResumeAppException {
  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
