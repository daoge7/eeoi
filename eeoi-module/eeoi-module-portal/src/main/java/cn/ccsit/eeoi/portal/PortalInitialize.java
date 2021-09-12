package cn.ccsit.eeoi.portal;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

import cn.ccsit.eeoi.portal.cache.RedisConfig;
import cn.ccsit.eeoi.portal.service.PermissionService;

@Configuration
public class PortalInitialize {

	private static Logger logger = LogManager.getLogger(PortalInitialize.class);
	
	public PortalInitialize() {
		
		logger.info("PortalInitialize initialize");
	}
	
}
