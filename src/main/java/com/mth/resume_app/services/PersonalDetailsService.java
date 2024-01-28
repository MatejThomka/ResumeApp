package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.PersonalDetailsDTO;


public interface PersonalDetailsService {

  PersonalDetailsDTO showPersonalDetails() throws ResumeAppException;
  PersonalDetailsDTO createUpdatePersonalDetails(PersonalDetailsDTO personalDetailsDTO) throws ResumeAppException;
}
