package com.mth.resume_app.controllers;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.InfoDTO;
import com.mth.resume_app.services.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/info")
public class InfoController {

    private final InfoService infoService;

    @GetMapping("/{username}")
    public ResponseEntity<?> showInfo(@PathVariable String username) {
        InfoDTO infoDTO;

        try {
            infoDTO = infoService.show(username);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(infoDTO, HttpStatus.FOUND);
    }

    @PutMapping("/{username}/create-update")
    public ResponseEntity<?> createOrUpdate(@PathVariable String username,
                                            @RequestBody InfoDTO infoDTO) {
        InfoDTO info;

        try {
            info = infoService.createOrUpdate(username, infoDTO);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}/delete")
    public ResponseEntity<?> delete(@PathVariable String username) {
        try {
            infoService.delete(username);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Deleted!", HttpStatus.OK);
    }
}
