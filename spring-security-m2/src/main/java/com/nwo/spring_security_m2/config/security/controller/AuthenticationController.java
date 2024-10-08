package com.nwo.spring_security_m2.config.security.controller;

import com.nwo.spring_security_m2.config.security.dto.AuthenticationRequest;
import com.nwo.spring_security_m2.config.security.dto.AuthenticationResponse;
import com.nwo.spring_security_m2.config.security.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> login(
      @RequestBody @Valid AuthenticationRequest authRequest){

    AuthenticationResponse jwtDto = authenticationService.login(authRequest);
    return ResponseEntity.ok(jwtDto);
  }

  @GetMapping("/public-access")
  public String publicAccessEndpoint(){
    return "Este endpoint es publico";
  }
}
