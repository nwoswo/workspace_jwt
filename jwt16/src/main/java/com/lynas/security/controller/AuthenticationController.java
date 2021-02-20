package com.lynas.security.controller;



import com.lynas.AppConstant;
import com.lynas.model.AppUser;
import com.lynas.security.config.TokenUtils;
import com.lynas.security.model.ApiResponse;
import com.lynas.security.model.AuthToken;
import com.lynas.security.model.AuthenticationRequest;
import com.lynas.security.model.AuthenticationResponse;
import com.lynas.security.model.LoginUser;
import com.lynas.security.model.SpringSecurityUser;
import com.lynas.service.AppUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private AppUserService userService;
    
    @Autowired
    private TokenUtils jwtTokenUtil;
    
    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws Exception  {

    	
    	
    	System.out.println("register---------");
    	 UserDetails userDetails=null;
    	/*
    	if(!new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()).isAuthenticated())
    	throw new UsernameNotFoundException("excepcion");
*/
    	try {
    		
    	//valida el password y el user, sin esta linea todo pasa asi sea con password incorrecto  
    	//Aqui se hace el Login 
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        
        
        //parece que se usa solo para obtener los datos para generar el token 
        userDetails = this.userDetailsService.loadUserByUsername(loginUser.getUsername());
        
    	}catch(Exception e) {
    		throw new UsernameNotFoundException("excepcion");
    	}
        System.out.println("userDetails="+userDetails);
        
        //final User user = new User(loginUser.getUsername(), loginUser.getPassword(), null);
        
        
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("token"+token);
        return new ApiResponse<>(200, "success",new AuthToken(token, userDetails.getUsername()));
        
    }
    
    
/*
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest)
            throws AuthenticationException {

        // Perform the authentication
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        ); 
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-authentication so we can generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = this.tokenUtils.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
        String token = request.getHeader(AppConstant.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(token);
        SpringSecurityUser user = (SpringSecurityUser) this.userDetailsService.loadUserByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
            String refreshedToken = this.tokenUtils.refreshToken(token);
            return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }*/

}
