package com.resumeApp.ResumeApp.services;

import com.resumeApp.ResumeApp.exceptions.ResumeAppException;
import com.resumeApp.ResumeApp.models.PersonalDetails;
import com.resumeApp.ResumeApp.models.User;
import com.resumeApp.ResumeApp.models.dtos.PersonalDetailsDTO;

public interface PersonalDetailsService {

  PersonalDetailsDTO createPersonalDetails(User user, PersonalDetails personalDetails);
  PersonalDetailsDTO updatePersonalDetails(User user, PersonalDetails personalDetails);
  void deletePersonalDetails(User user);
  PersonalDetailsDTO showPersonalDetails(User user) throws ResumeAppException;
}
