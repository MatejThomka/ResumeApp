package com.resumeApp.ResumeApp.services;

import com.resumeApp.ResumeApp.exceptions.ResumeAppException;
import com.resumeApp.ResumeApp.models.PersonalDetails;
import com.resumeApp.ResumeApp.models.User;
import com.resumeApp.ResumeApp.models.dtos.PersonalDetailsDTO;

public interface PersonalDetailsService {

  PersonalDetailsDTO showPersonalDetails(String email) throws ResumeAppException;
  void createPersonalDetails(String email, PersonalDetails personalDetails) throws ResumeAppException;
}
