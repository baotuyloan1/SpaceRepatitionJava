package com.example.security.jwt;

import com.example.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

/**
 * @author BAO 7/12/2023
 */
@Component
public class JwtUtils {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${learnEnglish.app.jwtSecret}")
  private String jwtSecret;

  @Value("${learnEnglish.app.jwtExpirationsMs}")
  private int jwtExpirationMs;

  @Value("${learnEnglish.app.jwtCookieName}")
  private String jwtCookie;

  public ResponseCookie getClearnJwtCookie() {
    return ResponseCookie.from(jwtCookie).path("/api").build();
  }

  public String getJwtFromCookiesOrHeader(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      String bearerToken = request.getHeader("Authorization");
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        return bearerToken.substring(7);
      } else {
        return null;
      }
    }
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public boolean validateJwtToken(String jwt) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(jwt);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  public String getUserNameFromJwtToken(String jwt) {
    return Jwts.parserBuilder()
        .setSigningKey(key())
        .build()
        .parseClaimsJws(jwt)
        .getBody()
        .getSubject();
  }

  public String generateTokenFromUserName(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

  public ResponseCookie generateJwtCookie(UserDetailsImpl userDetails) {
    String jwt = generateTokenFromUserName(userDetails.getUsername());
    return ResponseCookie.from(jwtCookie, jwt)
        .path("/api")
        .maxAge(24 * 60 * 60L)
        .httpOnly(true)
        .build();
  }



  public String generateJwtToken(UserDetailsImpl userDetails) {
    return generateTokenFromUserName(userDetails.getUsername());
  }
}
