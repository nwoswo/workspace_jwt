package com.nwo.spring_security_m2.config.security.repository.entity;


import com.nwo.spring_security_m2.config.security.util.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter  @Getter
public class Usuarios implements UserDetails {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String password;

  private String name;
  @Enumerated(EnumType.STRING)
  private Role role;

// Implemented methods

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    List<GrantedAuthority> authorities = role.getPermissions().stream()
        .map( permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
        .collect(Collectors.toList());

    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
