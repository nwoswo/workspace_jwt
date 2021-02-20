package pe.gob.mef.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

import static java.util.Collections.singletonMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The RestAccessDeniedHandler is called by the ExceptionTranslationFilter to handle all AccessDeniedExceptions.
 * These exceptions are thrown when the authentication is valid but access is not authorized.
 *
 */
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    
        ObjectMapper objMapper = new ObjectMapper();

        final HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
      
        wrapper.setContentType(APPLICATION_JSON_VALUE);
      
        wrapper.getWriter().flush();
    }
}