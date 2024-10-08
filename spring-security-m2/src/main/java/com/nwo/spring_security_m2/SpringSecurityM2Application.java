package com.nwo.spring_security_m2;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.BeanProperty;
import io.jsonwebtoken.security.Keys;
@SpringBootApplication
public class SpringSecurityM2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityM2Application.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner createPasswordsCommand(){
		return args -> {
			System.out.println(passwordEncoder.encode("123456"));
			System.out.println(Keys.secretKeyFor(SignatureAlgorithm.HS256));

		};

	}

}
