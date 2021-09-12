package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "GC_CLIENT")
public class GcClient implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Date version;
    private Long state;
    private Long valid;
    private Date updateTime;
    private String updateUser;
    private String code;
    private String cUid;
    private GcClient parent;
    private List<GcClient> children;
    private Long cGroup;
    private String gid;
    private String iacs;
    private String nameCn;
    private String nameEn;
    private String cnBrief;
    private String enBrief;
    private String addrCn;
    private String addrEn;
    private String tel;
    private String fax;
    private String email;
    private String postcode;
    private String website;
    private String nation;
    private String province;
    private String city;
    private String contact;
    private String contactMobile;
    private String contactTel;
    private String contactFax;
    private String contactEmail;
    private String legal;
    private String licenseNo;
    private String license;
    private String registration;
    private Date issuedon;
    private String nature;
    private String reputation;
    private String constraints;
    private String business;
    private String overview;
    private String sizd;
    private String bank;
    private String account;
    private String remarkInfo;
    private Long manufacture;
    private Long applicant;
    private Long isOrder;
    private Long payment;
    private String nameCnNew;
    private String nameEnNew;
    private String addrCnNew;
    private String addrEnNew;
    private Date addTime;
    private String branch;
    private String positionX;
    private String positionY;
    private Long axcd;
    private String cityCode;
    private String doccode;
    private Long chinaBz;
    private String countryAddr;
    private String mainRoute;
    private String certNo;
    private Date certDate;
    private String certScope;
    private String about;
    private String creator;
    private Date createTm;
    private String opuser;
    private Date opdate;
    private Boolean isDelete;


