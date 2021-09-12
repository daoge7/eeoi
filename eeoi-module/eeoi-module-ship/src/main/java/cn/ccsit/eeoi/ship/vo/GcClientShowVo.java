package cn.ccsit.eeoi.ship.vo;

import cn.ccsit.eeoi.ship.entity.GcClient;

import java.math.BigDecimal;
import java.util.Date;

public class GcClientShowVo {
	private String id;
	private String ccsCode;
	private String imoid;
	private String nameCn;
	private String nameEn;
	private String parentCompany;
	private String companyType;
	private String registerAdressCn;
	private String registerAdressEn;
	private String officeAdressCn;
	private String officeAdressEn;
	private String contactPerson;
	private String contactTel;
	private String contactEmail;
	private String businessScope;
	private String businessLine;
	private String certificateNo;
	private Date certificateDate;
	private String remark;
	private String iacs;
	private String code;

	public String getIacs() {
		return iacs;
	}

	public void setIacs(String iacs) {
		this.iacs = iacs;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

	public String getImoid() {
		return imoid;
	}

	public void setImoid(String imoid) {
		this.imoid = imoid;
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

	public String getParentCompany() {
		return parentCompany;
	}

	public void setParentCompany(String parentCompany) {
		this.parentCompany = parentCompany;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getRegisterAdressCn() {
		return registerAdressCn;
	}

	public void setRegisterAdressCn(String registerAdressCn) {
		this.registerAdressCn = registerAdressCn;
	}

	public String getRegisterAdressEn() {
		return registerAdressEn;
	}

	public void setRegisterAdressEn(String registerAdressEn) {
		this.registerAdressEn = registerAdressEn;
	}

	public String getOfficeAdressCn() {
		return officeAdressCn;
	}

	public void setOfficeAdressCn(String officeAdressCn) {
		this.officeAdressCn = officeAdressCn;
	}

	public String getOfficeAdressEn() {
		return officeAdressEn;
	}

	public void setOfficeAdressEn(String officeAdressEn) {
		this.officeAdressEn = officeAdressEn;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getBusinessLine() {
		return businessLine;
	}

	public void setBusinessLine(String businessLine) {
		this.businessLine = businessLine;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public Date getCertificateDate() {
		return certificateDate;
	}

	public void setCertificateDate(Date certificateDate) {
		this.certificateDate = certificateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public GcClientShowVo() {
	}
	public GcClientShowVo(GcClient gcClient) {
		this.id=gcClient.getId();
		this.ccsCode=gcClient.getCUid();
		this.imoid=gcClient.getIacs();
		this.nameCn=gcClient.getNameCn();
		this.nameEn=gcClient.getNameEn();
		this.parentCompany=gcClient.getGid();
		this.companyType=gcClient.getNature();
		this.registerAdressCn=gcClient.getRegistration();
		this.registerAdressEn=gcClient.getAddrEn();
		this.officeAdressCn=gcClient.getAddrCn();
		this.officeAdressEn=gcClient.getAddrEn();
		this.contactPerson=gcClient.getContact();
		this.contactTel=gcClient.getContactTel();
		this.contactEmail=gcClient.getContactEmail();
		this.businessScope=gcClient.getAbout();
		this.businessLine=gcClient.getMainRoute();
		this.certificateNo=gcClient.getCertNo();
		this.certificateDate=gcClient.getCertDate();
		this.remark=gcClient.getRemarkInfo();
		this.iacs=gcClient.getIacs();
		this.code=gcClient.getCode();
	}

}
