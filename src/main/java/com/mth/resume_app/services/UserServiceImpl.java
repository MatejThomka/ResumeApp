package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.PasswordException;
import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.exceptions.UserNotFoundException;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.PasswordDTO;
import com.mth.resume_app.models.dtos.UserDTO;
import com.mth.resume_app.repositories.UserRepository;
import com.mth.resume_app.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves and returns user credentials, such as name, lastname, email, phone number, and address,
     * for the currently authenticated user. It calls the findUser method to obtain the user details.
     *
     * @return A UserDTO containing the credentials of the authenticated user.
     * @throws ResumeAppException If an error occurs while retrieving user credentials.
     */
    @Override
    public UserDTO credentials() throws ResumeAppException {

        User user = findUser();

        return UserDTO.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }

    /**
     * Updates the credentials of the currently authenticated user based on the provided UserDTO.
     * It retrieves the currently authenticated user, updates the user's properties if new values
     * are provided in the UserDTO, and then saves the updated user to the repository. Finally, it
     * returns a UserDTO containing the updated credentials of the user.
     *
     * @param userDTO The UserDTO containing updated user credentials.
     * @return A UserDTO with the updated credentials of the authenticated user.
     * @throws ResumeAppException If an error occurs while updating user credentials.
     */
    @Override
    public UserDTO updateCredentials(UserDTO userDTO) throws ResumeAppException {

        User updatedUser = findUser();

        if (userDTO.getName() != null) updatedUser.setName(userDTO.getName());
        if (userDTO.getLastname() != null) updatedUser.setLastname(userDTO.getLastname());
        if (userDTO.getAddress() != null) updatedUser.setAddress(userDTO.getAddress());
        if (userDTO.getPhoneNumber() != null) updatedUser.setPhoneNumber(userDTO.getPhoneNumber());

        userRepository.save(updatedUser);

        return UserDTO.builder()
                .name(updatedUser.getName())
                .lastname(updatedUser.getLastname())
                .email(updatedUser.getEmail())
                .address(updatedUser.getAddress())
                .phoneNumber(updatedUser.getPhoneNumber())
                .build();
    }

    /**
     * Changes the password of the currently authenticated user based on the provided PasswordDTO.
     * It retrieves the currently authenticated user, validates the correctness of the current password,
     * checks if the new password matches the confirmation password, and then updates and saves the
     * new password to the repository.
     *
     * @param passwordDTO The PasswordDTO containing current and new password information.
     * @throws ResumeAppException If an error occurs during password change, such as incorrect current password or mismatched new password.
     */
    @Override
    public void passwordChange(PasswordDTO passwordDTO) throws ResumeAppException {

        User user = findUser();

        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(),
                user.getPassword())) {
            throw new PasswordException("Incorrect current password!");
        }

        if (!Objects.equals(passwordDTO.getPassword(),
                passwordDTO.getConfirmPassword())) {
            throw new PasswordException("Password didn't match!");
        }

        user.setPassword(passwordEncoder
                .encode(passwordDTO.getPassword())
        );

        userRepository.save(user);
    }

    /**
     * Finds and retrieves the currently authenticated user based on the email stored in the JSON
     * Web Token (JWT) obtained from the JwtUtil. It uses the email from the JWT to look up the user
     * in the repository. If the user is not found, a UserNotFoundException is thrown.
     *
     * @return The User object representing the currently authenticated user.
     * @throws ResumeAppException If the user is not found based on the JWT email.
     */
    private User findUser() throws ResumeAppException {
        return userRepository.findByEmail(jwtUtil
                .getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found")
                );
    }

}