//    private List<GcClientCate> gcClientCates;


    private List<GcClientCateRela> gcClientCateRelas;

    @Id
    @Column(name = "ID", length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Basic
    @Column(name = "STATE")
    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    @Basic
    @Column(name = "VALID")
    public Long getValid() {
        return valid;
    }

    public void setValid(Long valid) {
        this.valid = valid;
    }

    @Basic
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "UPDATE_USER")
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Basic
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "C_UID")
    public String getCUid() {
        return cUid;
    }

    public void setCUid(String cUid) {
        this.cUid = cUid;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cid", referencedColumnName = "ID")
    public GcClient getParent() {
        return parent;
    }

    public void setParent(GcClient parent) {
        this.parent = parent;
    }

    //    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.LAZY)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cid", referencedColumnName = "ID")
    public List<GcClient> getChildren() {
        return children;
    }

    public void setChildren(List<GcClient> children) {
        this.children = children;
    }

//    @ManyToMany(targetEntity = GcClientCate.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "GC_CLIENT_CATE_RELA",
//            joinColumns = {@JoinColumn(name = "CLIENT_ID")},
//            inverseJoinColumns = {@JoinColumn(name = "CATE_ID")})
//    public List<GcClientCate> getGcClientCates() {
//        return gcClientCates;
//    }

//    public void setGcClientCates(List<GcClientCate> gcClientCates) {
//        this.gcClientCates = gcClientCates;
//    }

    //    @Basic
//    @Column(name = "CID")
//    public String getCid() {
//        return cid;
//    }
//
//    public void setCid(String cid) {
//        this.cid = cid;
//    }

    @Basic
    @Column(name = "C_GROUP")
    public Long getCGroup() {
        return cGroup;
    }

    public void setCGroup(Long cGroup) {
        this.cGroup = cGroup;
    }

    @Basic
    @Column(name = "GID")
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    @Basic
    @Column(name = "IACS")
    public String getIacs() {
        return iacs;
    }

    public void setIacs(String iacs) {
        this.iacs = iacs;
    }

    @Basic
    @Column(name = "NAME_CN")
    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    @Basic
    @Column(name = "NAME_EN")
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @Basic
    @Column(name = "CN_BRIEF")
    public String getCnBrief() {
        return cnBrief;
    }

    public void setCnBrief(String cnBrief) {
        this.cnBrief = cnBrief;
    }

    @Basic
    @Column(name = "EN_BRIEF")
    public String getEnBrief() {
        return enBrief;
    }

    public void setEnBrief(String enBrief) {
        this.enBrief = enBrief;
    }

    @Basic
    @Column(name = "ADDR_CN")
    public String getAddrCn() {
        return addrCn;
    }

    public void setAddrCn(String addrCn) {
        this.addrCn = addrCn;
    }

    @Basic
    @Column(name = "ADDR_EN")
    public String getAddrEn() {
        return addrEn;
    }

    public void setAddrEn(String addrEn) {
        this.addrEn = addrEn;
    }

    @Basic
    @Column(name = "TEL")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "FAX")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "POSTCODE")
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Basic
    @Column(name = "WEBSITE")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Basic
    @Column(name = "NATION")
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Basic
    @Column(name = "PROVINCE")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "CONTACT")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "CONTACT_MOBILE")
    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    @Basic
    @Column(name = "CONTACT_TEL")
    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    @Basic
    @Column(name = "CONTACT_FAX")
    public String getContactFax() {
        return contactFax;
    }

    public void setContactFax(String contactFax) {
        this.contactFax = contactFax;
    }

    @Basic
    @Column(name = "CONTACT_EMAIL")
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Basic
    @Column(name = "LEGAL")
    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    @Basic
    @Column(name = "LICENSE_NO")
    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    @Basic
    @Column(name = "LICENSE")
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Basic
    @Column(name = "REGISTRATION")
    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    @Basic
    @Column(name = "ISSUEDON")
    public Date getIssuedon() {
        return issuedon;
    }

    public void setIssuedon(Date issuedon) {
        this.issuedon = issuedon;
    }

    @Basic
    @Column(name = "NATURE")
    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    @Basic
    @Column(name = "REPUTATION")
    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    @Basic
    @Column(name = "CONSTRAINTS")
    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    @Basic
    @Column(name = "BUSINESS")
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    @Basic
    @Column(name = "OVERVIEW")
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Basic
    @Column(name = "SIZD")
    public String getSizd() {
        return sizd;
    }

    public void setSizd(String sizd) {
        this.sizd = sizd;
    }

    @Basic
    @Column(name = "BANK")
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Basic
    @Column(name = "ACCOUNT")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "REMARK_INFO")
    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    @Basic
    @Column(name = "MANUFACTURE")
    public Long getManufacture() {
        return manufacture;
    }

    public void setManufacture(Long manufacture) {
        this.manufacture = manufacture;
    }

    @Basic
    @Column(name = "APPLICANT")
    public Long getApplicant() {
        return applicant;
    }

    public void setApplicant(Long applicant) {
        this.applicant = applicant;
    }

    @Basic
    @Column(name = "IS_ORDER")
    public Long getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(Long isOrder) {
        this.isOrder = isOrder;
    }

    @Basic
    @Column(name = "PAYMENT")
    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    @Basic
    @Column(name = "NAME_CN_NEW")
    public String getNameCnNew() {
        return nameCnNew;
    }

    public void setNameCnNew(String nameCnNew) {
        this.nameCnNew = nameCnNew;
    }

    @Basic
    @Column(name = "NAME_EN_NEW")
    public String getNameEnNew() {
        return nameEnNew;
    }

    public void setNameEnNew(String nameEnNew) {
        this.nameEnNew = nameEnNew;
    }

    @Basic
    @Column(name = "ADDR_CN_NEW")
    public String getAddrCnNew() {
        return addrCnNew;
    }

    public void setAddrCnNew(String addrCnNew) {
        this.addrCnNew = addrCnNew;
    }

    @Basic
    @Column(name = "ADDR_EN_NEW")
    public String getAddrEnNew() {
        return addrEnNew;
    }

    public void setAddrEnNew(String addrEnNew) {
        this.addrEnNew = addrEnNew;
    }

    @Basic
    @Column(name = "ADD_TIME")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Basic
    @Column(name = "BRANCH")
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Basic
    @Column(name = "POSITION_X")
    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    @Basic
    @Column(name = "POSITION_Y")
    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    @Basic
    @Column(name = "AXCD")
    public Long getAxcd() {
        return axcd;
    }

    public void setAxcd(Long axcd) {
        this.axcd = axcd;
    }

    @Basic
    @Column(name = "CITY_CODE")
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Basic
    @Column(name = "DOCCODE")
    public String getDoccode() {
        return doccode;
    }

    public void setDoccode(String doccode) {
        this.doccode = doccode;
    }

    @Basic
    @Column(name = "CHINA_BZ")
    public Long getChinaBz() {
        return chinaBz;
    }

    public void setChinaBz(Long chinaBz) {
        this.chinaBz = chinaBz;
    }

    @Basic
    @Column(name = "COUNTRY_ADDR")
    public String getCountryAddr() {
        return countryAddr;
    }

    public void setCountryAddr(String countryAddr) {
        this.countryAddr = countryAddr;
    }

    @Basic
    @Column(name = "MAIN_ROUTE")
    public String getMainRoute() {
        return mainRoute;
    }

    public void setMainRoute(String mainRoute) {
        this.mainRoute = mainRoute;
    }

    @Basic
    @Column(name = "CERT_NO")
    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    @Basic
    @Column(name = "CERT_DATE")
    public Date getCertDate() {
        return certDate;
    }

    public void setCertDate(Date certDate) {
        this.certDate = certDate;
    }

    @Basic
    @Column(name = "CERT_SCOPE")
    public String getCertScope() {
        return certScope;
    }

    public void setCertScope(String certScope) {
        this.certScope = certScope;
    }

    @Basic
    @Column(name = "ABOUT")
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Basic
    @Column(name = "CREATOR")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "CREATE_TM")
    public Date getCreateTm() {
        return createTm;
    }

    public void setCreateTm(Date createTm) {
        this.createTm = createTm;
    }

    @Basic
    @Column(name = "OPUSER")
    public String getOpuser() {
        return opuser;
    }

    public void setOpuser(String opuser) {
        this.opuser = opuser;
    }

    @Basic
    @Column(name = "OPDATE")
    public Date getOpdate() {
        return opdate;
    }

    public void setOpdate(Date opdate) {
        this.opdate = opdate;
    }

    @OneToMany(mappedBy = "gcClient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<GcClientCateRela> getGcClientCateRelas() {
        return gcClientCateRelas;
    }

    public void setGcClientCateRelas(List<GcClientCateRela> gcClientCateRelas) {
        this.gcClientCateRelas = gcClientCateRelas;
    }

    @Basic
    @Column(name = "IS_DELETE")
    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public GcClient() {
        this.gcClientCateRelas = new ArrayList<GcClientCateRela>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GcClient that = (GcClient) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(version, that.version) &&
                Objects.equals(state, that.state) &&
                Objects.equals(valid, that.valid) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(updateUser, that.updateUser) &&
                Objects.equals(code, that.code) &&
                Objects.equals(cUid, that.cUid) &&
//                Objects.equals(cid, that.cid) &&
                Objects.equals(cGroup, that.cGroup) &&
                Objects.equals(gid, that.gid) &&
                Objects.equals(iacs, that.iacs) &&
                Objects.equals(nameCn, that.nameCn) &&
                Objects.equals(nameEn, that.nameEn) &&
                Objects.equals(cnBrief, that.cnBrief) &&
                Objects.equals(enBrief, that.enBrief) &&
                Objects.equals(addrCn, that.addrCn) &&
                Objects.equals(addrEn, that.addrEn) &&
                Objects.equals(tel, that.tel) &&
                Objects.equals(fax, that.fax) &&
                Objects.equals(email, that.email) &&
                Objects.equals(postcode, that.postcode) &&
                Objects.equals(website, that.website) &&
                Objects.equals(nation, that.nation) &&
                Objects.equals(province, that.province) &&
                Objects.equals(city, that.city) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(contactMobile, that.contactMobile) &&
                Objects.equals(contactTel, that.contactTel) &&
                Objects.equals(contactFax, that.contactFax) &&
                Objects.equals(contactEmail, that.contactEmail) &&
                Objects.equals(legal, that.legal) &&
                Objects.equals(licenseNo, that.licenseNo) &&
                Objects.equals(license, that.license) &&
                Objects.equals(registration, that.registration) &&
                Objects.equals(issuedon, that.issuedon) &&
                Objects.equals(nature, that.nature) &&
                Objects.equals(reputation, that.reputation) &&
                Objects.equals(constraints, that.constraints) &&
                Objects.equals(business, that.business) &&
                Objects.equals(overview, that.overview) &&
                Objects.equals(sizd, that.sizd) &&
                Objects.equals(bank, that.bank) &&
                Objects.equals(account, that.account) &&
                Objects.equals(remarkInfo, that.remarkInfo) &&
                Objects.equals(manufacture, that.manufacture) &&
                Objects.equals(applicant, that.applicant) &&
                Objects.equals(isOrder, that.isOrder) &&
                Objects.equals(payment, that.payment) &&
                Objects.equals(nameCnNew, that.nameCnNew) &&
                Objects.equals(nameEnNew, that.nameEnNew) &&
                Objects.equals(addrCnNew, that.addrCnNew) &&
                Objects.equals(addrEnNew, that.addrEnNew) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(branch, that.branch) &&
                Objects.equals(positionX, that.positionX) &&
                Objects.equals(positionY, that.positionY) &&
                Objects.equals(axcd, that.axcd) &&
                Objects.equals(cityCode, that.cityCode) &&
                Objects.equals(doccode, that.doccode) &&
                Objects.equals(chinaBz, that.chinaBz) &&
                Objects.equals(countryAddr, that.countryAddr) &&
                Objects.equals(mainRoute, that.mainRoute) &&
                Objects.equals(certNo, that.certNo) &&
                Objects.equals(certDate, that.certDate) &&
                Objects.equals(certScope, that.certScope) &&
                Objects.equals(about, that.about) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

}
