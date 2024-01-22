package com.mth.resume_app.controllers;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.EmailDTO;
import com.mth.resume_app.models.dtos.PasswordDTO;
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

    @PatchMapping("/update-credentials")
    public ResponseEntity<?> updateCredentials(@RequestBody UserDTO userDTO) {

        UserDTO updatedUserDTO;

        try {
            updatedUserDTO = userService.updateCredentials(userDTO);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    @PatchMapping("/password-change")
    public ResponseEntity<?> passwordChange(@RequestBody PasswordDTO passwordDTO) {

        try {
            userService.passwordChange(passwordDTO);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Password change successfully!", HttpStatus.OK);
    }

    @PatchMapping("/email-change")
    public ResponseEntity<?> emailChange(@RequestBody EmailDTO emailDTO) {

        try {
            userService.emailChange(emailDTO);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Email change successfully! Please login again!", HttpStatus.OK);
    }

    @DeleteMapping("/{email}/delete-account")
    public ResponseEntity<?> deleteAccount(@PathVariable String email, @RequestBody PasswordDTO passwordDTO) {

        try {
            userService.deleteAccount(email, passwordDTO);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }
}
