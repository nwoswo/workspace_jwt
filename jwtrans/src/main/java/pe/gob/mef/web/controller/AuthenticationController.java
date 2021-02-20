package pe.gob.mef.web.controller;





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

import pe.gob.mef.repository.bean.Usuario;
import pe.gob.mef.repository.dao.UserDetailDaoInterface;
import pe.gob.mef.security.config.TokenUtils;
import pe.gob.mef.security.model.LoginUser;
import pe.gob.mef.web.model.ApiResponse;
import pe.gob.mef.web.model.AuthToken;


import javax.servlet.http.HttpServletRequest;


//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserDetailDaoInterface userDao;
    
    @Autowired
    private TokenUtils jwtTokenUtil;
    
    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> login(@RequestBody LoginUser loginUser) throws Exception  {

    	
    	System.out.println("login=");
    	
    	System.out.println("getUsername="+loginUser.getUsername());
    	System.out.println("getPassword="+loginUser.getPassword());
    	
    	
    	
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
      
        
    	}catch(Exception e) {
    		
    		
    		//throw new UsernameNotFoundException(e.getMessage());
    		  return new ApiResponse<AuthToken>(401, "Datos Incorrectos",null);
    		
    	}
    	
    	  userDetails = this.userDetailsService.loadUserByUsername(loginUser.getUsername());
    	  Usuario usuario =  userDao.findByUsername(loginUser.getUsername());
    	  
    	  
    	  System.out.println("getAreCArea="+loginUser.getAreCArea());
    	  System.out.println("usuario.getAreCArea()="+usuario.getAreCArea());
    	  
    	  if(usuario.getAreCArea()!=loginUser.getAreCArea())
    	  {
    		  
    		  return new ApiResponse<AuthToken>(401, "Datos Incorrectos",null);
    	  }
    	  
    
        
        final String token = jwtTokenUtil.generateToken(userDetails);
        
        return new ApiResponse<AuthToken>(200, "success",new AuthToken(token, usuario));
        
    }
    
    

}
