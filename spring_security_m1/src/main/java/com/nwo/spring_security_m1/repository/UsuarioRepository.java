package com.nwo.spring_security_m1.repository;

import com.nwo.spring_security_m1.repository.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

  Optional<Usuario> findOneByEmail(String email);
}
