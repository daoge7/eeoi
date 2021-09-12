package cn.ccsit.eeoi.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsit.common.utils.StringUtils;
import cn.ccsit.common.vo.ResultPageVo;
import cn.ccsit.eeoi.system.vo.*;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.system.entity.*;
import cn.ccsit.eeoi.system.repository.SysRoleMenuRepository;
import cn.ccsit.eeoi.system.repository.SysRoleRepository;
import cn.ccsit.eeoi.system.repository.SysUserRoleRepository;

@Service("sysRoleService")
public class SysRoleServiceImpl extends CommonServiceImpl implements SysRoleService, CommonService {
	@Autowired
	private SysRoleRepository sysRoleRepository;

	@Autowired
	private SysRoleMenuRepository sysRoleMenuRepository;

	@Autowired
	private SysUserRoleRepository sysUserRoleRepository;

	/*
	 * 按条件查找角色信息，返回列表
	 */
	@Override
	public ResultPageVo getSysRolesByVo(SysRoleVo sysRoleVo) {
		Page<SysRole> filePage = null;
		String jpql = "select c from SysRole c where isDelete=0";
		if (sysRoleVo == null) {
			PageRequest pageable = PageRequest.of(0, 15);
			jpql += "order by c.title ";
			filePage = commonRepository.findPagedDataByJPQLCriteria(SysRole.class, jpql, pageable);
		} else {
			PageRequest pageable = PageRequest.of(sysRoleVo.getCurrentPage() - 1, sysRoleVo.getPageSize());
			// 角色名称
			if (StringUtils.isNotEmpty(sysRoleVo.getName())) {
				sysRoleVo.setName(sysRoleVo.getName().toUpperCase());// 转换成大写
				sysRoleVo.setName(sysRoleVo.getName().replace(" ", ""));// 空格
				jpql += " and replace(upper(c.title), ' ','') like '%" + sysRoleVo.getName() + "%'";
			}
			// 状态
			if (sysRoleVo.getStatus() != null) {
				jpql += " and c.status=" + sysRoleVo.getStatus();
			}
			jpql += " order by c.title asc ";
			filePage = commonRepository.findPagedDataByJPQLCriteria(SysRole.class, jpql, pageable);
		}

		List<SysRoleShowVo> resultList = new ArrayList<SysRoleShowVo>();
		for (SysRole role : filePage) {
			SysRoleShowVo roleShow = new SysRoleShowVo(role);
			resultList.add(roleShow);
		}
		ResultPageVo data = new ResultPageVo(filePage.getTotalElements(), resultList);
		return data;
	}

	/*
	 * 查找所有未冻结的角色
	 */
	public List<RoleListDicVo> getAllRoles() {
		List<SysRole> fromSysRoles = sysRoleRepository.findSysRoles();

		List<RoleListDicVo> roles = new ArrayList<RoleListDicVo>();

		for (SysRole item : fromSysRoles) {
			if (item.getStatus() != 1) {
				continue;
			}
			RoleListDicVo model = new RoleListDicVo();
			model.setId(item.getId());
			model.setTitle(item.getTitle());
			roles.add(model);
		}
		return roles;
	}

