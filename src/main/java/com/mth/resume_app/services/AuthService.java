package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.LoginDTO;
import com.mth.resume_app.models.dtos.RegisterDTO;


public interface AuthService {

  void register(RegisterDTO registerDTO) throws ResumeAppException;
  void login(LoginDTO loginDTO) throws ResumeAppException;
}
