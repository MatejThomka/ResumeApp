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
  public PersonalDetailsDTO showPersonalDetails(String email) throws ResumeAppException {

    PersonalDetails personalDetails;
    User user;

    user = userCheck(email);
    personalDetails = personalDetailsRepository.findByUser(user).orElseThrow(() -> new DetailsNotFoundException("Details not found!"));

    if (personalDetails.isDriverLicense()) {
      return PersonalDetailsDTO.builder()
          .dateOfBirth(personalDetails.getDateOfBirth())
          .placeOfBirth(personalDetails.getPlaceOfBirth())
          .gender(personalDetails.getGender())
          .drivingGroup(personalDetails.getDrivingGroups())
          .build();
    } else {
      return PersonalDetailsDTO.builder()
          .dateOfBirth(personalDetails.getDateOfBirth())
          .placeOfBirth(personalDetails.getPlaceOfBirth())
          .gender(personalDetails.getGender())
          .build();
    }
  }

  @Override
  public void createPersonalDetails(String email,
                                    PersonalDetails personalDetails) throws ResumeAppException {
    PersonalDetails newPersonalDetails = new PersonalDetails();
    User user;

    user = userCheck(email);
    newPersonalDetails.setUser(user);
    newPersonalDetails.setDateOfBirth(personalDetails.getDateOfBirth());
    newPersonalDetails.setPlaceOfBirth(personalDetails.getPlaceOfBirth());
    newPersonalDetails.setGender(personalDetails.getGender());

    if (personalDetails.isDriverLicense()) {
      newPersonalDetails.setDriverLicense(true);
      newPersonalDetails.setDrivingGroups(personalDetails.getDrivingGroups());
    }

    personalDetailsRepository.save(newPersonalDetails);
  }

  private User userCheck(String email) throws UserNotFoundException {
    return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found!"));
  }
}
