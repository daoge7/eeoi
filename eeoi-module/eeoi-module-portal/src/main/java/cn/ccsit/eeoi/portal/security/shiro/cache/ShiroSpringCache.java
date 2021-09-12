package cn.ccsit.eeoi.portal.security.shiro.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.CacheException;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

public class ShiroSpringCache<K, V> implements org.apache.shiro.cache.Cache<K, V>{

	private static Logger logger = LogManager.getLogger(ShiroSpringCache.class);
	
	private Cache  cache;
	
	public ShiroSpringCache() {
	}
	
	public ShiroSpringCache(Cache cache) {
		if (cache==null) {
			logger.error("error:cache argument is null");
			throw new IllegalArgumentException("Cache argument cannot be null.");
		}
		this.cache=cache;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) throws CacheException {
		if (logger.isTraceEnabled()) {
				logger.trace("Getting object from cache [" + this.cache.getName() + "] for key [" + key + "]key type:" + key.getClass());
		}
		ValueWrapper valueWrapper = cache.get(key);
		if (valueWrapper == null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Element for [" + key + "] is null.");
			}
			return null;
		}
		return  (V) valueWrapper.get();
	}
	
	@Override
	public V put(K key, V value) throws CacheException {
		if (logger.isTraceEnabled()) {
			logger.trace("Putting object in cache [" + this.cache.getName() + "] for key [" + key + "]key type:" + key.getClass());
		}
		try {
			V previous = get(key);
			cache.put(key, value);
			return previous;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}
	
	@Override
	public V remove(K key) throws CacheException {
		 if (logger.isTraceEnabled()) {
			 logger.trace("Removing object from cache [" + this.cache.getName() + "] for key [" + key + "]key type:" + key.getClass());
		}
		try {
			V previous = get(key);
			cache.evict(key);
			return previous;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}
	
	@Override
	public void clear() throws CacheException {
		if (logger.isTraceEnabled()) {
			logger.trace("Clearing all objects from cache [" + this.cache.getName() + "]");
		}
		cache.clear();
	}

	
	@Override
	public Set<K> keys() {
		logger.error("ShiroSpringCache.keys:Method not implemented");
		return Collections.emptySet();
	}

	@Override
	public int size() {
		logger.error("ShiroSpringCache.size:Method not implemented");
		return 0;
	}

	@Override
	public Collection<V> values() {
		logger.error("ShiroSpringCache.values:Method not implemented");
		return Collections.emptySet();
	}

}
