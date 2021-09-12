package cn.ccsit.eeoi.portal.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;



/*
 * 
 * Redis 集成类 
 * 
 */

@Configuration
@ConditionalOnProperty(name = "spring.cache.type",havingValue="redis")
public class RedisConfig {
	
	private static Logger logger = LogManager.getLogger(RedisConfig.class);
	
	
	public RedisConfig() {
		logger.info("Initializing redisConfig");
	}
	
	
	// * 重写 key todo 如需要时 
	/*
	@Bean
	@Override
    public KeyGenerator keyGenerator() {
			//@Override
        	return (target,method,params)->{
        		StringBuilder sb = new StringBuilder();
        		sb.append(target.getClass().getName());
        		sb.append(method.getName()); 
        		for(Object obj : params){
        			sb.append(obj.toString()); 
        		}
        		return sb.toString();
        	};
    }
	*/
	
	@Bean
	public CacheManager cacheManager(LettuceConnectionFactory factory) {
		logger.info("Initializing cacheManager");		
		return  RedisCacheManager.builder(factory).build();

	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(LettuceConnectionFactory factory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setKeySerializer(RedisSerializer.string());
		template.setConnectionFactory(factory);
		return template;
	}
	
}
