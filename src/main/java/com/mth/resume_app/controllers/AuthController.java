package com.mth.resume_app.controllers;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.AuthenticationDTO;
import com.mth.resume_app.models.dtos.LoginDTO;
import com.mth.resume_app.models.dtos.RegisterDTO;
import com.mth.resume_app.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
    AuthenticationDTO authenticationDTO;

    try {
      authenticationDTO = authService.register(registerDTO);
    } catch (ResumeAppException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    return new ResponseEntity<>(authenticationDTO, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

    AuthenticationDTO authenticationDTO;

    try {
      authenticationDTO = authService.login(loginDTO);
    } catch (ResumeAppException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(authenticationDTO, HttpStatus.OK);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout() {

    try {
      authService.logout();
    } catch (AuthenticationException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

      return new ResponseEntity<>("Logout successfully!", HttpStatus.OK);
  }
}
