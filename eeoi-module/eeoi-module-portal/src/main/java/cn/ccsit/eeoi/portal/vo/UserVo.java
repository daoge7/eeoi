package cn.ccsit.eeoi.portal.vo;

import cn.ccsit.eeoi.system.entity.SysRole;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserVo {

	
	public UserVo() {
	}
	



	public List<Map<String, Object>> getAllowPage() {
		return allowPage;
	}

	public void setAllowPage(List<Map<String, Object>> allowPage) {
		this.allowPage = allowPage;
	}


	/**
	 * 用户名
	 */
	private String username;

	private String userId;
	private Set<SysRole> sysRoles;

	private String shipId;
	private String message;
	private String phone;
	private String shipName;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getShipId() {
		return shipId;
	}

	public void setShipId(String shipId) {
		this.shipId = shipId;
	}

	public Set<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 用户姓名
	 */
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 登录token;
	 */
	private String token;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	private Set<String>   allowButton;

	public Set<String> getAllowButton() {
		return allowButton;
	}




	public void setAllowButton(Set<String> allowButton) {
		this.allowButton = allowButton;
	}


	private List<Map<String,Object>>  allowPage;
}
