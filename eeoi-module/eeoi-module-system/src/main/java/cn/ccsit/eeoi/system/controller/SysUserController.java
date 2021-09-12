package cn.ccsit.eeoi.system.controller;

import java.util.List;

import cn.ccsit.eeoi.system.vo.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.ccsit.common.vo.ResultObjectVo;
import cn.ccsit.common.vo.ResultPageVo;
import cn.ccsit.common.vo.ResultStringVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.system.service.SysUserService;

@RestController
@RequestMapping("/sysUser")
public class SysUserController extends CommonController {
	@Autowired
	private SysUserService sysUserService;

	@GetMapping("userList")
	public ResultVo userList(SysUserVo sysUserVo) {
		ResultPageVo sysUsers = sysUserService.getSysUsersByVo(sysUserVo);
		return sysUsers;
	}

	//@RequiresPermissions("system:user:edit")
	@PostMapping("createUser")
	public ServiceResultVo CreateUser(@RequestBody(required = false) SysUserShowVo parameter) {
		ServiceResultVo result = sysUserService.saveSysUser(parameter);
		return result;
	}

	
	//@RequiresPermissions("system:user:edit")
	@PostMapping("deleteUserGcClient")
	public ServiceResultVo deleteUserGcClient(@RequestBody(required = false)UserGcClientParaVo  parameter) {
		if (parameter == null||parameter.getIds().size()==0) {
			String msg = this.msg("user.delete.success");
			return new ServiceResultVo(true, msg);
		}
		ServiceResultVo result = sysUserService.deleteUserGcClient(parameter.getIds());
		return result;
	}

	
	
	//@RequiresPermissions("system:user:detail")
	@GetMapping("userDetail")
	public ResultVo userDetail(String id) {
		SysUserShowVo sysUserVo = sysUserService.getSysUserById(id);
		if (sysUserVo != null) {
			ResultVo resultVo = new ResultObjectVo<SysUserShowVo>(sysUserVo);
			return resultVo;
		} else {
			String msg = this.msg("user.not.exist");
			ServiceResultVo result = new ServiceResultVo(false,msg);
			return result;
		}
	}

	//@RequiresPermissions("system:user:delete")
	@PostMapping("deleteUser")
	public ResultVo DeleteUser(@RequestBody(required = false) SysUserShowVo parameter) {
		if (parameter == null) {
			String msg = this.msg("user.delete.success");
			return new ServiceResultVo(true, msg);
		}
		ServiceResultVo result = sysUserService.deleteSysUser(parameter.getId());
		return result;
	}

	@PostMapping("updateUserPassword")
	public ResultVo UpdateUserPassword(@RequestBody(required = false) SysUserPasswordVo parameter) {
		ServiceResultVo result= sysUserService.updateUserPassword(parameter);
		return result;
	}

	//@RequiresPermissions("system:user:pwd")
	@PostMapping("resetUserPassword")
	public ResultVo ResetUserPassword(@RequestBody(required = false) SysUserPasswordVo parameter) {
		ServiceResultVo result= sysUserService.resetUserPassword(parameter);
		return result;
	}

	@PostMapping("saveAppUser")
	public ResultVo saveAppUser(@RequestBody(required = false) AppUserVo appUserVo) {
		return sysUserService.saveAppUser(appUserVo);
	}

	@GetMapping("findAppUserByShipId/{id}")
	public ResultVo findAppUserByShipId(@PathVariable String id) {
		return sysUserService.findAppUserByShipId(id);
	}

}
