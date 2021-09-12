package cn.ccsit.eeoi.portal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsit.common.enums.language.LangType;
import cn.ccsit.common.utils.StringUtils;
import cn.ccsit.eeoi.portal.vo.PermissionButtonVo;
import cn.ccsit.eeoi.portal.vo.PermissionPageVo;
import cn.ccsit.eeoi.system.entity.SysRole;
import cn.ccsit.eeoi.system.entity.SysUser;
import cn.ccsit.eeoi.system.repository.SysRoleRepository;
import cn.ccsit.eeoi.system.repository.SysUserRepository;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;

@Service("permissionService")
//@Transactional(readOnly = true)
public class PermissionServiceImpl extends CommonServiceImpl implements PermissionService {

	private static Logger logger = LogManager.getLogger(PermissionServiceImpl.class);

	@Autowired
	private SysUserRepository sysUserRepository;

	@Autowired
	private SysRoleRepository sysRoleRepository;

	public PermissionServiceImpl() {

	}

	// @Transactional()
	// @Cacheable(cacheNames = "dicCache",key = "'permission_'+#userName")
	public Set<String> getPermissions(String userId) {
		logger.info("Excutting method getPermissions ,parameter userName:" + userId);

		String jpql = "SELECT m.perms " + " FROM " + "SysRoleMenu rm," + "SysMenu m," + "SysUserRole ur" + " WHERE "
				+ "rm.menuId = m.id" + " AND rm.roleId = ur.roleId" + " AND m.menuType = 3" + " AND m.isDelete = 0"
				+ " AND rm.isDelete = 0" + " AND ur.isDelete = 0" + " AND ur.userId='" + userId + "'";
		List<String> userButtons = new ArrayList<String>();
		userButtons = commonRepository.findAllByJPQLCriteria(String.class, jpql);
		Set<String> stringSet = new HashSet<>();

		for (String userButton : userButtons) {
			stringSet.add(userButton);
		}
		return stringSet;
	}

	@Override
	public SysUser findSysUserByUsername(String userName) {
		SysUser sysUser = sysUserRepository.findSysUserByUsername(userName);
		return sysUser;
	}

//@CachePut()
//@CacheEvict()
	public List<Map<String, Object>> findSysMenuByUserId(String userId, LangType langType) {
		String jpql = "SELECT new cn.ccsit.eeoi.portal.vo.PermissionPageVo(m.id," + "m.pid," + "m.url," + "m.title,"
				+ "m.icon, m.perms, m.seqNo, m.menuType, m.titleEn) " 
				+ " FROM " + "SysRoleMenu rm," + "SysMenu m," + "SysUserRole ur"
				+ " WHERE " + "rm.menuId = m.id" + " AND rm.roleId = ur.roleId" + " AND m.menuType != 3"
				+ " AND m.status = 1"
				+ " AND m.isDelete = 0 AND rm.isDelete = 0 AND ur.isDelete = 0 AND ur.userId='" + userId
				+ "'";
		List<PermissionPageVo> userMenus = new ArrayList<PermissionPageVo>();
		userMenus = commonRepository.findAllByJPQLCriteria(PermissionPageVo.class, jpql);

		List<PermissionPageVo> results = new ArrayList<PermissionPageVo>();
		List<String> menuIds = new ArrayList<String>();
		for (PermissionPageVo ms : userMenus) {
			if(!menuIds.contains(ms.getMenuId())) {
				results.add(ms);
				menuIds.add(ms.getMenuId());
			}
		}
		List<Map<String, Object>> resultList = getChild("0", results, langType);

		return resultList;
	}

	/***
	 * pid 父id allList 所有数据库查出来的list数据
	 */
	private List<Map<String, Object>> getChild(String pid, List<PermissionPageVo> allList, LangType langType) {

		List<Map<String, Object>> childList = new ArrayList<>();// 用于保存子节点的list

		for (PermissionPageVo ms : allList) {
			// 判断传入的父id是否等于自身的，如果是，就说明自己是子节点
			if (pid.equals(ms.getPid())) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", ms.getMenuId());
				map.put("pid", ms.getPid());
				if (langType==LangType.zh_CN) {
					map.put("title", (ms.getTitle() == null ? ms.getTitleEn() : ms.getTitle()));
				} else {
					map.put("title", (ms.getTitleEn() == null ? ms.getTitle() : ms.getTitleEn()));
				}
				map.put("perms", ms.getPerms());
				map.put("icon", ms.getIcon());
				map.put("sort", ms.getSort());
				map.put("url", ms.getUrl());
				map.put("menuType", ms.getMenuType());
				map.put("children", new Object[] {});
				childList.add(map); // 加入子节点
			}
		}
		//排序
		Collections.sort(childList, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Integer name1 = Integer.valueOf(o1.get("sort").toString());
				Integer name2 = Integer.valueOf(o2.get("sort").toString());
				return name1.compareTo(name2);
			}
		});
		for (Map<String, Object> map : childList) {// 遍历子节点，继续递归判断每个子节点是否还含有子节点
			List<Map<String, Object>> tList = getChild(String.valueOf(map.get("id")), allList, langType);
			map.put("children", tList);
		}
		return childList;
	}

	public List<PermissionButtonVo> findPermissionButtonByUserId(String userId) {
		List<PermissionButtonVo> results = new ArrayList<PermissionButtonVo>();
		for (int i = 0; i < 3; i++) {
			PermissionButtonVo result = new PermissionButtonVo();
			List<String> buttons = new ArrayList<String>();
			for (int j = 0; j < 3; j++) {
				buttons.add("BUTTON" + j);
				// result.setButtonPerms(buttonPerms);
			}
			result.setPagePerms("system" + i);
			result.setButtonPerms(buttons);
			results.add(result);
		}
		return results;
	}

}
