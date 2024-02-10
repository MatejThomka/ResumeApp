package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.EducationException;
import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.exceptions.YearException;
import com.mth.resume_app.models.Education;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.EducationDTO;
import com.mth.resume_app.models.education_type.CourseOrCertificate;
import com.mth.resume_app.models.education_type.HighSchool;
import com.mth.resume_app.models.education_type.University;
import com.mth.resume_app.repositories.EducationRepository;
import com.mth.resume_app.security.UserExtraction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final UserExtraction extraction;

    /**
     * Creates or updates High School education details for the specified user.
     * Validates the year in the provided EducationDTO, retrieves the user by username,
     * and fetches or initializes the High School entity. The method updates attributes such as
     * school-leaving exam status in the High School entity and calls the common saveEducation
     * method to persist the changes.
     *
     * @param username     The username of the user for whom education details are being updated.
     * @param educationDTO The EducationDTO containing information about the High School education.
     * @return The EducationDTO representing the created or updated education.
     * @throws ResumeAppException If validation fails or user information is not found.
     */
    @Override
    public EducationDTO cOUHighSchool(String username,
                              EducationDTO educationDTO)
            throws ResumeAppException {
        isYearValid(educationDTO);

        User user = extraction.findUserByUsername(username);

        HighSchool highSchool = (HighSchool) educationRepository
                .findByUserAndId(user, educationDTO.getId())
                .orElse(new HighSchool());

        highSchool.setUser(user);
        highSchool.setSchoolLeavingExam(educationDTO.isSchoolLeavingExam());

        return saveEducation(highSchool, educationDTO);
    }

    /**
     * Creates or updates University education details for the specified user.
     * Validates the year in the provided EducationDTO, retrieves the user by username,
     * and fetches or initializes the University entity. The method updates attributes such as
     * faculty in the University entity and calls the common saveEducation
     * method to persist the changes.
     *
     * @param username     The username of the user for whom education details are being updated.
     * @param educationDTO The EducationDTO containing information about the University education.
     * @return The EducationDTO representing the created or updated education.
     * @throws ResumeAppException If validation fails or user information is not found.
     */
    @Override
    public EducationDTO cOUUniversity(String username,
                              EducationDTO educationDTO)
            throws ResumeAppException {
        isYearValid(educationDTO);

        User user = extraction.findUserByUsername(username);

        University university = (University) educationRepository
                .findByUserAndId(user, educationDTO.getId())
                .orElse(new University());

        university.setUser(user);
        university.setFaculty(educationDTO.getFaculty());

        return saveEducation(university, educationDTO);
    }

    /**
     * Creates or updates CourseOrCertificate education details for the specified user.
     * Validates the year in the provided EducationDTO, retrieves the user by username,
     * and fetches or initializes the CourseOrCertificate entity. The method updates attributes such as
     * nameOfInstitution in the CourseOrCertificate entity and calls the common saveEducation
     * method to persist the changes.
     *
     * @param username     The username of the user for whom education details are being updated.
     * @param educationDTO The EducationDTO containing information about the CourseOrCertificate education.
     * @return The EducationDTO representing the created or updated education.
     * @throws ResumeAppException If validation fails or user information is not found.
     */
    @Override
    public EducationDTO cOUCourseOrCertificate(String username,
                                       EducationDTO educationDTO)
            throws ResumeAppException {
        isYearValid(educationDTO);

        User user = extraction.findUserByUsername(username);

        CourseOrCertificate courseOrCertificate = (CourseOrCertificate) educationRepository
                .findByUserAndId(user, educationDTO.getId())
                .orElse(new CourseOrCertificate());

        courseOrCertificate.setUser(user);
        courseOrCertificate.setNameOfInstitution(educationDTO
                .getNameOfInstitution());

        return saveEducation(courseOrCertificate, educationDTO);
    }

    /**
     * Retrieves and returns a list of EducationDTOs representing education details for the specified user.
     * It fetches the user by username, then retrieves all education entities associated with that user.
     * The method maps each education entity to its corresponding EducationDTO and collects them into a list.
     *
     * @param username The username of the user for whom education details are being retrieved.
     * @return A list of EducationDTOs representing the user's education details.
     * @throws ResumeAppException If user information is not found.
     */
    @Override
    public List<EducationDTO> show(String username) throws ResumeAppException {

        User user = extraction.findUserByUsername(username);

        List<Education> educationList = educationRepository.findAllByUser(user);

        return educationList.stream()
                .map(Education::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deletes the education entry with the specified ID associated with the given username.
     * It fetches the user by username and then retrieves the education entity based on the provided ID.
     * If the education entry is found, it is deleted from the repository.
     *
     * @param username The username of the user associated with the education entry.
     * @param id       The ID of the education entry to be deleted.
     * @throws ResumeAppException If the education entry is not found.
     */
    @Override
    public void delete(String username,
                       Integer id)
            throws ResumeAppException {

        User user = extraction.findUserByUsername(username);

        Education education = educationRepository
                .findByUserAndId(user, id)
                .orElseThrow(() -> new EducationException("Education not found!"));

        educationRepository.delete(education);
    }

    /**
     * Saves the education entity based on the provided EducationDTO.
     * It takes an existing or new education entity and updates its attributes such as 'name,' 'fieldOfStudy,'
     * 'schoolLeavingExam,' 'studying,' 'yearTill,' 'city,' 'yearFrom,' and 'description' based on the
     * corresponding attributes in the EducationDTO. The method then saves the changes to the repository.
     *
     * @param education     The existing or new education entity to be updated or created.
     * @param educationDTO  The EducationDTO containing the updated or new education details.
     * @return EducationDTO representing user education details.
     */
    private EducationDTO saveEducation(Education education,
                               EducationDTO educationDTO) {

        education.setName(educationDTO.getName());
        education.setFieldOfStudy(educationDTO.getFieldOfStudy());
        education.setCity(educationDTO.getCity());
        education.setYearFrom(educationDTO.getYearFrom());
        education.setDescription(educationDTO.getDescription());
        education.setEducationType(educationDTO.getEducationType());

        if (educationDTO.isSchoolLeavingExam()) {
            education.setStudying(false);
            education.setYearTill(educationDTO.getYearTill());
        }

        if (educationDTO.isStudying()) {
            education.setYearTill(null);
        } else {
            education.setYearTill(educationDTO.getYearTill());
        }

        educationRepository.save(education);

        return education.toDTO();
    }

    /**
     * Validates the years in the provided EducationDTO.
     * It checks if the 'yearFrom' and 'yearTill' are within a valid range, ensuring they are not earlier
     * than 1950, not higher than the current year, and that 'yearTill' is not earlier than 'yearFrom.'
     *
     * @param educationDTO The EducationDTO containing the years to be validated.
     * @throws YearException If the years are not within a valid range or 'yearTill' is earlier than 'yearFrom.'
     */
    private void isYearValid(EducationDTO educationDTO) throws ResumeAppException {

        if (educationDTO.getYearFrom() < 1950 || educationDTO.getYearTill() < 1950) throw new YearException("Year is too low!");

        if (educationDTO.getYearFrom() > LocalDate.now().getYear() || educationDTO.getYearTill() > LocalDate.now().getYear()) throw new YearException("Year is too high!");

        if (educationDTO.getYearTill() < educationDTO.getYearFrom()) throw new YearException("Wrong year of start and end of your education!");
    }
}
