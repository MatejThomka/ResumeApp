package com.mth.resume_app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    Map<String, Object> errorDetails = new HashMap<>();

    try {
      String accessToken = jwtUtil.resolveToken(request);

      if (accessToken == null) {
        filterChain.doFilter(request, response);
        return;
      }

      if (jwtUtil.isBlackListed(accessToken)){
        return;
      }

      Claims claims = jwtUtil.resolveClaims(request);

      if (claims != null && jwtUtil.validateClaims(claims)) {
        String email = claims.getSubject();

        String role = claims.get("roles", String.class);

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, "", authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      errorDetails.put("message", "Authentication Error");
      errorDetails.put("details", e.getMessage());
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);

      objectMapper.writeValue(response.getWriter(), errorDetails);
    }

    filterChain.doFilter(request, response);
  }
}
