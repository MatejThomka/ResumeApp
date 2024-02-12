package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ExperienceException;
import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.exceptions.YearException;
import com.mth.resume_app.models.Experience;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.ExperienceDTO;
import com.mth.resume_app.repositories.ExperienceRepository;
import com.mth.resume_app.security.UserExtraction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final UserExtraction extraction;
    private final ExperienceRepository experienceRepository;

    /**
     * Retrieves and returns a list of experience data transfer objects (DTOs) associated with the specified user.
     * It fetches the user by their username, then retrieves all experience entities associated with that user.
     * If no experiences are found, it throws an ExperienceException with the message "Nothing inside!".
     *
     * @param username The username of the user for whom experience details are being retrieved.
     * @return A list of ExperienceDTOs representing the user's experience details.
     * @throws ResumeAppException If there is an issue with retrieving user information.
     * @throws ExperienceException If no experiences are found for the user.
     */
    @Override
    public List<ExperienceDTO> show(String username) throws ResumeAppException {

        User user = extraction.findUserByUsername(username);

        List<Experience> experienceList = experienceRepository.findAllByUser(user);

        if (experienceList.isEmpty()) throw new ExperienceException("Nothing inside!");

        return experienceList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Creates or updates an experience entry for the specified user based on the provided ExperienceDTO.
     * It first validates the date range using the checkDate method.
     * Then, it retrieves the user by their username and fetches or initializes the experience entity based on the provided ID.
     * If the experience does not have an associated user, it assigns the user to the experience.
     * Finally, it calls the saveExperience method to persist the changes and returns the resulting ExperienceDTO.
     *
     * @param username      The username of the user for whom the experience is being created or updated.
     * @param experienceDTO The ExperienceDTO containing information about the experience to be created or updated.
     * @return The ExperienceDTO representing the created or updated experience.
     * @throws ResumeAppException If there is an issue with retrieving user information or validating the date range.
     */
    @Override
    public ExperienceDTO createOrUpdate(String username,
                                        ExperienceDTO experienceDTO)
            throws ResumeAppException {

        checkDate(experienceDTO.getDateFrom(), experienceDTO.getDateTill());

        User user = extraction.findUserByUsername(username);

        Experience experience = experienceRepository.findByUserAndId(user, experienceDTO.getId()).orElse(new Experience());

        if (experience.getUser() == null) experience.setUser(user);

        return saveExperience(experience, experienceDTO);
    }

    /**
     * Deletes the experience entry with the specified ID associated with the given username.
     * It fetches the user by username and then retrieves the experience entity based on the provided ID.
     * If the experience entry is found, it is deleted from the repository.
     *
     * @param username The username of the user associated with the experience entry.
     * @param id       The ID of the experience entry to be deleted.
     * @throws ResumeAppException If the experience entry is not found.
     */
    @Override
    public void delete(String username, Integer id) throws ResumeAppException{

        User user = extraction.findUserByUsername(username);

        Experience experience = experienceRepository.findByUserAndId(user, id).orElseThrow(() -> new ExperienceException("Experience with this ID " + id + " missing!"));

        experienceRepository.delete(experience);
    }

    /**
     * Converts an Experience entity to its corresponding ExperienceDTO representation.
     * This method takes an Experience entity as input and creates a new ExperienceDTO object
     * with the same ID, title, employer, country, city, dateFrom, dateTill, and description as the input experience.
     * It then returns the newly created ExperienceDTO.
     *
     * @param experience The Experience entity to be converted to ExperienceDTO.
     * @return The ExperienceDTO representation of the input Experience entity.
     */
    private ExperienceDTO convertToDTO(Experience experience) {
        return ExperienceDTO.builder()
                .id(experience.getId())
                .title(experience.getTitle())
                .employer(experience.getEmployer())
                .country(experience.getCountry())
                .city(experience.getCity())
                .dateFrom(experience.getDateFrom())
                .dateTill(experience.getDateTill())
                .description(experience.getDescription())
                .build();
    }

    /**
     * Saves the experience entity based on the provided ExperienceDTO.
     * It updates the title, employer, country, city, dateFrom, dateTill, and description attributes
     * of the existing experience entity or creates a new one based on the information in the ExperienceDTO.
     * After saving the changes to the repository, it creates a new ExperienceDTO representation
     * with the updated or newly created experience attributes and returns it.
     *
     * @param experience     The existing or new experience entity to be updated or created.
     * @param experienceDTO  The ExperienceDTO containing the updated or new experience details.
     * @return The ExperienceDTO representation of the updated or newly created experience entity.
     */
    private ExperienceDTO saveExperience(Experience experience,
                                         ExperienceDTO experienceDTO) {

        experience.setTitle(experienceDTO.getTitle());
        experience.setEmployer(experienceDTO.getEmployer());
        experience.setCountry(experienceDTO.getCountry());
        experience.setCity(experienceDTO.getCity());
        experience.setDateFrom(experienceDTO.getDateFrom());
        experience.setDateTill(experienceDTO.getDateTill());
        experience.setDescription(experienceDTO.getDescription());

        experienceRepository.save(experience);

        return ExperienceDTO.builder()
                .id(experience.getId())
                .title(experience.getTitle())
                .employer(experience.getEmployer())
                .country(experience.getCountry())
                .city(experience.getCity())
                .dateFrom(experience.getDateFrom())
                .dateTill(experience.getDateTill())
                .description(experience.getDescription())
                .build();
    }

    /**
     * Checks if the provided date range is valid.
     * It compares the start date (dateFrom) and end date (dateTill) to ensure that the start date is not later than the end date.
     * Additionally, it verifies that the years in the date range are not higher than the current year.
     * If the provided date range is invalid, it throws a YearException with an appropriate error message.
     *
     * @param dateFrom The start date of the experience.
     * @param dateTill The end date of the experience (optional).
     * @throws YearException If the date range is invalid or contains incorrect years.
     */
    private void checkDate(String dateFrom, String dateTill) throws YearException {

        if (dateTill != null) {
            if (Integer.parseInt(dateFrom.substring(0, 3)) > Integer.parseInt(dateTill.substring(0,3))) throw new YearException("Your date from is higher than date till!");

            if (Integer.parseInt(dateFrom.substring(0,3)) > LocalDate.now().getYear() ||
                    Integer.parseInt(dateTill.substring(0,3)) > LocalDate.now().getYear()) throw new YearException("Wrong year!");
        }

        if (Integer.parseInt(dateFrom.substring(0,3)) > LocalDate.now().getYear()) throw new YearException("Wrong year!");

    }
}
