package com.mth.resume_app.controllers;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.PersonalDetailsDTO;
import com.mth.resume_app.services.PersonalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/personal-details")
public class PersonalDetailsController {

  private final PersonalDetailsService personalDetailsService;

  @GetMapping("/")
  public ResponseEntity<?> show() {
    PersonalDetailsDTO personalDetailsDTO;

    try {
      personalDetailsDTO = personalDetailsService.showPersonalDetails();
    } catch (ResumeAppException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(personalDetailsDTO, HttpStatus.OK);
  }

  @PutMapping("/create-update")
  public ResponseEntity<?> createUpdate(@RequestBody(required = false) PersonalDetailsDTO personalDetailsDTO) {
    PersonalDetailsDTO createdDetails;

    try {
      createdDetails = personalDetailsService.createUpdatePersonalDetails(personalDetailsDTO);
    } catch (ResumeAppException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(createdDetails, HttpStatus.CREATED);
  }
}