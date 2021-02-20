package com.lynas.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RestController
@RequestMapping("protected")
public class ProtectedController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> hello() {
    	
    	System.out.println(bCryptPasswordEncoder.encode("123456"));
    	boolean a = bCryptPasswordEncoder.matches("123456", "$2a$10$aS/lF2c/9JWPUjDHfJ/zTed1ihGBgfX/7xnGTOM5/lW59X4FHalSi");
    	boolean b = bCryptPasswordEncoder.matches("123456", "$2a$10$x2e4.lIokotutCX.K9Dd4uGVRsg2MXIZ4jALlQmKte5eO07w7M2Vi");
    	boolean c = bCryptPasswordEncoder.matches("123456", "$2a$10$/78SVNE/zvfEiY/YJtCB9exZxPojlgAc3o2gktFk6RDd3Scnvdr/2C");
    	System.out.println(a);
    	System.out.println(b);
    	System.out.println(c);
        return ResponseEntity.ok("{\"success\":true}");
    }

}
