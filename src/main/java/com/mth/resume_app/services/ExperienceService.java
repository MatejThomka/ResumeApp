package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.ExperienceDTO;

import java.util.List;

public interface ExperienceService {

    List<ExperienceDTO> show(String username) throws ResumeAppException;
    ExperienceDTO createOrUpdate(String username, ExperienceDTO experienceDTO) throws ResumeAppException;
    void delete(String username, Integer id) throws ResumeAppException;
}
