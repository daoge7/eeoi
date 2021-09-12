package cn.ccsit.eeoi.system.vo;

import cn.ccsit.common.vo.page.PageVo;

public class SysUserVo extends PageVo  {

	//用户中文名，用户英文名，用户名
	private String account;
	//状态：1:正常,2:冻结
	private Integer userstatus;
    //所属船公司中文名称，英文名称
	private String companyName;

	private String roleId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(Integer userstatus) {
		this.userstatus = userstatus;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
