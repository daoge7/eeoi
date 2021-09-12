package cn.ccsit.eeoi.portal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice
public class EeoiGlobalExceptionHandler  {
	
	private static Logger logger = LogManager.getLogger(EeoiGlobalExceptionHandler.class);
	
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public String handleShiroException(Exception ex) {
    	if (logger.isTraceEnabled()) {
    		logger.trace(ex.getMessage());
    	}
        return "无操作权限";
    }
    
    @ResponseBody
    @ExceptionHandler(org.apache.shiro.authz.AuthorizationException.class)
    public String AuthorizationException(Exception ex) {
    	if (logger.isTraceEnabled()) {
    		logger.trace(ex.getMessage());
    	}
        return "权限认证失败";
    }
    
}