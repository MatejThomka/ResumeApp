package com.mth.resume_app.security;

import com.mth.resume_app.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final CustomUserDetailsService userDetailsService;
  private final JwtAuthorizationFilter jwtAuthorizationFilter;

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                     PasswordEncoder bcrypt)
    throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.
        getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bcrypt);
    return authenticationManagerBuilder.build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/**"))
        .authorizeHttpRequests((request) -> request
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/resume/**").hasAnyRole("USER", "ADMIN", "COMPANY")
            .anyRequest().authenticated()
        );

    httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }
}