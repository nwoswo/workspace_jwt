package com.nwo.spring_security_m2.config.security.filter;

import com.nwo.spring_security_m2.config.security.repository.UserRepository;
import com.nwo.spring_security_m2.config.security.repository.entity.Usuarios;
import com.nwo.spring_security_m2.config.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  JwtService jwtService;

  @Autowired
  UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    //1. Obtener el header que contiene el jwt
    String authHeader = request.getHeader("Authorization");

    if(authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request,response);
      return;
    };

    //2. Obtener jwt desde header
    String jwt = authHeader.split(" ")[1];

    //3. Obtener subject/username desde el jwt
    String username = jwtService.extractUsername(jwt);

    //4. Setear un objeto Authentication dentro del SecurityContext
    Usuarios user = userRepository.findByUsername(username).get();
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        username, null,user.getAuthorities()
    );

    SecurityContextHolder.getContext().setAuthentication(authToken);

    //5. Ejecutar el resto de filtros
    filterChain.doFilter(request,response);
  }
}
