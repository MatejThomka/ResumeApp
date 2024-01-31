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

  /**
   * Creates a JSON Web Token (JWT) for the specified user, containing relevant user information
   * such as email, name, lastname, and roles. The token is signed using the HMAC-SHA256 algorithm
   * with a secret key. The token includes claims about the user and has a default expiration
   * time of 1 hour. The created token is then returned as a compact, URL-safe string.
   *
   * @param user The user object for whom the token is to be generated.
   * @return A JWT string representing the user's authentication token.
   */
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

  /**
   * Parses the JSON Web Token (JWT) claims from the provided token string using the configured
   * JWT parser. It verifies the token's signature and extracts the claims payload, returning
   * the Claims object containing information about the token.
   *
   * @param token The JWT string to be parsed and verified.
   * @return A Claims object representing the parsed JWT claims.
   */
  private Claims parseJwtClaims(String token) {
    return jwtParser
            .parseClaimsJws(token)
            .getBody();
  }

  /**
   * Resolves and parses the JSON Web Token (JWT) claims from the provided HttpServletRequest.
   * It attempts to extract the token from the request and then parses and verifies the token's
   * signature, returning the Claims object containing information about the token.
   *
   * @param request The HttpServletRequest from which the token is to be resolved.
   * @return A Claims object representing the parsed JWT claims if the token is valid; otherwise, null.
   * @throws ExpiredJwtException If the token is expired, with the exception message stored in the request attribute.
   */
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

  /**
   * Resolves the JWT (JSON Web Token) from the Authorization header of the provided HttpServletRequest.
   * It checks for the presence of the "Bearer " prefix in the Authorization header and, if found,
   * extracts and returns the token excluding the prefix.
   *
   * @param request The HttpServletRequest from which the token is to be resolved.
   * @return The extracted JWT string if it exists in the Authorization header; otherwise, null.
   */
  public String resolveToken(HttpServletRequest request) {

    String PREFIX = "Bearer ";
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken != null && bearerToken.startsWith(PREFIX)) {
      return bearerToken.substring(PREFIX.length());
    }

    return null;
  }

  /**
   * Validates the expiration of JSON Web Token (JWT) claims by checking if the expiration date
   * specified in the provided Claims object is after the current date and time. Returns true if
   * the token is still valid, and false otherwise.
   *
   * @param claims The Claims object containing information about the JWT, including its expiration date.
   * @return True if the JWT is still valid (not expired), false otherwise.
   */
  public boolean validateClaims(Claims claims) {
    try {
      return claims.getExpiration().after(new Date());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  /**
   * Checks if the provided JSON Web Token (JWT) is blacklisted by verifying its presence in a blacklist.
   * If the token is found in the blacklist, an AuthException with the message "Unauthorized!" is thrown.
   *
   * @param token The JWT string to be checked against the blacklist.
   * @return True if the token is blacklisted, triggering an AuthException; otherwise, false.
   * @throws AuthException If the token is found in the blacklist, indicating unauthorized access.
   */
  public boolean isBlackListed(String token) throws ResumeAppException {
    if (blackList.contains(token)) {
      throw new AuthException("Unauthorized!");
    }
    return false;
  }

  /**
   * Retrieves the email address associated with the user from the JSON Web Token (JWT)
   * claims stored in the current HttpServletRequest. It resolves the token, extracts the
   * subject (email) from the claims, and returns it. If the token is expired or invalid,
   * the method sets the corresponding attribute in the request and returns null.
   *
   * @return The email address of the user if the token is valid; otherwise, null.
   */
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

  /**
   * Adds the provided JSON Web Token (JWT) to the blacklist, preventing its further usage for authentication.
   * Additionally, initiates an asynchronous task to clean up expired tokens from the blacklist. If the cleaning
   * task is not already running, it starts the task to ensure periodic maintenance of the token blacklist.
   */
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

  /**
   * Cleans up expired JSON Web Tokens (JWTs) from the token blacklist. It iterates through
   * the tokens in the blacklist, parses and validates the claims for each token. If the token
   * is found to be expired, it is added to a list for removal. Finally, the expired tokens are
   * removed from the blacklist.
   */
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
