package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bolsadeideas.springboot.app.models.entity.Usuario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {		
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		//este metodo va a realizar la validacion con la bd
		
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
	

		
		if(username !=null && password != null) {
			logger.info("Username desde request parameter (form-data): "+username);
			logger.info("Password desde request parameter (form-data): "+password);
		}else {
			Usuario user;
			
			try {
				user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
				
				username = user.getUsername();
				password = user.getPassword();
				
				logger.info("Username desde request parameter (raw): "+username);
				logger.info("Password desde request parameter (raw): "+password);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		username = username.trim();
	
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		
		return this.authenticationManager.authenticate(authRequest);
	}

	
	
	
	
	


	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		SecretKey secretKey = new SecretKeySpec("Alguna.Clave.Secreta.123456.@#.123456789".getBytes(), SignatureAlgorithm.HS256.getJcaName());
		
//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(authResult.getAuthorities()));
        
      
		
        String token = Jwts.builder()
        		.setClaims(claims)
                        .setSubject(authResult.getName())
                        .signWith(secretKey)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis()+3600000))
                        .compact();

        response.addHeader("Authorization", "Bearer "+token);
        
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("token", token);
        body.put("user", authResult.getName());
        body.put("rol", authResult.getAuthorities());
        body.put("mensaje", "Hola usuario, has iniciado session con exito");
        
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		
		response.setStatus(200);
		response.setContentType("application/json");
		
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		 Map<String, Object> body = new HashMap<String, Object>();
		 body.put("mensaje", "Error de autenticacion: username o password incorrecto!");
		 body.put("error", failed.getMessage());
		 
		 response.getWriter().write(new ObjectMapper().writeValueAsString(body));
			
		response.setStatus(401);
		response.setContentType("application/json");
	}

}
