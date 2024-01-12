package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.PersonalDetails;
import com.mth.resume_app.models.dtos.PersonalDetailsDTO;


public interface PersonalDetailsService {

  PersonalDetailsDTO showPersonalDetails(String email) throws ResumeAppException;
  void createPersonalDetails(String email, PersonalDetails personalDetails) throws ResumeAppException;
}
