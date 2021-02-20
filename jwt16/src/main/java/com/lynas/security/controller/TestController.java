package com.lynas.security.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')") 
	@GetMapping(value = "/healthcheck", produces = "application/json; charset=utf-8")
	  
	public String getHealthCheck() 
	{
		return "{ \"todoOk\" : true }";
	}


}
