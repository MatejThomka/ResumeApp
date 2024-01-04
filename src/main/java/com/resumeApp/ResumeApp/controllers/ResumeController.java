package com.resumeApp.ResumeApp.controllers;

import com.resumeApp.ResumeApp.exceptions.ResumeAppException;
import com.resumeApp.ResumeApp.models.User;
import com.resumeApp.ResumeApp.models.dtos.PersonalDetailsDTO;
import com.resumeApp.ResumeApp.services.PersonalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeController {

  private final PersonalDetailsService personalDetailsService;

  @PostMapping("/{user}/personal_details")
  public ResponseEntity<?> personalDetails(@PathVariable User user) {
    PersonalDetailsDTO personalDetailsDTO;

    try {
      personalDetailsDTO = personalDetailsService.showPersonalDetails(user);
    } catch (ResumeAppException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(personalDetailsDTO, HttpStatus.OK);
  }
}
