package cn.ccsit.eeoi.portal.security.shiro.jwt;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * 自定义token处理jwt
 * @author lfj
 *
 */
@Component
public class ShiroJwtToken implements HostAuthenticationToken,RememberMeAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3549408018678037431L;

	private String token;
	private String host;
	private boolean rememberMe;
	
	public ShiroJwtToken() {
		
	}
	
	public ShiroJwtToken(String token) {
		this.token=token;
	}
	
	public ShiroJwtToken(String token, String host, boolean rememberMe) {
		this.token=token;
		this.host=host;
		this.rememberMe=rememberMe;		
	}

	public String getToken() {
		return token;
	}
	
	@Override
	public Object getCredentials() {		
		return token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public boolean isRememberMe() {
		return rememberMe;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
    public String toString(){

        return token + ':' + host;

    }
}
