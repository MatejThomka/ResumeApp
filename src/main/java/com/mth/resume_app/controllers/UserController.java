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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> credentials(@PathVariable String username) {
        UserDTO userDTO;

        try {
            userDTO = userService.credentials(username);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PatchMapping("/{username}/update-credentials")
    public ResponseEntity<?> updateCredentials(@PathVariable String username ,@RequestBody UserDTO userDTO) {

        UserDTO updatedUserDTO;

        try {
            updatedUserDTO = userService.updateCredentials(username ,userDTO);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    @PatchMapping("/{username}/password-change")
    public ResponseEntity<?> passwordChange(@PathVariable String username, @RequestBody PasswordDTO passwordDTO) {

        try {
            userService.passwordChange(username, passwordDTO);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Password change successfully!", HttpStatus.OK);
    }

    @PatchMapping("/{username}/email-change")
    public ResponseEntity<?> emailChange(@PathVariable String username, @RequestBody EmailDTO emailDTO) {

        try {
            userService.emailChange(username, emailDTO);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Email change successfully! Please login again!", HttpStatus.OK);
    }

    @DeleteMapping("/{username}/delete-account")
    public ResponseEntity<?> deleteAccount(@PathVariable String username, @RequestBody PasswordDTO passwordDTO) {

        try {
            userService.deleteAccount(username, passwordDTO);
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> showAllUser() {
        List<UserDTO> userDTOList;

        try {
            userDTOList = userService.showAllUsers();
        } catch (ResumeAppException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
}
