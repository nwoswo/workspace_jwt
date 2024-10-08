package com.nwo.spring_security_m2.config.security.repository;

import com.nwo.spring_security_m2.config.security.repository.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuarios, Long> {
  Optional<Usuarios> findByUsername(String username);
}
