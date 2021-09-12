package cn.ccsit.eeoi.system.service;

import java.util.List;

import cn.ccsit.eeoi.system.vo.RoleListDicVo;
import cn.ccsit.eeoi.system.vo.ServiceResultVo;
import cn.ccsit.eeoi.system.vo.SysRoleShowVo;
import cn.ccsit.eeoi.system.vo.SysRoleVo;
import cn.ccsit.common.vo.ResultPageVo;
import cn.ccsit.eeoi.common.service.CommonService;


public interface SysRoleService extends CommonService {
	
	/***
	 * 根据条件查找角色
	 * @return
	*/
	public ResultPageVo getSysRolesByVo(SysRoleVo sysRoleVo);
	
	
	/***
	 * 查找所有角色
	 * @return
	*/
	public List<RoleListDicVo> getAllRoles();
	
	/***
	 * 查找所有角色拥有的菜单
	 * @return
	*/
	public List<String> getRoleMenu(String roleId);

	/***
	 * 根据id删除角色
	 */
	public	ServiceResultVo deleteSysRole(String id);

	/***
	 * 保存角色信息
	 */
	public ServiceResultVo saveRole(SysRoleShowVo role);
	
	/***
	 * 获取用户角色明细
	 */
	public SysRoleShowVo getRoleDetail(String roleId);

}
