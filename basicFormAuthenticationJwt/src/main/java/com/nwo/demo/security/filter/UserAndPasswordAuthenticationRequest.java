package com.nwo.demo.security.filter;

public class UserAndPasswordAuthenticationRequest {

    private String username;
    private String password;

    public UserAndPasswordAuthenticationRequest() {  }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
