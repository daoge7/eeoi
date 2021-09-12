package cn.ccsit.eeoi.portal.security.shiro.db;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import cn.ccsit.eeoi.portal.security.shiro.cache.ShiroSpringCacheManager;

@Configuration
@ConditionalOnProperty(name = "shiro.type",havingValue="db")
@AutoConfigureBefore(ShiroWebAutoConfiguration.class)
public class ShiroDbConfig{
	private static Logger logger = LogManager.getLogger(ShiroDbConfig.class);

	public ShiroDbConfig() {
		logger.info("Initializing ShiroDbConfig(ShiroDbConfig)");
	}
	
	@Value("#{ @environment['shiro.anon.filter.urls']}")
	protected String anonFilterUrls;
	
	@Bean
	public ShiroFilterChainDefinition  shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
		
		//处理不需要登录的url
		if (anonFilterUrls==null||"".equals(anonFilterUrls)) {
			logger.error("application.properties:shiro.anon.filter.urls must be valid url (for example shiro.anon.filter.urls=/login,/js/**");			
		}else {
			Arrays.asList(anonFilterUrls.split(",")).forEach(item->shiroFilterChainDefinition.addPathDefinition(item, "anon"));
		}
		//处理需要注销的url
		shiroFilterChainDefinition.addPathDefinition("/logout", "logout");
		//处理需要登录的url
		shiroFilterChainDefinition.addPathDefinition("/**", "authc");
		
		return shiroFilterChainDefinition;
	}

	@Bean
	public SessionsSecurityManager securityManager(Realm shiroDbRealm,CacheManager cacheManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        SessionManager sessionManager=new org.apache.shiro.web.session.mgt.DefaultWebSessionManager();
//        //sessionManager
//        securityManager.setSessionManager(sessionManager);
		// 设置超时
		DefaultWebSessionManager xx=(DefaultWebSessionManager) securityManager.getSessionManager();
		xx.setGlobalSessionTimeout(43200000);
		securityManager.setSessionManager(xx);
		ShiroSpringCacheManager shiroSpringCacheManager=new ShiroSpringCacheManager(cacheManager);
		securityManager.setCacheManager(shiroSpringCacheManager);
		securityManager.setRealm(shiroDbRealm);
		return securityManager;
	}
	
	@Bean("shiroDbRealm")
	public Realm shiroDbRealm() {
		Realm shiroDbRealm = new ShiroDbRealm();
		return shiroDbRealm;
	}
	
	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
}
