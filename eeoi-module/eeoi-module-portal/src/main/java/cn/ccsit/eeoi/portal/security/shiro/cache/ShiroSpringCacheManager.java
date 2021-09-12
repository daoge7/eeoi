package cn.ccsit.eeoi.portal.security.shiro.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.Destroyable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ShiroSpringCacheManager implements org.apache.shiro.cache.CacheManager,Destroyable{

	private static Logger logger = LogManager.getLogger(ShiroSpringCacheManager.class);
	
	//@Autowired
	//@Lazy
	private CacheManager  cacheManager;
	
	public ShiroSpringCacheManager(CacheManager  cacheManager) {

		this.cacheManager=cacheManager;
	}
	
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		if (logger.isTraceEnabled()){
			logger.trace("Getting cache by  name [" +name + "]");
		}
		org.springframework.cache.Cache cache = cacheManager.getCache(name);
		return new ShiroSpringCache<K, V>(cache);
	}
	
	@Override
	public void destroy() throws Exception {
		logger.info("Executing ShiroSpringCacheManager.destroy:cacheManager =null");
		cacheManager = null;
	}

	

}
