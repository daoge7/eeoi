package cn.ccsit.eeoi.system.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.ccsit.eeoi.system.entity.SysUser;

public class SysUserShowVo {
	// 主键id
	private String id;
	// 登录账户(用户名)
	private String account;
	// 英文名称
	private String nameEn;
	// 中文名称
	private String nameCn;
	// 邮箱
	private String email;
	// 联系电话
	private String phone;
	// 用户状态
	private int userStatus;
	// 备注
	private String remark;
	// 最后操作时间
	private Date opdate;
	// 公司id
	private List<String> companyIds;
	// 用户角色id
	private List<String> userRole;
	// 用户角色id
	private String userRoleName;

	public String getUserRoleName() {
		return userRoleName;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}

	// 公司信息集合
	private List<UserGcClientVo> companyList;

	
	public List<UserGcClientVo> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<UserGcClientVo> companyList) {
		this.companyList = companyList;
	}

	public Date getOpdate() {
		return opdate;
	}

	public void setOpdate(Date opdate) {
		this.opdate = opdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public List<String> getUserRole() {
		return userRole;
	}

	public void setUserRole(List<String> userRole) {
		this.userRole = userRole;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<String> getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(List<String> companyIds) {
		this.companyIds = companyIds;
	}

	public SysUserShowVo() {

	}

	public SysUserShowVo(SysUser user) {
		this.account = user.getUsername();
		this.email = user.getEmail();
		this.id = user.getId();
		this.nameCn = user.getNickname();
		this.nameEn = user.getNicknameEn();
		this.phone = user.getPhone();
		this.remark = user.getRemark();
		this.userStatus = user.getStatus();
		this.opdate = user.getOpdate();
	}

}
