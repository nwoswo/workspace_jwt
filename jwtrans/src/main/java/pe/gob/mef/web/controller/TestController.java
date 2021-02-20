package pe.gob.mef.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Qualifier;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


import pe.gob.mef.repository.bean.Usuario;
import pe.gob.mef.repository.dao.UserDetailDaoInterface;
import pe.gob.mef.web.config.MyException;
import pe.gob.mef.web.model.ApiResponse;
import pe.gob.mef.web.model.AuthToken;



@RestController
public class TestController  {
	//ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_MANAGER
	

	@Autowired
	private UserDetailDaoInterface userdao;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	@GetMapping(value = "/healthcheck", produces = "application/json; charset=utf-8")
	public String getHealthCheck() 
			 {
		
		return "{ \"todoOk\" : true  }";
	}
	
	@GetMapping(value = "/sysadmin", produces = "application/json; charset=utf-8")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public String sysadmin() 
			throws Exception {

		return "{ \"todoOk\" : sysadmin  }";
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
	@GetMapping(value = "/admin", produces = "application/json; charset=utf-8")
	public String admin() 
			throws Exception {

		return "{ \"todoOk\" : manager  }";
	}
	
	
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
	@GetMapping(value = "/plataforma", produces = "application/json; charset=utf-8")
	public String plataforma() 
			throws Exception {

		return "{ \"todoOk\" : employee  }";
	}
	
	

	
	
	/*
	{
		
		"usuCUsuario":"2",
		"usuDUsuario":"ADMIN",
		"usuDPassword":"OVELISCO",
		"usuNUbigeo":"140133",
		"areCArea":"1",
		"usuDPermisos":"ROLE_ADMIN"
	}*/
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ApiResponse<AuthToken> createUser(@RequestBody Usuario appUser)   throws Exception {
		if (userdao.findByUsername(appUser.getUsuDUsuario()) != null) {
			throw new Exception("Usuario ya existe");
		}
		
		
		
		
		
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE_MANAGER");

		appUser.setUsuDPassword(bCryptPasswordEncoder().encode(appUser.getUsuDPassword()));
		
		userdao.guardar(appUser);
		
		return new ApiResponse<AuthToken>(200,"Registrado con Exito",appUser);
	}
	


}
