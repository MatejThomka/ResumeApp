package com.mth.resume_app.security;

import com.mth.resume_app.services.CustomUserDetailsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final CustomUserDetailsService userDetailsService;
  private final JwtAuthorizationFilter jwtAuthorizationFilter;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;

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
        .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
            .authorizeHttpRequests((request) -> request
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/user/all-users").hasAnyRole("ADMIN", "COMPANY")
                    .requestMatchers("/api/user/{username}/**").hasAnyRole("USER", "ADMIN", "COMPANY")
                    .requestMatchers("/api/personal-details/{username}/**").hasAnyRole("USER", "ADMIN", "COMPANY")
                    .requestMatchers("/api/education/{username}/**").hasAnyRole("USER", "ADMIN", "COMPANY")
                    .requestMatchers("/api/skill/{username}/**").hasAnyRole("USER", "ADMIN", "COMPANY")
                    .requestMatchers("/api/experience/{username}/**").hasAnyRole("USER", "ADMIN", "COMPANY")
                    .requestMatchers("/api/info/{username}/**").hasAnyRole("USER", "ADMIN", "COMPANY")
                    .anyRequest().authenticated()
        )
            .exceptionHandling(handling -> handling.accessDeniedHandler(customAccessDeniedHandler));

    httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
      public void addCorsMappings(@NonNull CorsRegistry registry) {
          registry.addMapping("/api/**").allowedOrigins("http://localhost:4200");
        }
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}