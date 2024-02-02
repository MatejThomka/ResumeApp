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
  public PersonalDetailsDTO showPersonalDetails(String username) throws ResumeAppException {

    User user = extraction.findUserByUsername(username);

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
  public PersonalDetailsDTO createUpdatePersonalDetails(String username, PersonalDetailsDTO personalDetailsDTO) throws ResumeAppException {

    User user = extraction.findUserByUsername(username);

    PersonalDetails personalDetails = detailsRepository.findByUser(user).orElse(new PersonalDetails());

    personalDetails.setUser(user);

    return savePersonalDetailsDTO(personalDetailsDTO, personalDetails);
  }

  /**
   * Removes personal details associated with the specified user.
   * It attempts to find personal details for the provided user in the repository.
   * If details are found, they are deleted from the repository.
   * If no details are found, a DetailsException is thrown.
   *
   * @param user The User for whom personal details need to be removed.
   * @throws ResumeAppException If an error occurs during the removal of personal details.
   *                           Specifically, a DetailsException is thrown if details are not found.
   */
  @Override
  public void removePersonalDetails(User user) throws ResumeAppException {

    PersonalDetails personalDetails = detailsRepository.findByUser(user).orElseThrow(() -> new DetailsException("Details not found!"));

    detailsRepository.delete(personalDetails);
  }

  /**
   * Updates and saves personal details based on the provided PersonalDetailsDTO.
   * It takes a PersonalDetailsDTO and an existing PersonalDetails entity, updates its attributes
   * such as 'dateOfBirth,' 'placeOfBirth,' 'gender,' 'drivingLicence,' and 'drivingGroups,'
   * and then saves the changes to the repository. The method returns a PersonalDetailsDTO
   * representation of the updated details.
   *
   * @param detailsDTO The PersonalDetailsDTO containing the updated personal details.
   * @param details    The existing PersonalDetails entity to be updated.
   * @return A PersonalDetailsDTO representing the updated personal details.
   */
  private PersonalDetailsDTO savePersonalDetailsDTO(PersonalDetailsDTO detailsDTO,
                                                    PersonalDetails details) {
    details.setDateOfBirth(detailsDTO.getDateOfBirth());
    details.setPlaceOfBirth(detailsDTO.getPlaceOfBirth());
    details.setGender(detailsDTO.getGender());

    if (detailsDTO.isDrivingLicence()) {
      details.setDrivingLicence(true);
      details.setDrivingGroups(detailsDTO.getDrivingGroups());
    } else {
      details.setDrivingLicence(false);
      details.setDrivingGroups(null);
    }

    detailsRepository.save(details);

    return buildByDrivingLicence(details);
  }

  /**
   * Builds and returns a PersonalDetailsDTO representation based on the provided PersonalDetails entity.
   * The method checks whether the provided entity has a driving license and constructs a PersonalDetailsDTO
   * accordingly, including attributes such as 'dateOfBirth,' 'placeOfBirth,' 'drivingLicence,' 'gender,'
   * and 'drivingGroups' if applicable.
   *
   * @param details The PersonalDetails entity to be converted to a PersonalDetailsDTO.
   * @return A PersonalDetailsDTO representation based on the provided entity's driving license status.
   */
  private PersonalDetailsDTO buildByDrivingLicence(PersonalDetails details) {

    if (details.isDrivingLicence()) {
      return PersonalDetailsDTO.builder()
              .dateOfBirth(details.getDateOfBirth())
              .placeOfBirth(details.getPlaceOfBirth())
              .drivingLicence(details.isDrivingLicence())
              .gender(details.getGender())
              .drivingGroups(details.getDrivingGroups())
              .build();
    } else {
      return PersonalDetailsDTO.builder()
              .dateOfBirth(details.getDateOfBirth())
              .placeOfBirth(details.getPlaceOfBirth())
              .drivingLicence(details.isDrivingLicence())
              .gender(details.getGender())
              .build();
    }
  }
}
