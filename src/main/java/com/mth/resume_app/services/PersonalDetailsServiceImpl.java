package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.DetailsNotFoundException;
import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.exceptions.UserNotFoundException;
import com.mth.resume_app.models.PersonalDetails;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.PersonalDetailsDTO;
import com.mth.resume_app.repositories.PersonalDetailsRepository;
import com.mth.resume_app.repositories.UserRepository;
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
