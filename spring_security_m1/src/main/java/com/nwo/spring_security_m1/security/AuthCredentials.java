package com.nwo.spring_security_m1.security;

import lombok.Data;

@Data
public class AuthCredentials {

  private String email;
  private String password;
}
