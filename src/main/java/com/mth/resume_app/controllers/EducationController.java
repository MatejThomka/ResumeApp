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
@CrossOrigin(origins = "http://localhost:4200/")
public class EducationController {

    private final EducationService educationService;
    @PutMapping("/{username}/create-update")
    public ResponseEntity<?> createOrUpdate(@PathVariable String username, @RequestBody EducationDTO educationDTO, @RequestParam (required = false) Integer id) {

        EducationType type = educationDTO.getEducationType();
        EducationDTO education;

        switch (type) {
            case HIGH_SCHOOL -> {
                try {
                    education = educationService.cOUHighSchool(username, educationDTO, id);
                } catch (ResumeAppException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            }
            case UNIVERSITY -> {
                try {
                    education = educationService.cOUUniversity(username, educationDTO, id);
                } catch (ResumeAppException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            }
            case COURSE_OR_CERTIFICATE -> {
                try {
                    education = educationService.cOUCourseOrCertificate(username, educationDTO, id);
                } catch (ResumeAppException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }

        return new ResponseEntity<>(education, HttpStatus.OK);
    }

    @GetMapping("/{username}")
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
        }

        return new ResponseEntity<>("Delete of education ID: " + id + " successfully!", HttpStatus.OK);
    }
}
