package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.AuthenticationDTO;
import com.mth.resume_app.models.dtos.LoginDTO;
import com.mth.resume_app.models.dtos.RegisterDTO;
import javax.naming.AuthenticationException;


public interface AuthService {

  AuthenticationDTO register(RegisterDTO registerDTO) throws ResumeAppException;
  AuthenticationDTO login(LoginDTO loginDTO) throws ResumeAppException;
  void logout() throws AuthenticationException;
}
