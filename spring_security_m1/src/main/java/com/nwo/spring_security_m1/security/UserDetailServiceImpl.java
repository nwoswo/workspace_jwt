package com.nwo.spring_security_m1.security;

import com.nwo.spring_security_m1.repository.UsuarioRepository;
import com.nwo.spring_security_m1.repository.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findOneByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Email not Found"));

    return new UserDetailsImpl(usuario);
  }
}
