package cn.ccsit.eeoi.portal.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ccsit.common.vo.ResultStringVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.utils.JwtUtils;
import cn.ccsit.eeoi.portal.service.PermissionService;

@RestController
public class TestController {

	private static Logger logger = LogManager.getLogger(TestController.class);
	
	public TestController() {
		
	}
	
	@Autowired 
	private PermissionService permissionService ;
	
	@Autowired 
	@Lazy
	private JwtUtils  jwtUtils;
	
	
	//@Autowired 
	//@Lazy
	private JCacheCacheManager jCacheCacheManager;
	
	@GetMapping("/testvalid")
	public ResultVo Testvalid() {
		return new ResultStringVo("测试");
	}
}
