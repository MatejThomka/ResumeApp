package com.mth.resume_app.controllers;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.EducationDTO;
import com.mth.resume_app.models.enums.EducationType;
import com.mth.resume_app.services.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/education")
public class EducationController {

    private final EducationService educationService;
    @PutMapping("/{username}/create-update")
    public ResponseEntity<?> createOrUpdate(@PathVariable String username, @RequestBody EducationDTO educationDTO) {

        EducationType type = educationDTO.getEducationType();

        switch (type) {
            case HIGH_SCHOOL -> {
                try {
                    educationService.cOUHighSchool(username, educationDTO);
                } catch (ResumeAppException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            }
            case UNIVERSITY -> {
                try {
                    educationService.cOUUniversity(username, educationDTO);
                } catch (ResumeAppException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            }
            case COURSE_OR_CERTIFICATE -> {
                try {
                    educationService.cOUCourseOrCertificate(username, educationDTO);
                } catch (ResumeAppException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            }
        }

        return new ResponseEntity<>("Education added successfully! " + type.getValue(), HttpStatus.OK);
    }

    @GetMapping("/{username}/show")
    public ResponseEntity<?> showEducations(@PathVariable String username) {
        List<EducationDTO> educationDTO;

        try {
            educationDTO = educationService.show(username);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(educationDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{username}/delete")
    public ResponseEntity<?> deleteEducation(@PathVariable String username, @RequestParam Integer id) {

        try {
            educationService.delete(username, id);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Delete of education ID: " + id + " successfully!", HttpStatus.OK);
    }
}
