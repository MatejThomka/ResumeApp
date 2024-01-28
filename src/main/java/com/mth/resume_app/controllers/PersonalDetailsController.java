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
@RequestMapping("/api/resume")
public class ResumeController {

  private final PersonalDetailsService personalDetailsService;

  @GetMapping("/{email}/show")
  public ResponseEntity<?> show(@PathVariable String email) {
    PersonalDetailsDTO personalDetailsDTO;

    try {
      personalDetailsDTO = personalDetailsService.showPersonalDetails(email);
    } catch (ResumeAppException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(personalDetailsDTO, HttpStatus.OK);
  }

  @PostMapping("/{email}/create")
  public ResponseEntity<?> create(@PathVariable String email,
                                  @RequestBody PersonalDetails personalDetails) {


    try {
      personalDetailsService.createPersonalDetails(email, personalDetails);
    } catch (ResumeAppException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>("Personal details added!", HttpStatus.CREATED);
  }
}
