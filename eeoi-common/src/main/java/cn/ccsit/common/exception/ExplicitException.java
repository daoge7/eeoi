package cn.ccsit.common.exception;

/**
 * 明确的自定义异常
 * @author lfj
 *
 */
public class ExplicitException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2953028118818766667L;

	
	public ExplicitException() {
		
	}

	public ExplicitException(String message) {
		super(message);
	}

	public ExplicitException(Throwable cause) {
		super(cause);
	}

	public ExplicitException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExplicitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
