package cn.ccsit.eeoi.portal.security.shiro.db;

import java.io.Serializable;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cn.ccsit.common.security.DigestSecurityUtils;
import cn.ccsit.eeoi.portal.service.PermissionService;
import cn.ccsit.eeoi.system.entity.SysUser;
//@Component("shiroDbRealm")
public class ShiroDbRealm extends AuthorizingRealm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3939011339269803046L;

	private static Logger logger = LogManager.getLogger(ShiroDbRealm.class);
	
	@Autowired 	
	@Lazy
	private PermissionService permissionService;
	
	 //@Autowired
	 //private ApplicationContext applicationContext;
	
	public ShiroDbRealm() {
		this.setCachingEnabled(true);
		this.setAuthenticationCachingEnabled(true);
		this.setAuthorizationCachingEnabled(true);
		
		logger.info("Initializing ShiroDbRealm");
	}
	
	@Override
    public boolean supports(AuthenticationToken token) {
		
        return token instanceof UsernamePasswordToken;
    }
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		String userName = (String) SecurityUtils.getSubject().getPrincipal();		
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
       // permissionService=applicationContext.getBean(PermissionService.class);
        Set<String> permissions=permissionService.getPermissions(userName);
        info.setStringPermissions(permissions);
        return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {			
		
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
		String userName = usernamePasswordToken.getUsername();
		if (userName == null) {
			logger.info("License name can't be null");
			throw new AuthenticationException("用户名不能为空");
		}
		SysUser sysUser =permissionService.findSysUserByUsername(userName);
		if (sysUser == null) {
			logger.info("The sys user dose't existed,user name:"+userName);
			throw new AuthenticationException("用户不存在");
		}
		//ByteSource salt = ByteSource.Util.bytes(sysUser.getSalt());
		CustomByteSource salt = new CustomByteSource(sysUser.getSalt());  //redis只能用这个
		return new SimpleAuthenticationInfo(userName, sysUser.getPassword(), salt, getName());
	}
	
	@Override 
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {		
		super.setCredentialsMatcher(
				(authenticationToken ,authenticationInfo)->{
					UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
					SimpleAuthenticationInfo  SimpleAuthenticationInfo =(SimpleAuthenticationInfo)authenticationInfo;
					String rawPassword = String.valueOf((char[])usernamePasswordToken.getPassword());					
			        String dbPassword = String.valueOf(SimpleAuthenticationInfo.getCredentials());			        
			        String salt=String.valueOf(SimpleAuthenticationInfo.getCredentialsSalt());
			        if( logger.isTraceEnabled()) {
			        	logger.trace("Inputting password:"+rawPassword);
			        	logger.trace("Querying password:"+dbPassword);
			        	logger.trace("ByteSource salt:"+salt);
			        }
			        String enCryptPassword=DigestSecurityUtils.sha256Digest(rawPassword, salt);
			        if ((dbPassword==null)||(enCryptPassword==null)) {
			        	logger.info("the inputting password or the querying password can't be null");
			        	return false ;
			        }
			        if (dbPassword.equals(enCryptPassword)){
			        	logger.info("Password Authentication success");
			        	return true ;
			        }
			        logger.info("Invalid password");
			        return false ;
				}
			);
	}		
}
