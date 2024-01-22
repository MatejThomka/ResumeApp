package com.mth.resume_app.controllers;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.UserDTO;
import com.mth.resume_app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> credentials() {
        UserDTO userDTO;

        try {
            userDTO = userService.credentials();
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateCredentials(@RequestBody User user) {

        UserDTO userDTO;

        try {
            userDTO = userService.updateCredentials(user);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
