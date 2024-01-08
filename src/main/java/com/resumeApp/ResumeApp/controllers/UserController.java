package com.resumeApp.ResumeApp.controllers;

import com.resumeApp.ResumeApp.exceptions.ResumeAppException;
import com.resumeApp.ResumeApp.models.dtos.LoginDTO;
import com.resumeApp.ResumeApp.models.dtos.RegisterDTO;
import com.resumeApp.ResumeApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {

    try {
      authService.register(registerDTO);
    } catch (ResumeAppException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    return new ResponseEntity<>("Registration successfully!", HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

    try {
      authService.login(loginDTO);
    } catch (ResumeAppException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>("Login successfully!", HttpStatus.OK);
  }
}
