package cn.ccsit.eeoi.portal.security.shiro.jwt;

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
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.common.security.DigestSecurityUtils;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.portal.security.shiro.db.CustomByteSource;
import cn.ccsit.eeoi.portal.service.PermissionService;
import cn.ccsit.eeoi.system.entity.SysUser;
import cn.ccsit.eeoi.system.repository.SysUserRepository;
public class ShiroDbRealm extends AuthorizingRealm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3939011339269803046L;

	private static Logger logger = LogManager.getLogger(ShiroDbRealm.class);
	
	
	
	
	@Autowired 	
	@Lazy
	private PermissionService permissionService;
	
	
	@Autowired 	
	@Lazy
	private CommonService commonService;
	
	
	public ShiroDbRealm() {
		
		String authenticationCache=this.getClass().getName()+".AuthorizationCache";
		String authorizationCache=this.getClass().getName()+".AuthorizationCache";		
		this.setAuthenticationCacheName(authenticationCache);
		this.setAuthorizationCacheName(authorizationCache);
		logger.debug("Setting ShiroDbRealm AuthenticationCacheName:"+authenticationCache);
		logger.debug("Setting ShiroDbRealm AuthorizationCacheName:"+authorizationCache);
		
		this.setCachingEnabled(false);
		this.setAuthenticationCachingEnabled(false);
		this.setAuthorizationCachingEnabled(false);
		
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
        Set<String> permissions=permissionService.getPermissions(userName);
        info.setStringPermissions(permissions);
        return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {			
		
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
		String userName = usernamePasswordToken.getUsername();
		if (userName == null) {
			String msg=commonService.msg("user.name.null");
			logger.info("License name can't be null");
			throw new AuthenticationException(msg);
		}
		SysUser sysUser =permissionService.findSysUserByUsername(userName);
		if (sysUser == null) {
			logger.info("The sys user dose't existed,user name:"+userName);
			String msg=commonService.msg("user.not.exist");
			throw new AuthenticationException(msg);
		}
		//ByteSource salt = ByteSource.Util.bytes(sysUser.getSalt());
		CustomByteSource salt = new CustomByteSource(sysUser.getSalt());  //redis 只用能这个
		return new SimpleAuthenticationInfo(userName, sysUser.getPassword(), salt, getName());
	}
	
	@Override 
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {		
		super.setCredentialsMatcher(
				(authenticationToken ,authenticationInfo)->{
					String msg=commonService.msg("password.error");
					UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
					SimpleAuthenticationInfo  SimpleAuthenticationInfo =(SimpleAuthenticationInfo)authenticationInfo;					
					String rawPassword = String.valueOf((char[])usernamePasswordToken.getPassword());					
			        String dbPassword = String.valueOf(SimpleAuthenticationInfo.getCredentials());		
			        ByteSource bs=SimpleAuthenticationInfo.getCredentialsSalt();			        
			        String salt=CodecSupport.toString(bs.getBytes());
			        if( logger.isTraceEnabled()) {
			        	logger.trace("Inputting password:"+rawPassword);
			        	logger.trace("Querying password:"+dbPassword);
			        	logger.trace("ByteSource salt:"+salt);
			        }
			        String enCryptPassword=DigestSecurityUtils.sha256Digest(rawPassword, salt);
			        
			        if ((dbPassword==null)||(enCryptPassword==null)) {
			        	logger.info("the inputting password or the querying password can't be null");			        	
			        	throw new ExplicitException(msg);
			        	//return false ;
			        }
			        if (dbPassword.equals(enCryptPassword)){
			        	logger.info("Password Authentication success");
			        	return true ;
			        }
			        logger.info("Invalid password");
			        throw new ExplicitException(msg);
			        //return false ;
				}
			);
	}		
}
