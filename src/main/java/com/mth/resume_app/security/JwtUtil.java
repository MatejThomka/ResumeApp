package com.mth.resume_app.security;

import com.mth.resume_app.exceptions.AuthException;
import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.User;
import com.mth.resume_app.config.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Slf4j
public class JwtUtil {


  private final String secret;
  private final JwtParser jwtParser;
  private final List<String> blackList = new ArrayList<>();
  private volatile boolean isCleaningTokens = false;

  public JwtUtil(AppConfig appConfig) {
    this.secret = appConfig.getSeckey();
    this.jwtParser = Jwts.parser().setSigningKey(secret);
  }

  public String createToken(User user) {
    Claims claims = Jwts.claims().setSubject(user.getEmail());
    claims.put("name", user.getName());
    claims.put("lastname", user.getLastname());
    claims.put("roles", user.getRole());
    Date tokenCreateTime = new Date();
      Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(60 * 60 * 1000));
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(tokenValidity)
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  private Claims parseJwtClaims(String token) {
    return jwtParser
            .parseClaimsJws(token)
            .getBody();
  }

  public Claims resolveClaims(HttpServletRequest request) {
    try {
      String token = resolveToken(request);
      if (token != null) {
        return parseJwtClaims(token);
      }
      return null;
    } catch (ExpiredJwtException e) {
      request.setAttribute("Expired!", e.getMessage());
      throw e;
    } catch (Exception e) {
      request.setAttribute("Invalid!", e.getMessage());
      throw e;
    }
  }

  public String resolveToken(HttpServletRequest request) {

    String PREFIX = "Bearer ";
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken != null && bearerToken.startsWith(PREFIX)) {
      return bearerToken.substring(PREFIX.length());
    }

    return null;
  }

  public boolean validateClaims(Claims claims) {
    try {
      return claims.getExpiration().after(new Date());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  public boolean isBlackListed(String token) throws ResumeAppException {
    if (blackList.contains(token)) {
      throw new AuthException("Unauthorized!");
    }
    return false;
  }

  public String getEmail() {

      HttpServletRequest request = null;

      try {
          request = ((ServletRequestAttributes) RequestContextHolder
                  .currentRequestAttributes())
                  .getRequest();
          Claims claims = resolveClaims(request);
          if (claims != null) {
              return claims.getSubject();
          }
          return null;
      } catch (ExpiredJwtException e) {
          assert request != null;
          request.setAttribute("Expired!", e.getMessage());
          return null;
      } catch (Exception e) {
          assert request != null;
          request.setAttribute("Invalid!", e.getMessage());
          return null;
      }

  }

  public void jwtBlackList() {

    try {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
              .currentRequestAttributes())
              .getRequest();

      String token = resolveToken(request);

      if (!blackList.contains(token)) {
        blackList.add(token);
      }

      if (!isCleaningTokens) {
        CompletableFuture.runAsync(() -> {
          cleanExpiredTokens();
          isCleaningTokens = false;
        });
        isCleaningTokens = true;
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private void cleanExpiredTokens() {

    List<String> tokenToRemove = new ArrayList<>();

    for (String token : blackList) {
      try {
        Claims claims = parseJwtClaims(token);
        if (!validateClaims(claims)) {
          tokenToRemove.add(token);
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    blackList.removeAll(tokenToRemove);
  }
}
