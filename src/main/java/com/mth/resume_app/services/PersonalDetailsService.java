package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.PersonalDetailsDTO;


public interface PersonalDetailsService {

  PersonalDetailsDTO showPersonalDetails(String username) throws ResumeAppException;
  PersonalDetailsDTO createUpdatePersonalDetails(String username, PersonalDetailsDTO personalDetailsDTO) throws ResumeAppException;
  void removePersonalDetails(User user) throws ResumeAppException;
}
