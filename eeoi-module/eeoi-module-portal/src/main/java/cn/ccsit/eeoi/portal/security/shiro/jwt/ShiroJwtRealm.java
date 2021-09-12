package cn.ccsit.eeoi.portal.security.shiro.jwt;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.eeoi.common.utils.JwtUtils;
import cn.ccsit.eeoi.portal.service.PermissionService;
import cn.ccsit.eeoi.system.entity.SysUser;
import org.springframework.stereotype.Component;

public class ShiroJwtRealm extends AuthorizingRealm {
	
	private static Logger logger = LogManager.getLogger(ShiroJwtRealm.class);
	
	public ShiroJwtRealm() {
		String authenticationCache=this.getClass().getName()+".AuthorizationCache";
		String authorizationCache=this.getClass().getName()+".AuthorizationCache";		
		this.setAuthenticationCacheName(authenticationCache);
		this.setAuthorizationCacheName(authorizationCache);
		logger.debug("Setting ShiroJwtRealm AuthenticationCacheName:"+authenticationCache);
		logger.debug("Setting ShiroJwtRealm authorizationCacheName:"+authorizationCache);
		
		this.setCachingEnabled(true);
		this.setAuthenticationCachingEnabled(true);
		this.setAuthorizationCachingEnabled(true);
		logger.info("Initializing ShiroJwtRealm");
	}
	
	@Autowired
	@Lazy
	private PermissionService permissionService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	/**
	 * ShiroJwtToken才可以访问
	 */
    @Override
    public boolean supports(AuthenticationToken token) {
    	return token instanceof ShiroJwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

    	ShiroJwtToken shiroJwtToken = (ShiroJwtToken) authenticationToken;
    	
    	String jwtToken = (String) shiroJwtToken.getPrincipal();    	
		if (jwtToken == null) {
			logger.info(" jwt token can't be null");
			throw new AuthenticationException("jwt token 不能为空");
		}
		String userName=JwtUtils.getUserName(jwtToken);
		
		if (userName == null||"".equals(userName)) {
			logger.info(" user name can't be null");
			throw new AuthenticationException("用户名 不能为空");
		}		
		SysUser sysUser =permissionService.findSysUserByUsername(userName);
		if (sysUser == null) {
			logger.info("The sys user dose't existed,user name:"+userName);
			throw new AuthenticationException("用户不存在");
		}
		
		return new SimpleAuthenticationInfo(userName,sysUser.getSalt(), getName());
    }

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		String userName = (String) SecurityUtils.getSubject().getPrincipal();		
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
       // permissionService=applicationContext.getBean(PermissionService.class);
        Set<String> permissions=permissionService.getPermissions(userName);
        permissions.add("valid");
        info.setStringPermissions(permissions);
        return info;
	}
	
	@Override 
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {		
		super.setCredentialsMatcher(
				(authenticationToken ,authenticationInfo)->{
					ShiroJwtToken shiroJwtToken = (ShiroJwtToken) authenticationToken;
					SimpleAuthenticationInfo  simpleAuthenticationInfo =(SimpleAuthenticationInfo)authenticationInfo;
					String jwtToken = shiroJwtToken.getToken();					
			        String userName = String.valueOf(simpleAuthenticationInfo.getPrincipals().getPrimaryPrincipal());			        
			        String salt=String.valueOf(simpleAuthenticationInfo.getCredentials());
			        try {
						return jwtUtils.verify(jwtToken, userName);
					} catch (MalformedClaimException | InvalidJwtException e) {
						throw new ExplicitException(e.getMessage());
					}
				}
			);
	}
}
