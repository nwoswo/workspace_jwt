package com.nwo.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.nwo.domain.Usuario;
import com.nwo.services.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	
	private final PasswordEncoder utilsecurity;
	
	@Autowired
	public UsuarioController(PasswordEncoder utilsecurity) {
		System.out.println("encoded password -> "+ utilsecurity.encode("123456"));
		this.utilsecurity = utilsecurity;
	}


	@Autowired
	private UsuarioService usuarioService;

	 @Secured("ROLE_USER")
	 @GetMapping("/listar0")


	 public List<Usuario> getList( HttpServletRequest request) {
		 System.out.println("request.isUserInRole(\"ROLE_ADMIN\")"+request.isUserInRole("_ADMIN"));
		 System.out.println("request.isUserInRole(\"ROLE_USER\")"+request.isUserInRole("USER"));
	  List<Usuario> usuariosList = usuarioService.getList();
	  return usuariosList;	
	  
	 }
	 
	 @PreAuthorize("hasAuthority('[{authority=ROLE_ADMIN}]')")
	 @GetMapping("/listar")


	 public List<Usuario> getLista() {
	  List<Usuario> usuariosList = usuarioService.getList();
	  return usuariosList;	
	  
	 }
	 
	 
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @PutMapping("/usuario/{id}")
	 
	 public List<Usuario> updateUser(@PathVariable("id") long id, @RequestBody Usuario usuario) {
	  List<Usuario> usuariosList = usuarioService.getList();
	  return usuariosList;	
	  
	}
	
	
}
