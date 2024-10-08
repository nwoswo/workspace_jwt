package com.nwo.spring_security_m2.config.security;

import com.nwo.spring_security_m2.config.security.filter.JwtAuthenticationFilter;
import com.nwo.spring_security_m2.config.security.util.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;


@Component
public class HttpSecurityConfig {

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private JwtAuthenticationFilter authenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .sessionManagement( sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(authenticationFilter, AuthorizationFilter.class)
        .authorizeHttpRequests(authConfig -> {
          myFilter(authConfig);
        });
//        .headers().frameOptions().disable();
    return http.build();
  }

  private static void myFilter(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authConfig) {
    authConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
    authConfig.requestMatchers(HttpMethod.GET, "/auth/public-access").permitAll();
//          authConfig.requestMatchers(HttpMethod.GET, "/h2-console/**").permitAll();
    authConfig.requestMatchers(HttpMethod.GET, "/error").permitAll();

    authConfig.requestMatchers(HttpMethod.GET, "/products").hasAuthority(Permission.READ_ALL_PRODUCTS.name());
    authConfig.requestMatchers(HttpMethod.POST, "/products").hasAuthority(Permission.SAVE_ONE_PRODUCT.name());
    authConfig.anyRequest().denyAll();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
  }
}
