package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.EducationDTO;

import java.util.List;

public interface EducationService {

    EducationDTO cOUHighSchool(String username, EducationDTO educationDTO, Integer id) throws ResumeAppException;
    EducationDTO cOUUniversity(String username, EducationDTO educationDTO, Integer id) throws ResumeAppException;
    EducationDTO cOUCourseOrCertificate(String username, EducationDTO educationDTO, Integer id) throws ResumeAppException;
    List<EducationDTO> show(String username) throws ResumeAppException;
    void delete(String username, Integer id) throws ResumeAppException;
}
