package com.mth.resume_app.exceptions;

public class UserNotFoundException extends ResumeAppException{
  public UserNotFoundException(String message) {
    super(message);
  }
}
