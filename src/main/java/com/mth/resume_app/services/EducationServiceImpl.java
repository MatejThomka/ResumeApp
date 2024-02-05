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
    @Override
    public void cOUHighSchool(String username, EducationDTO educationDTO) throws ResumeAppException {
        isYearValid(educationDTO);

        User user = extraction.findUserByUsername(username);

        HighSchool highSchool = (HighSchool) educationRepository.findByUserAndName(user, educationDTO.getName()).orElse(new HighSchool());

        highSchool.setUser(user);
        highSchool.setSchoolLeavingExam(educationDTO.isSchoolLeavingExam());

        saveEducation(highSchool, educationDTO);
    }

    @Override
    public void cOUUniversity(String username, EducationDTO educationDTO) throws ResumeAppException {
        isYearValid(educationDTO);

        User user = extraction.findUserByUsername(username);

        University university = (University) educationRepository.findByUserAndName(user, educationDTO.getName()).orElse(new University());

        university.setUser(user);
        university.setFaculty(educationDTO.getFaculty());

        saveEducation(university, educationDTO);
    }

    @Override
    public void cOUCourseOrCertificate(String username, EducationDTO educationDTO) throws ResumeAppException {
        isYearValid(educationDTO);
        educationDTO.setName(educationDTO.getNameOfInstitution());
        User user = extraction.findUserByUsername(username);

        CourseOrCertificate courseOrCertificate = (CourseOrCertificate) educationRepository.findByUserAndName(user, educationDTO.getName()).orElse(new CourseOrCertificate());

        courseOrCertificate.setUser(user);
        courseOrCertificate.setNameOfInstitution(educationDTO.getNameOfInstitution());

        saveEducation(courseOrCertificate, educationDTO);
    }

    @Override
    public List<EducationDTO> show(String username) throws ResumeAppException {

        User user = extraction.findUserByUsername(username);
        List<Education> educationList = educationRepository.findAllByUser(user);

        return educationList.stream()
                .map(Education::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String username, Integer id) throws ResumeAppException {

        User user = extraction.findUserByUsername(username);

        Education education = educationRepository.findByUserAndId(user, id).orElseThrow(() -> new EducationException("Education not found!"));

        educationRepository.delete(education);
    }

    private void saveEducation(Education education, EducationDTO educationDTO) {

        education.setName(educationDTO.getName());
        education.setFieldOfStudy(educationDTO.getFieldOfStudy());

        if (educationDTO.isSchoolLeavingExam()) {
            education.setStudying(false);
            education.setYearTill(educationDTO.getYearTill());
        }

        education.setCity(educationDTO.getCity());

        if (educationDTO.isStudying()) {
            education.setYearTill(null);
        } else {
            education.setYearTill(educationDTO.getYearTill());
        }

        education.setYearFrom(educationDTO.getYearFrom());
        education.setDescription(educationDTO.getDescription());

        educationRepository.save(education);
    }

    private void isYearValid(EducationDTO educationDTO) throws ResumeAppException {
        if (educationDTO.getYearFrom() < 1950 || educationDTO.getYearTill() < 1950) {
            throw new YearException("Year is too low!");
        }

        if (educationDTO.getYearFrom() > LocalDate.now().getYear() || educationDTO.getYearTill() > LocalDate.now().getYear()) {
            throw new YearException("Year is too high!");
        }

        if (educationDTO.getYearTill() < educationDTO.getYearFrom()) {
            throw new YearException("Wrong year of start and end of your education!");
        }
    }
}
