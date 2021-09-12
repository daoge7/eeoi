package cn.ccsit.eeoi.system.service;

import java.util.List;
import java.util.Map;

import cn.ccsit.eeoi.common.service.CommonService;

public interface SysMenuService  extends CommonService {

	/***
	 * 获取所有菜单和按钮数据
	 */
	public List<Map<String, Object>> GetAllSysMenus();
}
