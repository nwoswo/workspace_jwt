package com.nwo.spring_security_m1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

//@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//  @Autowired
//  private TokenUtils tokenUtils;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{

    AuthCredentials authCredentials =  new AuthCredentials();

    try {
      authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    UsernamePasswordAuthenticationToken usernamePat =  new UsernamePasswordAuthenticationToken(
        authCredentials.getEmail(),authCredentials.getPassword(), Collections.emptyList()
    );

    return getAuthenticationManager().authenticate(usernamePat);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) throws IOException, ServletException {

    UserDetailsImpl userDetails =  (UserDetailsImpl)authResult.getPrincipal();
    String token = TokenUtils.createToken(userDetails.getNombre(), userDetails.getUsername());

    response.addHeader("Authorization", "Bearer "+token);
    response.getWriter().flush();

    super.successfulAuthentication(request, response, chain, authResult);
  }
}
