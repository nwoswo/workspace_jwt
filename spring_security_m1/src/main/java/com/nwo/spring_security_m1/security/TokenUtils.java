package com.nwo.spring_security_m1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TokenUtils {

  @Value("${security.jwt.secret-key}")
  private String token_secret;


  @Value("${security.jwt.expiration-minutes}")
  private Long accessTokenValiditySeconds;

  public static Long ACCESS_TOKEN_VALIDITY_SECONDS;
  public static String ACCES_TOKEN_SECRET;

  @PostConstruct
  public void init() {
    ACCESS_TOKEN_VALIDITY_SECONDS = accessTokenValiditySeconds;
    ACCES_TOKEN_SECRET = token_secret;
  }



  public static String createToken(String nombre, String email){
    Long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 60 * 1000;
    Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

    Map<String, Object> extra = new HashMap<>();
    extra.put("nombre", nombre);

    return Jwts.builder()
        .setSubject(email)
        .setExpiration(expirationDate)
        .addClaims(extra)
        .signWith(Keys.hmacShaKeyFor(ACCES_TOKEN_SECRET.getBytes()))
        .compact();

  }

  public UsernamePasswordAuthenticationToken getAuthentication(String token){
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(ACCES_TOKEN_SECRET.getBytes())
        .build()
        .parseClaimsJws(token)
        .getBody();

    String email = claims.getSubject();

    return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
  }

}
