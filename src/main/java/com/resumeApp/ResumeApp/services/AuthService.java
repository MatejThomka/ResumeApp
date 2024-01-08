package com.resumeApp.ResumeApp.services;

import com.resumeApp.ResumeApp.exceptions.ResumeAppException;
import com.resumeApp.ResumeApp.models.dtos.LoginDTO;
import com.resumeApp.ResumeApp.models.dtos.RegisterDTO;

public interface AuthService {

  void register(RegisterDTO registerDTO) throws ResumeAppException;
  void login(LoginDTO loginDTO) throws ResumeAppException;
}
