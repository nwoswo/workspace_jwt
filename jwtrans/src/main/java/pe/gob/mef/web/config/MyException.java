package pe.gob.mef.web.config;

import org.springframework.stereotype.Component;

@Component
public class MyException extends RuntimeException {
 
    private static final long serialVersionUID = 1L;
 
  
    private String errMsg;
 
    public MyException() { }
 
    public MyException(String errMsg) {
    
        this.errMsg = errMsg;
    }
 
   
 
    public String getErrMsg() {
        return errMsg;
    }
 
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }   
}