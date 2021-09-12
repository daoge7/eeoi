package cn.ccsit.eeoi.portal.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.ccsit.common.enums.language.LangType;
import cn.ccsit.common.security.DigestSecurityUtils;
import cn.ccsit.eeoi.portal.vo.PermissionButtonVo;
import cn.ccsit.eeoi.portal.vo.PermissionPageVo;
import cn.ccsit.eeoi.system.entity.SysUser;

public interface PermissionService {

	public Set<String> getPermissions(String userName);
	
	public SysUser findSysUserByUsername(String userName);
	
	public List<PermissionButtonVo> findPermissionButtonByUserId(String userId);
	
	public List<Map<String, Object>> findSysMenuByUserId(String userId, LangType langType);
}
