package com.nwo.spring_security_m2.config.security.service;

import com.nwo.spring_security_m2.config.security.dto.AuthenticationRequest;
import com.nwo.spring_security_m2.config.security.dto.AuthenticationResponse;
import com.nwo.spring_security_m2.config.security.repository.UserRepository;
import com.nwo.spring_security_m2.config.security.repository.entity.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtService jwtService;
  public AuthenticationResponse login(AuthenticationRequest authRequest) {

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        authRequest.getUsername(), authRequest.getPassword()
    );

    authenticationManager.authenticate(authToken);

    Usuarios user = userRepository.findByUsername(authRequest.getUsername()).get();

    String jwt = jwtService.generateToken(user, generateExraClaims(user));

    return new AuthenticationResponse(jwt);

  }

  private Map<String,Object> generateExraClaims(Usuarios user) {

    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("name", user.getName());
    extraClaims.put("role", user.getRole().name());
    extraClaims.put("permissions", user.getAuthorities());
    return extraClaims;

  }


}
