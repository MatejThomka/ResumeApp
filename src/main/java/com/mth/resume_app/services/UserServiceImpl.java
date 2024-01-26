package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.*;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.EmailDTO;
import com.mth.resume_app.models.dtos.PasswordDTO;
import com.mth.resume_app.models.dtos.UserDTO;
import com.mth.resume_app.models.enums.Roles;
import com.mth.resume_app.repositories.UserRepository;
import com.mth.resume_app.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                user.getPassword()))
            throw new PasswordException("Incorrect current password!");


        if (!Objects.equals(passwordDTO.getNewPassword(),
                passwordDTO.getConfirmNewPassword()))
            throw new PasswordException("Passwords don't match!");


        user.setPassword(passwordEncoder
                .encode(passwordDTO.getNewPassword())
        );

        userRepository.save(user);
    }

    /**
     * Changes the email of the currently authenticated user based on the provided EmailDTO. It retrieves
     * the currently authenticated user, checks if the new email matches the confirmation email, and then
     * updates and saves the new email to the repository.
     *
     * @param emailDTO The EmailDTO containing the new email and confirmation email.
     * @throws ResumeAppException If an error occurs during email change, such as mismatched emails.
     */
    @Override
    public void emailChange(EmailDTO emailDTO) throws ResumeAppException {

        User user = findUser();

        if (Objects.equals(user.getEmail(),
                emailDTO.getEmail()))
            throw new EmailException("You are already using this email!");

        if (!Objects.equals(emailDTO.getEmail(),
                emailDTO.getConfirmEmail()))
            throw new EmailException("Emails don't match!");

        user.setEmail(emailDTO.getEmail());

        userRepository.save(user);
    }

    /**
     * Deletes the account of the currently authenticated user based on the provided email and
     * password information. It retrieves the currently authenticated user, checks if the provided
     * email matches the user's email, validates the correctness of the provided password, and then
     * deletes the user account from the repository.
     *
     * @param email       The email of the currently authenticated user for verification.
     * @param passwordDTO The PasswordDTO containing the current password for verification.
     * @throws ResumeAppException If an error occurs during account deletion, such as incorrect email or password.
     */
    @Override
    public void deleteAccount(String email, PasswordDTO passwordDTO) throws ResumeAppException {

        User user = findUser();

        if (!Objects.equals(email,
                user.getEmail()))
            throw new EmailException("Email is incorrect!");

        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(),
                user.getPassword()))
            throw new PasswordException("Password is incorrect!");

        userRepository.delete(user);
    }

    /**
     * Retrieves and returns a list of UserDTOs representing all users in the system. It fetches
     * the list of users from the repository and converts each user to a UserDTO. If there are no
     * users, a UserNotFoundException is thrown. Additionally, only users with the 'ADMIN' role are
     * authorized to access this information; otherwise, an AuthException is thrown.
     *
     * @return A List of UserDTOs representing all users in the system.
     * @throws ResumeAppException If there are no users or if the operation is unauthorized.
     */
    @Override
    public List<UserDTO> showAllUsers() throws ResumeAppException {
        List<User> userList = userRepository.findAll();

        if (userList.isEmpty()) {
           throw new UserNotFoundException("There is not any users!");
        }

        if (findUser().getRole() != Roles.ADMIN) {
            throw new AuthException("Unauthorized!");
        }

        return userList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
                .orElseThrow(() -> new UserNotFoundException("User not found!")
                );
    }

    /**
     * Converts a User entity to a UserDTO for simplified representation. It takes a User object
     * and creates a UserDTO containing selected user attributes such as 'name,' 'lastname,' 'email,'
     * 'phoneNumber,' and 'address.'
     *
     * @param user The User entity to be converted.
     * @return A UserDTO containing selected user attributes.
     */
    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }

}
