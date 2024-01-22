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
    @Override
    public UserDTO credentials() throws ResumeAppException {
        User user;

        user = userRepository.findByEmail(jwtUtil.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found!"));

        return UserDTO.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }

    @Override
    public UserDTO updateCredentials(User user) throws ResumeAppException {
        User updatedUser;

        updatedUser = userRepository.findByEmail(jwtUtil.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getName() != null) updatedUser.setName(user.getName());
        if (user.getLastname() != null) updatedUser.setLastname(user.getLastname());
        if (user.getAddress() != null) updatedUser.setAddress(user.getAddress());
        if (user.getPhoneNumber() != null) updatedUser.setPhoneNumber(user.getPhoneNumber());

        userRepository.save(updatedUser);

        return UserDTO.builder()
                .name(updatedUser.getName())
                .lastname(updatedUser.getLastname())
                .email(updatedUser.getEmail())
                .address(updatedUser.getAddress())
                .phoneNumber(updatedUser.getPhoneNumber())
                .build();
    }


}
