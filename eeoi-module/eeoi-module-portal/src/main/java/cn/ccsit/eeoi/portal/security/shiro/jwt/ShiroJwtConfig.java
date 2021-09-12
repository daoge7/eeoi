package cn.ccsit.eeoi.portal.security.shiro.jwt;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import cn.ccsit.eeoi.common.utils.JwtUtils;
import cn.ccsit.eeoi.portal.security.shiro.cache.ShiroSpringCacheManager;

@Configuration
@ConditionalOnProperty(name = "shiro.type", havingValue = "jwt")
@AutoConfigureBefore(ShiroWebAutoConfiguration.class)
public class ShiroJwtConfig {

	private static Logger logger = LogManager.getLogger(ShiroJwtConfig.class);

	@Value("#{ @environment['shiro.anon.filter.urls']}")
	private String anonFilterUrls;

	@Value("#{ @environment['shiro.loginUrl']}")
	private String loginUrl;

	@Value("#{ @environment['shiro.successUrl']}")
	private String successUrl;

	@Autowired
	@Lazy
	private JwtUtils jwtUtils;

	@Autowired
	protected Map<String, Filter> filterMap;

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();

		// 处理不需要登录的url
		if (anonFilterUrls == null || "".equals(anonFilterUrls)) {
			logger.error(
					"application.properties:shiro.anon.filter.urls must be valid url (for example shiro.anon.filter.urls=/login,/js/**");
		} else {
			Arrays.asList(anonFilterUrls.split(","))
					.forEach(item -> shiroFilterChainDefinition.addPathDefinition(item, "anon"));
		}

		// 处理需要注销的url
		shiroFilterChainDefinition.addPathDefinition("/logout", "logout");

		// 处理需要登录的url
		shiroFilterChainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken"); // 对应自定义过滤器
																								// noSessionCreation
		return shiroFilterChainDefinition;
	}

	@Bean
	protected ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
			ShiroFilterChainDefinition shiroFilterChainDefinition) {
		ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

		filterFactoryBean.setLoginUrl(loginUrl);
		filterFactoryBean.setSuccessUrl(successUrl);
		filterFactoryBean.setUnauthorizedUrl("404.html");

		filterFactoryBean.setSecurityManager(securityManager);
		filterMap.put("authcToken", new ShiroJwtFilter(jwtUtils));//不能采用@Bean 方式注入，因为过滤器注入后就起作用
		filterFactoryBean.setFilters(filterMap);
		filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

		return filterFactoryBean;
	}

	@Bean	
	public ShiroJwtFilter shiroJwtFilter() {
		ShiroJwtFilter shiroJwtFilter =new ShiroJwtFilter();
		return shiroJwtFilter;
	}
	
	@Bean
	public DefaultWebSecurityManager securityManager(Realm shiroDbRealm, Realm shiroJwtRealm,
			CacheManager cacheManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		ShiroSpringCacheManager shiroSpringCacheManager = new ShiroSpringCacheManager(cacheManager);
		securityManager.setCacheManager(shiroSpringCacheManager);
		securityManager.setRealms(Arrays.asList(shiroDbRealm, shiroJwtRealm));
		return securityManager;
	}

	@Bean("shiroDbRealm")
	public Realm shiroDbRealm() {
		Realm shiroDbRealm = new ShiroDbRealm();
		return shiroDbRealm;
	}

	@Bean("shiroJwtRealm")
	public Realm shiroJwtRealm() {
		Realm shiroJwtRealm = new ShiroJwtRealm();
		return shiroJwtRealm;
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

}
