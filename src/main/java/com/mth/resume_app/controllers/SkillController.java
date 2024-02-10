package com.mth.resume_app.controllers;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.SkillDTO;
import com.mth.resume_app.services.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skill")
public class SkillController {

    private final SkillService skillService;

    @GetMapping("/{username}/")
    public ResponseEntity<?> showSkill(@PathVariable String username) {
        List<SkillDTO> skillDTO;

        try {
            skillDTO = skillService.show(username);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(skillDTO, HttpStatus.OK);
    }

    @PutMapping("/{username}/create-update")
    public ResponseEntity<?> createOrUpdate(@PathVariable String username,
                                            @RequestBody SkillDTO skillDTO) {
        SkillDTO skill;

        try {
            skill = skillService.createOrUpdate(username, skillDTO);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(skill, HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}/delete")
    public ResponseEntity<?> delete(@PathVariable String username,
                                    @RequestParam Integer id) {

        try {
            skillService.delete(username, id);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Deleted!", HttpStatus.OK);
    }

}
