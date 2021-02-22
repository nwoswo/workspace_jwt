package com.nwo.demo.security;


import com.nwo.demo.auth.ApplicationUserService;
import com.nwo.demo.security.config.JwtConfig;
import com.nwo.demo.security.filter.JwtTokenVerifier;
import com.nwo.demo.security.filter.JwtUserAndPasswodAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static com.nwo.demo.security.bean.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //PARA PODER USAR EL PREAUHORIZE
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService, JwtConfig jwtConfig, SecretKey secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;

        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(new JwtUserAndPasswodAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
            .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig),JwtUserAndPasswodAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers("/","index", "/css/*","/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
            .anyRequest()
            .authenticated()
            ;


    }
    //2
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(daoauthenticationProvider());

    }

    //1
    @Bean
    public DaoAuthenticationProvider daoauthenticationProvider(){
        //Este es el proovedor
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }



}
