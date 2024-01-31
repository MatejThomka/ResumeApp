package com.mth.resume_app.security;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.exceptions.UserException;
import com.mth.resume_app.models.User;
import com.mth.resume_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserExtraction {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * Finds and retrieves the currently authenticated user based on the email stored in the JSON
     * Web Token (JWT) obtained from the JwtUtil. It uses the email from the JWT to look up the user
     * in the repository. If the user is not found, a UserNotFoundException is thrown.
     *
     * @return The User object representing the currently authenticated user.
     * @throws ResumeAppException If the user is not found based on the JWT email.
     */
    public User findUserByAuthorization() throws ResumeAppException {
        return userRepository.findByEmail(jwtUtil
                .getEmail())
                .orElseThrow(() -> new UserException("User not found!"));
    }

    public User findUserByUsername(String username) throws ResumeAppException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserException("User not found!"));
    }
}
