package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.DetailsException;
import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.PersonalDetails;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.PersonalDetailsDTO;
import com.mth.resume_app.repositories.PersonalDetailsRepository;
import com.mth.resume_app.security.UserExtraction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalDetailsServiceImpl implements PersonalDetailsService {

  private final PersonalDetailsRepository detailsRepository;
  private final UserExtraction extraction;

  /**
   * Retrieves and returns personal details for the user with the specified email. It checks for
   * the existence of the user and their associated personal details. If the user or details are
   * not found, respective exceptions are thrown. The method constructs a PersonalDetailsDTO based
   * on the presence or absence of a driver's license in the user's personal details and returns it.
   *
   * @return A PersonalDetailsDTO containing the user's personal information.
   * @throws ResumeAppException If the user or personal details are not found.
   */
  @Override
  public PersonalDetailsDTO showPersonalDetails() throws ResumeAppException {

    User user = extraction.findUserByAuthorization();
    PersonalDetails personalDetails = detailsRepository
            .findByUser(user)
            .orElseThrow(() -> new DetailsException("Details not found!"));

      return buildByDrivingLicence(personalDetails);
  }

  /**
   * Updates or creates and saves personal details for the user with the specified email. It first checks
   * for the existence of the user, and if found, updates or creates a new PersonalDetails entity based on
   * the provided data. The method then saves the personal details to the repository.
   *
   * @throws ResumeAppException If the user is not found or an error occurs during personal details creation.
   */
  @Override
  public PersonalDetailsDTO createUpdatePersonalDetails(PersonalDetailsDTO personalDetailsDTO) throws ResumeAppException {

    User user = extraction.findUserByAuthorization();

    PersonalDetails personalDetails = detailsRepository.findByUser(user).orElse(new PersonalDetails());

    personalDetails.setUser(user);

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

  /**
   * Creates and saves personal details for the user with the specified email. It first checks
   * for the existence of the user, and if found, creates a new PersonalDetails entity based on
   * the provided data. The method then saves the new personal details to the repository.
   *
   * @param email           The email of the user for whom personal details are to be created.
   * @param personalDetails The PersonalDetails object containing the user's personal information.
   * @throws ResumeAppException If the user is not found or an error occurs during personal details creation.
   */
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
