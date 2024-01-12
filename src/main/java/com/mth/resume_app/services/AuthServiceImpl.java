package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.exceptions.UserAlreadyExistsException;
import com.mth.resume_app.exceptions.UserNotFoundException;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.LoginDTO;
import com.mth.resume_app.models.dtos.RegisterDTO;
import com.mth.resume_app.models.enums.Roles;
import com.mth.resume_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  @Override
  public void register(RegisterDTO registerDTO) throws ResumeAppException {

    if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("User already exists!");
    }

    userRepository.save(User.builder()
        .email(registerDTO.getEmail())
        .password(registerDTO.getPassword())
        .role(Roles.USER)
        .build());
  }

  @Override
  public void login(LoginDTO loginDTO) throws ResumeAppException {

    userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found!"));


  }


}
