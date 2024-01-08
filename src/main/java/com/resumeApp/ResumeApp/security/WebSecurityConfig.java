package com.resumeApp.ResumeApp.security;

import com.resumeApp.ResumeApp.exceptions.ResumeAppException;
import com.resumeApp.ResumeApp.exceptions.UserNotFoundException;
import com.resumeApp.ResumeApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final UserRepository userRepository;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(csrf -> csrf.ignoringRequestMatchers("/auth/**"))
        .authorizeHttpRequests((request) -> request
            .requestMatchers("auth/**").permitAll()
            .requestMatchers("/api/resume/**").hasRole("USER")
            .anyRequest().authenticated()
        );

    return httpSecurity.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      try {
        return (UserDetails) userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("User not found!"));
      } catch (ResumeAppException e) {
        throw new RuntimeException(e);
      }
    };
  }
}