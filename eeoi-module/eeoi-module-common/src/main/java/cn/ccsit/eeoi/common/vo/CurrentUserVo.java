package cn.ccsit.eeoi.common.vo;

import java.util.List;

import cn.ccsit.eeoi.system.entity.SysUserRole;

public class CurrentUserVo {

	public CurrentUserVo() {
		
	}
	//用户id
	private String Id;
	//用户名
	private String userName;
	//中文名称
	private String nameCn;
	//英文名称
	private String nameEn;
	//角色信息
	private List<SysUserRole> roles;
	//船公司code
	private List<String> companyCodes;
	//船公司iacs
	private List<String> companyIacses;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}


	public List<SysUserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysUserRole> roles) {
		this.roles = roles;
	}

	public List<String> getCompanyCodes() {
		return companyCodes;
	}

	public void setCompanyCodes(List<String> companyCodes) {
		this.companyCodes = companyCodes;
	}

	public List<String> getCompanyIacses() {
		return companyIacses;
	}

	public void setCompanyIacses(List<String> companyIacses) {
		this.companyIacses = companyIacses;
	}
	
}
