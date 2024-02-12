package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.InfoDTO;

public interface InfoService {

    InfoDTO show(String username) throws ResumeAppException;
    InfoDTO createOrUpdate(String username, InfoDTO infoDTO) throws ResumeAppException;
    void delete(String username) throws ResumeAppException;
}
