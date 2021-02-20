package pe.gob.mef.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpMethod.*;
/**
 * Created by LynAs on 20-Jan-16
 */
@EnableWebMvc
@ComponentScan("pe.gob.mef")
public class WebConfig
extends WebMvcConfigurerAdapter 
{

	@Autowired
    Environment env;
	
	
	 @Override
	    public void addCorsMappings(CorsRegistry corsRegistry) {
	        corsRegistry.addMapping( "/**" )
			.allowedMethods("POST", "GET", "PUT", "DELETE", "HEAD")
			 .allowedHeaders("Content-Type", "Date", "Total-Count", "loginInfo","Authorization")
             .exposedHeaders("Content-Type", "Date", "Total-Count", "loginInfo", "Authorization")
			.allowedOrigins("*")
			.allowCredentials(false);
	    }
	 
	 
}
