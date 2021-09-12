package cn.ccsit.eeoi.portal.vo;

import java.util.List;

public class PermissionButtonVo {

	private String pagePerms;
	
	private List<String> buttonPerms;

	public String getPagePerms() {
		return pagePerms;
	}

	public void setPagePerms(String pagePerms) {
		this.pagePerms = pagePerms;
	}

	public List<String> getButtonPerms() {
		return buttonPerms;
	}

	public void setButtonPerms(List<String> buttonPerms) {
		this.buttonPerms = buttonPerms;
	}
}
