package com.nwo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nwo.dao.UsuarioDao;
import com.nwo.domain.Usuario;


@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService { 

	@Autowired
	UsuarioDao usuarioDao;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if (usuario == null) {
            throw new UsernameNotFoundException(String.format("No appUser found with username '%s'.", username));
        } else {
        	System.out.println("usuario.getRoles() "+usuario.getRoles());
        	return new User(
        			usuario.getUsername(), 
        			usuario.getPassword(), 
        			true, 
        			true, 
        			true, 
        			true, 
        			AuthorityUtils.commaSeparatedStringToAuthorityList(usuario.getRoles())
        		);
        	
          
        }
		
	}
}


