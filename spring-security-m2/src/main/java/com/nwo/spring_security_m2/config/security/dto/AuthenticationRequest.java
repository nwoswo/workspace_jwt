package com.nwo.spring_security_m2.config.security.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

  private String username;
  private String password;
}
