package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bolsadeideas.springboot.app.auth.SimpleGrantedAuthoritiesMixin;
import com.bolsadeideas.springboot.app.models.entity.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter  extends OncePerRequestFilter { 
//extends BasicAuthenticationFilter {

//	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
//		super(authenticationManager);
//	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		System.out.println("doFilterInternal");
		String header = request.getHeader("Authorization");
		
		if(requiresAuthentication(header)) {
			chain.doFilter(request, response);;
			return;
		}
		
		boolean validoToken; 
		Claims token = null;
		try {
			
			token = Jwts.parserBuilder()
				.setSigningKey("Alguna.Clave.Secreta.123456.@#.123456789".getBytes()).build()
				.parseClaimsJws( header.replace("Bearer ",""))
				.getBody();
			
			validoToken = true;
			
		} catch (JwtException | IllegalArgumentException e) {
			validoToken = false;
			e.printStackTrace();
		}
		
		UsernamePasswordAuthenticationToken authentication = null;
	    
		System.out.println("validoToken="+validoToken);
	    if(validoToken) {
	    	String username = token.getSubject();
	    	Object roles = token.get("authorities");
	    	
	    	Collection<? extends GrantedAuthority> authorities 
	    		= Arrays.asList(new ObjectMapper()
	    				.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritiesMixin.class)
	    				.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class)
	    			);
	    	
	    	authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
	    }
		
	    SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	
	}
	
	protected boolean requiresAuthentication(String header) {
		
		System.out.println("header: "+header);
		if(header == null || !header.startsWith("Bearer ") ) {
			return true;
			
		}
		return false;
	}
	
	

}
