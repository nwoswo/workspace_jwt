package com.nwo.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwo.security.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Strings;


public class JWTAuthorizationFilter  extends OncePerRequestFilter {
	
	
	private final JwtConfig jwtConfig;
	
	public JWTAuthorizationFilter(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}


	protected boolean requiresAuthentication(String header) {
		
		
		if(header == null || !header.startsWith(jwtConfig.getTokenPrefix()) ) {
			return true;
			
		}
		return false;
	}

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
    	
    	
        
        
        try {
	        	String header = req.getHeader("Authorization");
	        	
	        
	        	
	        	if(requiresAuthentication(header)) {
	        		filterChain.doFilter(req, res);
	    			return;
	    		}
	        	
	        	UsernamePasswordAuthenticationToken authentication = null;
	        	String token = header.replace(jwtConfig.getTokenPrefix(), "");
	        	
	        	SecretKey secretKey = new SecretKeySpec(jwtConfig.getSecretKey().getBytes(), SignatureAlgorithm.HS256.getJcaName());
	        	
	        	Claims body = Jwts.parserBuilder()
	                    .setSigningKey(secretKey).build()
	                    .parseClaimsJws( token )
	                    .getBody();
	
	        	System.out.println("body :"+body);
	        	
	            String username  = body.getSubject(); 
	            Object roles = body.get("ROLES");

	            System.out.println("roles :"+AuthorityUtils.commaSeparatedStringToAuthorityList(roles.toString()));
	            
	            Collection<? extends GrantedAuthority> authorities  = AuthorityUtils.commaSeparatedStringToAuthorityList(roles.toString());
	            authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
	            
	            System.out.println("authorities :"+authorities);
	            
	            
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            
	            
	        	filterChain.doFilter(req, res);
	        } catch (RuntimeException e) {
	        	
	        	e.printStackTrace();
	        	
		   	     Map<String, Object> body2 = new HashMap<String, Object>();
		   	     body2.put("error", e.getMessage());
		   		 
		   		 res.getWriter().write(new ObjectMapper().writeValueAsString(body2));
		   			
		   		 res.setStatus(403);
		   		 res.setContentType("application/json");
		            
		   		 return;
	        
	
	        }
    }
}
