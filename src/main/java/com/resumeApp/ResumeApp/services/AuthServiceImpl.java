package com.resumeApp.ResumeApp.services;

import com.resumeApp.ResumeApp.exceptions.ResumeAppException;
import com.resumeApp.ResumeApp.exceptions.UserAlreadyExistsException;
import com.resumeApp.ResumeApp.exceptions.UserNotFoundException;
import com.resumeApp.ResumeApp.models.User;
import com.resumeApp.ResumeApp.models.dtos.LoginDTO;
import com.resumeApp.ResumeApp.models.dtos.RegisterDTO;
import com.resumeApp.ResumeApp.models.enums.Roles;
import com.resumeApp.ResumeApp.repositories.UserRepository;
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
