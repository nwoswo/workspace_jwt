package pe.gob.mef.web.config;


import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liger on 11/10/2016.
 */
@ControllerAdvice
public class HandleException {
	
	 
	 
	  @ExceptionHandler(UsernameNotFoundException.class)
	    public ResponseEntity<?> handleCustomException(UsernameNotFoundException ex) {
	        Map<String, Object> metadata = new HashMap<String, Object>();
	        metadata.put("status", 401);
	        metadata.put("message","Datos de Acceso Incorrectos");
	        return new ResponseEntity<Object>(metadata, HttpStatus.UNAUTHORIZED);
	    }
	  
	  
	
	  @ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity<?> handleCustomException(AccessDeniedException ex) {
	        Map<String, Object> metadata = new HashMap<String, Object>();
	        metadata.put("status", 401);
	        metadata.put("message","Unauthorized");
	        return new ResponseEntity<Object>(metadata, HttpStatus.UNAUTHORIZED);
	    }
	  
	  
  

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<?> handleCustomException(AuthenticationCredentialsNotFoundException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 403);
        metadata.put("message","No Autorizado");
        return new ResponseEntity<Object>(metadata, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleCustomException(MethodArgumentNotValidException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 400);
        metadata.put("message","Bad Request");
        return new ResponseEntity<Object>(metadata, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleCustomException(EntityExistsException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 409);
        metadata.put("message","Conflict");
        return new ResponseEntity<Object>(metadata, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleCustomException(NotFoundException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 404);
        metadata.put("message","Not Found");
        return new ResponseEntity<Object>(metadata, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleCustomException(EntityNotFoundException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 404);
        metadata.put("message","Not Found Entity");
        return new ResponseEntity<Object>(metadata, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleCustomException(Exception ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 500);
        metadata.put("message",ex.getMessage());
        return new ResponseEntity<Object>(metadata, HttpStatus.INTERNAL_SERVER_ERROR);
    }
     
 
    
}