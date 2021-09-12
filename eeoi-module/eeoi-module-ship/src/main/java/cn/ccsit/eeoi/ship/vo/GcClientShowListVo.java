package cn.ccsit.eeoi.ship.vo;

import cn.ccsit.eeoi.ship.entity.GcClient;

public class GcClientShowListVo {

	//主键
	private String id;
	//ccs客户编号
	private String ccsCode;
	//imo id
	private String iacs;
	//公司中文名称
	private String nameCn;
	//公司英文名称
	private String nameEn;
	//公司中文地址
	private String adressCn;
	//公司英文地址
	private String adressEn;
	//主营业务
	private String business;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCcsCode() {
		return ccsCode;
	}
	public void setCcsCode(String ccsCode) {
		this.ccsCode = ccsCode;
	}
	public String getIacs() {
		return iacs;
	}
	public void setIacs(String iacs) {
		this.iacs = iacs;
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
	public String getAdressCn() {
		return adressCn;
	}
	public void setAdressCn(String adressCn) {
		this.adressCn = adressCn;
	}
	public String getAdressEn() {
		return adressEn;
	}
	public void setAdressEn(String adressEn) {
		this.adressEn = adressEn;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public GcClientShowListVo() {

	}

	public GcClientShowListVo(GcClient item) {
		this.id=item.getId();
		this.ccsCode=item.getCode();
		this.adressCn=item.getAddrCn();
		this.adressEn=item.getAddrEn();
		this.business=item.getBusiness();
		this.iacs=item.getIacs();
		this.nameCn=item.getNameCn();
		this.nameEn=item.getNameEn();

	}
}
