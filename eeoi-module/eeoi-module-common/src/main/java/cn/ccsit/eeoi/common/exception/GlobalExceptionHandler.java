package cn.ccsit.eeoi.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.common.vo.ResultErrorVo;


@ControllerAdvice
public class GlobalExceptionHandler  {
	
	private static Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
	
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResultErrorVo handleUnauthorizedException(Exception ex) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
    	if (sw!=null) {
    		logger.error(sw.toString());
    	}
        return new ResultErrorVo("无访问权限(Not authorized)");
    }
    
    @ResponseBody
    @ExceptionHandler(org.apache.shiro.authz.AuthorizationException.class)
    public ResultErrorVo handleAuthorizationException(Exception ex) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
    	if (sw!=null) {
    		logger.error(sw.toString());
    	}
    	return new ResultErrorVo("未登录或超时(No login or login timeout)");
    }
    
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class) 
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResultErrorVo handleNoHandleFoundException(Exception ex) {
    	return new ResultErrorVo("no found"); 
    }
    
    @ResponseBody
    @ExceptionHandler(ExplicitException.class)
    public ResultErrorVo handleExplicitException(Exception ex) {    	
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
    	if (sw!=null) {
    		logger.error(sw.toString());
    	}
    	return new ResultErrorVo(ex.getMessage());
    }
    
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResultErrorVo runtimeException(Exception ex) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
    	if (sw!=null) {
    		logger.error(sw.toString());
    	}
    	return new ResultErrorVo(ex.getMessage());        
    }
    
    //todo by lfj 
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   // @ExceptionHandler(NoHandlerFoundException.class)
    public ResultErrorVo handle(HttpServletRequest request,NoHandlerFoundException ex) {
        return new ResultErrorVo(ex.getMessage()); 
      //  return new Result(404,"没有【"+request.getMethod()+"】"+request.getRequestURI()+"方法可以访问",null);
    }
    
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultErrorVo exception(Exception ex) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
    	if (sw!=null) {
    		logger.error(sw.toString());
    	}
    	return new ResultErrorVo(ex.getMessage());
    }
}