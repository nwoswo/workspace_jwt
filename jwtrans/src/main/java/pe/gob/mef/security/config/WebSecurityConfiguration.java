package pe.gob.mef.security.config;



import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpMethod.*;
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableTransactionManagement
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    
    
    @Autowired    
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        /*
         Configures the AuthenticationManagerBuilder to use the specified DetailsService.
         The password is also specified as being encrypted in database.
         */
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
    
    
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	
        http
        .csrf().disable()
       // .cors().and()
        
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/healthcheck").permitAll()
                .anyRequest().authenticated()
                .and()
                
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                
                
                
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http
                .addFilterBefore(authenticationTokenFilterBean(), ExceptionTranslationFilter.class);
              //  .addFilterAfter(corsFilter(), ExceptionTranslationFilter.class);
      
    }
    
    
     /*
    CORS requests are managed only if headers Origin and Access-Control-Request-Method are available on OPTIONS requests
    (this filter is simply ignored in other cases).
    This filter can be used as a replacement for the @Cors annotation.
   */
    
  
        
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       /*
        
        configuration.setAllowedOrigins(Arrays.asList("/*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        source.registerCorsConfiguration("/**", configuration);
        */
        
        /*
        
        configuration.setAllowedOrigins(Arrays.asList("/*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        
        source.registerCorsConfiguration("/**", configuration);
        
        */
        /*
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers","Access-Control-Allow-Origin","Access-Control-Request-Method", "Access-Control-Request-Headers","Origin","Cache-Control", "Content-Type", "Authorization"));
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT"));
        
        source.registerCorsConfiguration("/**", configuration);
        */
        
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        
        source.registerCorsConfiguration("/**", configuration);
        
        
        return source;
    }
    
   
    
    /*
    private CorsFilter corsFilter() {
     
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader(ORIGIN);
        config.addAllowedHeader(CONTENT_TYPE);
        config.addAllowedHeader(ACCEPT);
        config.addAllowedHeader(AUTHORIZATION);
        config.addAllowedMethod(GET);
        config.addAllowedMethod(PUT);
        config.addAllowedMethod(POST);
        config.addAllowedMethod(OPTIONS);
        config.addAllowedMethod(DELETE);
        config.addAllowedMethod(PATCH);
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    */
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
