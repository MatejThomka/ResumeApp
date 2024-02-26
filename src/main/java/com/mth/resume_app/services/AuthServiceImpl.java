package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.AuthException;
import com.mth.resume_app.exceptions.DetailsException;
import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.exceptions.UserException;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.AuthenticationDTO;
import com.mth.resume_app.models.dtos.LoginDTO;
import com.mth.resume_app.models.dtos.RegisterDTO;
import com.mth.resume_app.models.enums.Roles;
import com.mth.resume_app.repositories.UserRepository;
import com.mth.resume_app.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  /**
   * Registers a new user with the provided registration data. It checks if a user with the given
   * email already exists in the repository. If the email is already taken, a UserAlreadyExistsException
   * is thrown. Otherwise, a new user is created, encrypted password is stored, and the user is saved
   * to the repository. Finally, an AuthenticationDTO containing a JWT token for the new user.
   *
   * @param registerDTO The registration data for the new user.
   * @return An AuthenticationDTO containing a JWT token for the newly registered user.
   * @throws UserException If a user with the specified email already exists.
   */
  @Override
  public AuthenticationDTO register(RegisterDTO registerDTO) throws ResumeAppException {

    checkCredentials(registerDTO);

    if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
      throw new UserException("This email is already taken!");
    }

    if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
      throw new DetailsException("Username already exists!");
    }

    User user = userRepository.save(User.builder()
            .email(registerDTO.getEmail())
            .password(passwordEncoder.encode(registerDTO.getPassword()))
            .username(registerDTO.getUsername())
            .role(Roles.USER)
            .build());

    return AuthenticationDTO.builder()
            .token(jwtUtil.createToken(user))
            .username(user.getUsername())
            .build();
  }

  /**
   * Authenticates a user using the provided login credentials. It utilizes the authentication
   * manager to validate the provided email and password. If the authentication is successful,
   * it retrieves the corresponding user from the repository. If the user is not found, a
   * UserNotFoundException is thrown. Otherwise, an AuthenticationDTO containing a JWT token
   * for the authenticated user is returned.
   *
   * @param loginDTO The login credentials containing email and password.
   * @return An AuthenticationDTO containing a JWT token for the authenticated user.
   * @throws UserException If the authenticated user is not found in the repository.
   * @throws ResumeAppException    If the authentication process encounters an exception.
   */
  @Override
  public AuthenticationDTO login(LoginDTO loginDTO) throws ResumeAppException {

    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

    User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserException("User not found!"));

    return AuthenticationDTO.builder()
            .token(jwtUtil.createToken(user))
            .username(user.getUsername())
            .build();
  }

  /**
   * Logs out the currently authenticated user by blacklisting the JSON Web Token (JWT) associated
   * with the current session. It invokes the jwtBlackList method to add the token to the blacklist,
   * preventing further usage for authentication.
   */
  @Override
  public void logout() {
    jwtUtil.jwtBlackList();
  }

  private void checkCredentials(RegisterDTO registerDTO) throws ResumeAppException {
    if (registerDTO.getEmail().isEmpty()) throw new AuthException("Email is missing!");
    if (registerDTO.getPassword().isEmpty()) throw new AuthException("Password is missing!");
    if (registerDTO.getUsername().isEmpty()) throw new AuthException("Username is missing!");
    if (!registerDTO.getEmail().contains("@.")) throw new AuthException("Incorrect email!");
  }
}
