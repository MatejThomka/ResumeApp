package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.SkillDTO;

import java.util.List;

public interface SkillService {

    List<SkillDTO> show(String username) throws ResumeAppException;
    SkillDTO createOrUpdate(String username, SkillDTO skillDTO) throws ResumeAppException;
    void delete(String username, Integer id) throws ResumeAppException;
}
