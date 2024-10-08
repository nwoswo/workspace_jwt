package com.nwo.spring_security_m2.config.security.service;

import com.nwo.spring_security_m2.config.security.repository.entity.Usuarios;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

  @Value("${security.jwt.expiration-minutes}")
  private long EXPIRATION_MINUTES;

  @Value("${security.jwt.secret-key}")
  private String SECRET_KEY;
  public String generateToken(Usuarios user, Map<String,Object> claims) {

    Date issuedAt = new Date(System.currentTimeMillis());
    Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(expiration) // 10 horas
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
        .signWith(generateKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key generateKey() {
    byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(secretAsBytes);
  }

  public String extractUsername(String jwt) {
   return Jwts.parserBuilder().setSigningKey(generateKey()).build()
        .parseClaimsJws(jwt).getBody().getSubject();
  }
}
