package cn.ccsit.eeoi.ship.vo;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.common.vo.page.PageVo;
import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.ship.entity.GcClientCate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GcClientVo {
    /**
     * id :
     * ccsCode :
     * nameCn :
     * nameEn :
     * parentCompanyId :
     * companyTypeIds :
     * registerAdressCn :
     * registerAdressEn :
     * officeAdressCn :
     * officeAdressEn :
     * contactPerson :
     * contactEmail :
     * businessScope :
     * businessLine :
     * contactTel :
     * certificateNo :
     * certificateDate :
     * remark :
     * iacs :
     */

    private String id;
    private String ccsCode;
    private String nameCn;
    private String nameEn;
    private List<String> childrenCompanyIds;
    private String childrenCompanyNames;
    private String companyType;
    private List<String> companyTypeIds;
    private String registerAdressCn;
    private String registerAdressEn;
    private String officeAdressCn;
    private String officeAdressEn;
    private String contactPerson;
    private String contactEmail;
    private String businessScope;
    private String businessLine;
    private String contactTel;
    private String certificateNo;
    private Date certificateDate;
    private String remark;
    private String iacs;

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

    public String getNameCn() {
        return nameCn;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
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

    public List<String> getChildrenCompanyIds() {
        return childrenCompanyIds;
    }

    public void setChildrenCompanyIds(List<String> childrenCompanyIds) {
        this.childrenCompanyIds = childrenCompanyIds;
    }

    public List<String> getCompanyTypeIds() {
        return companyTypeIds;
    }

    public void setCompanyTypeIds(List<String> companyTypeIds) {
        this.companyTypeIds = companyTypeIds;
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

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
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

    public String getIacs() {
        return iacs;
    }

    public void setIacs(String iacs) {
        this.iacs = iacs;
    }

    public String getChildrenCompanyNames() {
        return childrenCompanyNames;
    }

    public void setChildrenCompanyNames(String childrenCompanyNames) {
        this.childrenCompanyNames = childrenCompanyNames;
    }

    public GcClientVo(GcClient gcClient) {
        this.id = gcClient.getId();
        this.ccsCode = gcClient.getCode();
        this.nameCn = gcClient.getNameCn();
        this.nameEn = gcClient.getNameEn();

        //把gcClient中的List的id提取出来，转化为一个新的List
        List<String> ids = gcClient.getChildren().stream().map(GcClient::getId).collect(Collectors.toList());
        this.childrenCompanyIds = ids.size() > 0 ? null : ids;
        childrenCompanyNames = "";
        List<GcClient> gcClients = gcClient.getChildren();
        for (int i = 0; i < gcClients.size(); i++) {
            int index = i + 1;
            childrenCompanyNames = childrenCompanyNames + index + ":" + gcClients.get(i).getNameCn() + ";\n";
        }
        this.registerAdressCn = gcClient.getAddrCn();
        this.registerAdressEn = gcClient.getAddrEn();
        this.officeAdressCn = gcClient.getAddrCnNew();
        this.officeAdressEn = gcClient.getAddrEnNew();
        this.contactPerson = gcClient.getContact();
        this.contactEmail = gcClient.getContactEmail();
        this.businessScope = gcClient.getBusiness();
        this.businessLine = gcClient.getMainRoute();
        this.contactTel = gcClient.getContactTel();
        this.certificateNo = gcClient.getCertNo();
        this.certificateDate = gcClient.getCertDate();
        this.remark = gcClient.getRemarkInfo();
        this.iacs = gcClient.getIacs();
        if(gcClient.getGcClientCateRelas()!=null && gcClient.getGcClientCateRelas().size()>0) {
            this.companyType = gcClient.getGcClientCateRelas().get(0).getGcClientCate().getId();
        }
    }

    public GcClientVo() {
    }
}
