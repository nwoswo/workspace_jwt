package com.lynas.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.lynas.security.config.JwtAuthenticationFilter;

import javax.servlet.Filter;

/**
 * Created by LynAs on 20-Jan-16
 */
  
@Configuration
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer{
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
              // new AuthenticationTokenFilter()
        		new JwtAuthenticationFilter()
                //new CORSFilter()
        };
    }
}
