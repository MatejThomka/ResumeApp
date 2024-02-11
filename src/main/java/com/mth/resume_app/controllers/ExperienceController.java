package com.mth.resume_app.controllers;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.ExperienceDTO;
import com.mth.resume_app.services.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping("/{username}")
    public ResponseEntity<?> show(@PathVariable String username) {
        List<ExperienceDTO> experienceDTO;

        try {
            experienceDTO = experienceService.show(username);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(experienceDTO, HttpStatus.OK);
    }

    @PutMapping("/{username}/create-update")
    public ResponseEntity<?> createOrUpdate(@PathVariable String username,
                                            @RequestBody ExperienceDTO experienceDTO) {
        ExperienceDTO experience;

        try {
            experience = experienceService.createOrUpdate(username, experienceDTO);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(experience, HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}/delete")
    public ResponseEntity<?> delete(@PathVariable String username,
                                    @RequestParam Integer id) {
        try {
            experienceService.delete(username, id);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Deleted!", HttpStatus.OK);
    }
}
