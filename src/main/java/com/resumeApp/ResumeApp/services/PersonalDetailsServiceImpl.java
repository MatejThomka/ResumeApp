package com.resumeApp.ResumeApp.services;

import com.resumeApp.ResumeApp.exceptions.DetailsNotFoundException;
import com.resumeApp.ResumeApp.exceptions.ResumeAppException;
import com.resumeApp.ResumeApp.exceptions.UserNotFoundException;
import com.resumeApp.ResumeApp.models.PersonalDetails;
import com.resumeApp.ResumeApp.models.User;
import com.resumeApp.ResumeApp.models.dtos.PersonalDetailsDTO;
import com.resumeApp.ResumeApp.repositories.PersonalDetailsRepository;
import com.resumeApp.ResumeApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalDetailsServiceImpl implements PersonalDetailsService {

  private final UserRepository userRepository;
  private final PersonalDetailsRepository personalDetailsRepository;

  @Override
  public PersonalDetailsDTO createPersonalDetails(User user, PersonalDetails personalDetails) {
    return null;
  }

  @Override
  public PersonalDetailsDTO updatePersonalDetails(User user, PersonalDetails personalDetails) {
    return null;
  }

  @Override
  public void deletePersonalDetails(User user) {

  }

  @Override
  public PersonalDetailsDTO showPersonalDetails(User user) throws ResumeAppException {

    userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found!"));
    personalDetailsRepository.findByUser(user).orElseThrow(() -> new DetailsNotFoundException("Details not found!"));

    return null;
  }
}
