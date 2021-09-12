package cn.ccsit.eeoi.system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ccsit.common.vo.ResultObjectVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.system.service.SysMenuService;

@RestController
@RequestMapping("/sysMenu")
public class SysMenuController extends CommonController {

	
	@Autowired
	private SysMenuService sysMenuService;
	
	@GetMapping("allSysMenus")
	public ResultVo  GetAllSysMenus(){
		List<Map<String, Object>> menus=sysMenuService.GetAllSysMenus();
		ResultObjectVo<List<Map<String, Object>>> results=new ResultObjectVo<List<Map<String, Object>>>(menus);
		return results;
	}
}
