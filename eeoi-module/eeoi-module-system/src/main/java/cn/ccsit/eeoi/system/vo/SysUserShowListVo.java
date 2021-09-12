package cn.ccsit.eeoi.system.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.system.entity.SysUser;

public class SysUserShowListVo {

	
	
	public SysUserShowListVo() {

	}
	
	
	
	
	public SysUserShowListVo(SysUser item) {

		this.setId(item.getId());
		this.setAccount(item.getUsername());
		this.setNameCn(item.getNickname());
		this.setNameEn(item.getNicknameEn());
		this.setUserStatus(item.getStatus());
		if (item.getOpdate() != null) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.opdate = formatter.format(item.getOpdate());
	}
		List<String> companyNames=new ArrayList<String>();
		for( GcClient company :item.getCompanys()) {
			String companyName=company.getNameCn()+"("+company.getNameEn() +")";
			companyNames.add(companyName);
		}
		this.companyName=String.join(" | ", companyNames);
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

	public String getOpdate() {
		return opdate;
	}

	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
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

	private String id;

	private String account;

	private String opdate;

	private int userStatus;

	private String nameCn;

	private String nameEn;
	
	private String companyName;


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
