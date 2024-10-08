package com.nwo.api.keyresource.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// public class WebSecurity extends WebSecurityConfigurerAdapter
public class WebSecurity {

  @Bean
  SecurityFilterChain configure(HttpSecurity http) throws Exception {

    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

    http.authorizeHttpRequests()
        .antMatchers(HttpMethod.GET, "/users/status/check")
        // .hasAnyAuthority("SCOPE_profile")
        .hasRole("developer")
        .anyRequest().authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(converter);
    // http.authorizeHttpRequests(authz ->
    // authz
    // .antMatchers(HttpMethod.GET, "/users").hasAuthority("SCOPE_profile")
    // .anyRequest().authenticated())
    // .oauth2ResourceServer(oauth2 -> oauth2.jwt( jwt -> {} ));

    return http.build();
  }
}
