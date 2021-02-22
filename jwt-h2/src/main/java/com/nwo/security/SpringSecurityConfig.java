package com.nwo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nwo.security.config.JwtConfig;
import com.nwo.security.filter.JWTAuthorizationFilter;
import com.nwo.security.filter.JwtUserAndPasswodAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //PARA PODER USAR EL PREAUHORIZE
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtConfig jwtConfig;
	
    @Bean
    public DaoAuthenticationProvider daoauthenticationProvider(){
        //Este es el proovedor
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
    
    //2
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(daoauthenticationProvider());

    }
    

    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	System.out.println("http "+jwtConfig);
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            //.antMatcher("/**")
            .authorizeRequests()
            //.antMatchers("/api/**","index", "/css/*","/js/*").permitAll()
            .antMatchers("/login*").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JwtUserAndPasswodAuthenticationFilter(authenticationManager(),jwtConfig ))
            .addFilterAfter(new JWTAuthorizationFilter(jwtConfig),JwtUserAndPasswodAuthenticationFilter.class);

//        http.addFilterBefore(new JWTAuthorizationFilter(),ExceptionTranslationFilter.class);
    }
    

    
	
	
}
