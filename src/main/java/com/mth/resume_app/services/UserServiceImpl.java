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

    private User findUser() throws ResumeAppException {
        return userRepository.findByEmail(jwtUtil
                .getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found")
                );
    }

}
