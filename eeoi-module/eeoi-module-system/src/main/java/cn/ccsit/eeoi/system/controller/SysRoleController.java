package cn.ccsit.eeoi.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ccsit.common.vo.ResultObjectVo;
import cn.ccsit.common.vo.ResultPageVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.system.service.SysRoleService;
import cn.ccsit.eeoi.system.vo.RoleListDicVo;
import cn.ccsit.eeoi.system.vo.ServiceResultVo;
import cn.ccsit.eeoi.system.vo.SysRoleShowVo;
import cn.ccsit.eeoi.system.vo.SysRoleVo;
import cn.ccsit.eeoi.system.vo.SysUserShowVo;

@RestController
@RequestMapping("/sysRole")
public class SysRoleController extends CommonController {
	@Autowired
	private SysRoleService sysRoleService;

	@GetMapping("roleList")
	public ResultVo getSysRolesByVo (SysRoleVo sysRoleVo) {
		ResultPageVo sysRoles = sysRoleService.getSysRolesByVo(sysRoleVo);
		return sysRoles;
	}

	@GetMapping("roleListDic")
	public ResultVo getAllRoles() {
		List<RoleListDicVo> sysRoles = sysRoleService.getAllRoles();
		return new ResultObjectVo<List<RoleListDicVo>>(sysRoles);
	}

	@GetMapping("roleMenu")
	public ResultVo getRoleMenu(String roleId) {
		List<String> results = sysRoleService.getRoleMenu(roleId);
		return new ResultObjectVo<List<String>>(results);
	}
	
	@PostMapping("deleteRole")
	public ResultVo deleteSysRole(@RequestBody(required = false) SysRoleShowVo role) {
		ServiceResultVo results = sysRoleService.deleteSysRole(role.getId());
		return results;
	}
	
	
	@PostMapping("saveRole")
	public ResultVo saveRole(@RequestBody(required = false) SysRoleShowVo role) {
		ServiceResultVo results = sysRoleService.saveRole(role);
		return results;
	}
	
	@GetMapping("roleDetail")
	public ResultVo getRoleDetail(String roleId) {
		SysRoleShowVo sysRoles = sysRoleService.getRoleDetail(roleId);
		if(sysRoles!=null){
			ResultVo resultVo = new ResultObjectVo<SysRoleShowVo>(sysRoles);
			return resultVo;
		} else {
			String msg = this.msg("role.not.exist");
			ServiceResultVo result = new ServiceResultVo(false,msg);
			return result;
		}
		
	}
	
}
