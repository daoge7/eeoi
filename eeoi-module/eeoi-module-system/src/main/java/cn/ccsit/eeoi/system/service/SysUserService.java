package cn.ccsit.eeoi.system.service;

import java.util.List;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.system.vo.*;
import org.springframework.data.domain.Page;

import cn.ccsit.common.vo.ResultPageVo;
import cn.ccsit.eeoi.common.service.CommonService;


public interface SysUserService extends CommonService {
	
	/***
	 * 查找所有角色
	 * @return
	*/
	public ResultPageVo getSysUsersByVo(SysUserVo sysUserVo);
	/***
	 *
	 * @see 保存用户
	 * @param parameter
	 * @return
	 */
	public ServiceResultVo saveSysUser(SysUserShowVo parameter);
	/***
	 * 删除用户
	 * @param id
	 * @return 
	 */
	public ServiceResultVo deleteSysUser(String id);
	/***
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	public SysUserShowVo getSysUserById(String id);
	
	/***
	 * 根据登录名查询用户
	 * @param account
	 * @return
	 */
	public SysUserShowVo getSysUserByAccount(String account);
	
	/***
	 * 删除用户所属公司
	 * @param account
	 * @return
	 */
	public ServiceResultVo deleteUserGcClient(List<String> ids);
	
	
	/***
	 * 更新密码
	 */
	public ServiceResultVo updateUserPassword(SysUserPasswordVo parameter); 
	
	/***
	 * 重置密码
	 */
	public ServiceResultVo resetUserPassword(SysUserPasswordVo parameter);

	ServiceResultVo saveAppUser(AppUserVo appUserVo);

	ResultVo findAppUserByShipId(String shipId);
}
