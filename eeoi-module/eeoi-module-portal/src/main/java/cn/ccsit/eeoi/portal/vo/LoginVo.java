package cn.ccsit.eeoi.portal.vo;

import cn.ccsit.common.enums.language.LangType;

public class LoginVo {

	public LoginVo() {		
	}
	private String username;
	private String password;
	private String verifycode;
	//0或空：PC端，1:手机app端
	private String terminalType;
	private int  i18n=1;
	
	public String getVerifycode() {
		return verifycode;
	}
	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getI18n() {
		return i18n;
	}
	public void setI18n(int i18n) {
		this.i18n = i18n;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	
}
