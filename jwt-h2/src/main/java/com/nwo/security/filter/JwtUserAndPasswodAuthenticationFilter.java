package com.nwo.security.filter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nwo.domain.Usuario;
import com.nwo.security.config.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.AuthenticationException;


public class JwtUserAndPasswodAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	private final JwtConfig jwtConfig;

	@Autowired
	public JwtUserAndPasswodAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
	}
	
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		String username = obtainUsername(request);
		String password = obtainPassword(request);
     
     try {
    	 Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
    	 
    	 username = usuario.getUsername();
		 password = usuario.getPassword();
		 
         System.out.println("usuario username: "+usuario.getUsername());
         
    	 
		}  catch (IOException e) {

			e.printStackTrace();
		}
     
     
     
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		
		return this.authenticationManager.authenticate(authRequest);

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


	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
    	SecretKey secretKey = new SecretKeySpec(jwtConfig.getSecretKey().getBytes(), SignatureAlgorithm.HS256.getJcaName());
    	
    	System.out.println("authResult.getAuthorities() : "+authResult.getAuthorities());
    	
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration( java.sql.Date.valueOf(LocalDate.now().plusDays(Long.valueOf(jwtConfig.getTokenExpirationAfterDays()))) )
                .signWith(secretKey)
                .compact();

        response.addHeader(HttpHeaders.AUTHORIZATION,jwtConfig.getTokenPrefix() + token);
        System.out.println("TOKEN:"+token);
        String json = new Gson().toJson(authResult);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    }
    
	
}
