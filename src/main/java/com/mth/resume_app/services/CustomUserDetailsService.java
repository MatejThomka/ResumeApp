package com.mth.resume_app.services;

import com.mth.resume_app.models.User;
import com.mth.resume_app.models.enums.Roles;
import com.mth.resume_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * Loads user details for authentication based on the provided email. It retrieves the user
   * from the repository using the email, and constructs a UserDetails object with the user's
   * email, password, and role. If the user is not found, a UsernameNotFoundException is thrown.
   *
   * @param email The email of the user for whom details are to be loaded.
   * @return A UserDetails object representing the user's details for authentication.
   * @throws UsernameNotFoundException If the user with the specified email is not found.
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    Roles role;
    role = user.getRole();
    return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .roles(role.getValue())
            .build();
  }
}
