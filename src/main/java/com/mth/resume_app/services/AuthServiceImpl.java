package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.exceptions.UserAlreadyExistsException;
import com.mth.resume_app.exceptions.UserNotFoundException;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.AuthenticationDTO;
import com.mth.resume_app.models.dtos.LoginDTO;
import com.mth.resume_app.models.dtos.RegisterDTO;
import com.mth.resume_app.models.enums.Roles;
import com.mth.resume_app.repositories.UserRepository;
import com.mth.resume_app.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  @Override
  public AuthenticationDTO register(RegisterDTO registerDTO) throws ResumeAppException {

    if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("User already exists!");
    }

    User user = userRepository.save(User.builder()
        .email(registerDTO.getEmail())
        .password(passwordEncoder.encode(registerDTO.getPassword()))
        .role(Roles.USER)
        .build());

    return AuthenticationDTO.builder().token(jwtUtil.createToken(user)).build();
  }

  @Override
  public AuthenticationDTO login(LoginDTO loginDTO) throws ResumeAppException {

    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

    User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException("User not found!"));

    return AuthenticationDTO.builder().token(jwtUtil.createToken(user)).build();
  }

  @Override
  public void logout() {
    jwtUtil.jwtBlackList();
  }

}
