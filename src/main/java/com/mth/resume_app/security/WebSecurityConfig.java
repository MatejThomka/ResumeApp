package com.mth.resume_app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

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
}