	/*
	 * 查找角色id对应的菜单
	 */
	public List<String> getRoleMenu(String roleId) {
		SysRole sysRole = sysRoleRepository.findSysRoleById(roleId);
		List<String> results = new ArrayList<String>();
		if (sysRole.getStatus() != 1) {
			return results;
		}
		List<SysRoleMenu> sysRoleMenus = sysRoleMenuRepository.findSysRoleMenuByUserId(roleId);

		for (SysRoleMenu item : sysRoleMenus) {
			results.add(item.getMenuId());
		}
		return results;
	}

	
	/***
	 * 根据id删除角色
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ServiceResultVo deleteSysRole(String id) {
		SysRole sysRole = sysRoleRepository.findSysRoleById(id);
		if (sysRole == null) {
			String msg = this.msg("user.delete.success");
			return new ServiceResultVo(true, msg);
		}
		List<SysRoleMenu> sysRoleMenus = sysRoleMenuRepository.findSysRoleMenuByUserId(id);
		sysRoleMenuRepository.deleteAll(sysRoleMenus);

		// 删除数据库原有对应人员信息
		List<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
		List<SysUserRole> sysUserRoles = sysUserRoleRepository.findSysUserRoleByRoleId(id);
		for (SysUserRole role : sysUserRoles) {
			role.setIsDelete(1);
			role.setOpdate(new Date());
			userRoleList.add(role);
		}
		sysUserRoleRepository.saveAll(userRoleList);

		sysRole.setIsDelete(1);
		sysRole.setOpdate(new Date());
		sysRole = sysRoleRepository.save(sysRole);
		if (sysRole != null) {
			String msg = this.msg("user.delete.success");
			return new ServiceResultVo(true, msg);
		} else {
			String msg = this.msg("user.delete.fail");
			return new ServiceResultVo(false, msg);
		}
	}

	/***
	 * 保存
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ServiceResultVo saveRole(SysRoleShowVo role) {
		if (role == null) {
			String msg = this.msg("user.save.fail");
			return new ServiceResultVo(false, msg);
		}

		String roleId = role.getId();
		String title = role.getTitle().trim();
		List<String> roleIds = sysRoleRepository.findSysRoleByIdAndTitle(title);
		if (!roleIds.contains(roleId)&&roleIds.size()>0) {
			String msg = this.msg("role.title.exist");
			return new ServiceResultVo(false, msg);
		}
		SysRole sysRole = sysRoleRepository.findSysRoleById(role.getId());
		if (sysRole == null) {
			sysRole = new SysRole();
			sysRole.setId(java.util.UUID.randomUUID().toString());
			sysRole.setCreateTm(new Date());
		}
		sysRole.setIsDelete(0);
		sysRole.setTitle(role.getTitle());
		sysRole.setStatus(role.getStatus());
		sysRole.setRemark(role.getRemark());
		sysRole.setOpdate(new Date());
		List<SysRoleMenu> sysRoleMenus = sysRoleMenuRepository.findSysRoleMenuByUserId(roleId);
		sysRoleMenuRepository.deleteAll(sysRoleMenus);

		List<SysRoleMenu> adds = new ArrayList<SysRoleMenu>();
		List<String> menuIds = role.getMenuIds();
		for (String menuId : menuIds) {
			SysRoleMenu model = new SysRoleMenu();
			model.setCreateTm(new Date());
			model.setIsDelete(0);
			model.setMenuId(menuId);
			model.setOpdate(new Date());
			model.setRoleId(sysRole.getId());
			adds.add(model);
		}
		sysRoleMenuRepository.saveAll(adds);
		sysRole = sysRoleRepository.save(sysRole);

		if (sysRole != null) {
			return new ServiceResultVo(true, sysRole.getId());
		} else {
			String msg = this.msg("user.save.fail");
			return new ServiceResultVo(false, msg);
		}
	}

	public SysRoleShowVo getRoleDetail(String roleId) {
		SysRole sysRole = sysRoleRepository.findSysRoleById(roleId);
		if (sysRole == null) {
			return null;
//			String msg = this.msg("role.not.exist");
//			return new ServiceResultVo(false, msg);
		}
		List<SysRoleMenu> sysRoleMenus = sysRoleMenuRepository.findSysRoleMenuByUserId(roleId);
		List<String> menuIds = new ArrayList<String>();
		for (SysRoleMenu item : sysRoleMenus) {
			menuIds.add(item.getMenuId());
		}
		SysRoleShowVo returnItem = new SysRoleShowVo(sysRole);
		returnItem.setMenuIds(menuIds);
		return returnItem;
	}
}
