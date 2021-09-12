package cn.ccsit.eeoi.energyeefic.entity;

import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "EU_EMISSION_RPT",  catalog = "")
public class EuEmissionRpt {
    private String id;
    private String imono;
    private String shipId;
    private OiShipInfo oiShipInfo;
    private Date startTm;
    private Date endTm;
    private String rptSource;
    private String shipName;
    private String homePortCode;
    private String homePortName;
    private String countryFlag;
    private String spTypeCode;
    private String iceClass;
    private BigDecimal eediVal;
    private String ownerName;
    private String ownerRegAddr;
    private String docName;
    private String ownerAddr;
    private String docRegAddr;
    private String docAddr;
    private String mrvpTitle;
    private String mrvpFname;
    private String mrvpSname;
    private String mrvpJobtitle;
    private String mrvpTele;
    private String mrvpEmail;
    private String verifierName;
    private String verifierAddr;
    private String accrNum;
    private String verifierState;
    private String emmSource;
    private String emmMeMethod;
    private String emmMeUncer;
    private String emmGeMethod;
    private String emmGeUncer;
    private String emmBoMethod2;
    private String emmBoUncer2;
    private BigDecimal hfoTons;
    private BigDecimal lfoTons;
    private BigDecimal dgoTons;
    private BigDecimal euCo2Tons;
    private BigDecimal euInnerCo2Tons;
    private BigDecimal euToCo2Tons;
    private BigDecimal euFrCo2Tons;
    private BigDecimal euBerthCo2Tons;
    private String euLadenCons;
    private String euCargoheatCons;
    private BigDecimal distOnsea;
    private BigDecimal distIce;
    private BigDecimal hourOnsea;
    private BigDecimal hourIce;
    private BigDecimal transportWork;
    private BigDecimal fuelConsPerMile;
    private BigDecimal fuelConsPerWork;
    private BigDecimal co2PerMile2;
    private BigDecimal co2PerWork2;
    private String fileId;
    private String recStatus;
    private String creator;
    private Date createDate;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    private BigDecimal eivVal;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "IMONO")
    public String getImono() {
        return imono;
    }

    public void setImono(String imono) {
        this.imono = imono;
    }

    @Basic
    @Column(name = "START_TM")
    public Date getStartTm() {
        return startTm;
    }

    public void setStartTm(Date startTm) {
        this.startTm = startTm;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SHIP_ID", insertable = false, updatable = false)
    public OiShipInfo getOiShipInfo() {
        return oiShipInfo;
    }

    public void setOiShipInfo(OiShipInfo oiShipInfo) {
        this.oiShipInfo = oiShipInfo;
    }

    @Basic
    @Column(name = "SHIP_ID")
    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    @Basic
    @Column(name = "END_TM")
    public Date getEndTm() {
        return endTm;
    }

    public void setEndTm(Date endTm) {
        this.endTm = endTm;
    }

    @Basic
    @Column(name = "RPT_SOURCE")
    public String getRptSource() {
        return rptSource;
    }

    public void setRptSource(String rptSource) {
        this.rptSource = rptSource;
    }

    @Basic
    @Column(name = "SHIP_NAME")
    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    @Basic
    @Column(name = "HOME_PORT_CODE")
    public String getHomePortCode() {
        return homePortCode;
    }

    public void setHomePortCode(String homePortCode) {
        this.homePortCode = homePortCode;
    }

    @Basic
    @Column(name = "HOME_PORT_NAME")
    public String getHomePortName() {
        return homePortName;
    }

    public void setHomePortName(String homePortName) {
        this.homePortName = homePortName;
    }

    @Basic
    @Column(name = "COUNTRY_FLAG")
    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    @Basic
    @Column(name = "SP_TYPE_CODE")
    public String getSpTypeCode() {
        return spTypeCode;
    }

    public void setSpTypeCode(String spTypeCode) {
        this.spTypeCode = spTypeCode;
    }

    @Basic
    @Column(name = "ICE_CLASS")
    public String getIceClass() {
        return iceClass;
    }

    public void setIceClass(String iceClass) {
        this.iceClass = iceClass;
    }

    @Basic
    @Column(name = "EEDI_VAL")
    public BigDecimal getEediVal() {
        return eediVal;
    }

    public void setEediVal(BigDecimal eediVal) {
        this.eediVal = eediVal;
    }

    @Basic
    @Column(name = "OWNER_NAME")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Basic
    @Column(name = "OWNER_REG_ADDR")
    public String getOwnerRegAddr() {
        return ownerRegAddr;
    }

    public void setOwnerRegAddr(String ownerRegAddr) {
        this.ownerRegAddr = ownerRegAddr;
    }

    @Basic
    @Column(name = "DOC_NAME")
    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    @Basic
    @Column(name = "OWNER_ADDR")
    public String getOwnerAddr() {
        return ownerAddr;
    }

    public void setOwnerAddr(String ownerAddr) {
        this.ownerAddr = ownerAddr;
    }

    @Basic
    @Column(name = "DOC_REG_ADDR")
    public String getDocRegAddr() {
        return docRegAddr;
    }

    public void setDocRegAddr(String docRegAddr) {
        this.docRegAddr = docRegAddr;
    }

    @Basic
    @Column(name = "DOC_ADDR")
    public String getDocAddr() {
        return docAddr;
    }

    public void setDocAddr(String docAddr) {
        this.docAddr = docAddr;
    }

    @Basic
    @Column(name = "MRVP_TITLE")
    public String getMrvpTitle() {
        return mrvpTitle;
    }

    public void setMrvpTitle(String mrvpTitle) {
        this.mrvpTitle = mrvpTitle;
    }

    @Basic
    @Column(name = "MRVP_FNAME")
    public String getMrvpFname() {
        return mrvpFname;
    }

    public void setMrvpFname(String mrvpFname) {
        this.mrvpFname = mrvpFname;
    }

    @Basic
    @Column(name = "MRVP_SNAME")
    public String getMrvpSname() {
        return mrvpSname;
    }

    public void setMrvpSname(String mrvpSname) {
        this.mrvpSname = mrvpSname;
    }

    @Basic
    @Column(name = "MRVP_JOBTITLE")
    public String getMrvpJobtitle() {
        return mrvpJobtitle;
    }

    public void setMrvpJobtitle(String mrvpJobtitle) {
        this.mrvpJobtitle = mrvpJobtitle;
    }

    @Basic
    @Column(name = "MRVP_TELE")
    public String getMrvpTele() {
        return mrvpTele;
    }

    public void setMrvpTele(String mrvpTele) {
        this.mrvpTele = mrvpTele;
    }

    @Basic
    @Column(name = "MRVP_EMAIL")
    public String getMrvpEmail() {
        return mrvpEmail;
    }

    public void setMrvpEmail(String mrvpEmail) {
        this.mrvpEmail = mrvpEmail;
    }

    @Basic
    @Column(name = "VERIFIER_NAME")
    public String getVerifierName() {
        return verifierName;
    }

    public void setVerifierName(String verifierName) {
        this.verifierName = verifierName;
    }

    @Basic
    @Column(name = "VERIFIER_ADDR")
    public String getVerifierAddr() {
        return verifierAddr;
    }

    public void setVerifierAddr(String verifierAddr) {
        this.verifierAddr = verifierAddr;
    }

    @Basic
    @Column(name = "ACCR_NUM")
    public String getAccrNum() {
        return accrNum;
    }

    public void setAccrNum(String accrNum) {
        this.accrNum = accrNum;
    }

    @Basic
    @Column(name = "VERIFIER_STATE")
    public String getVerifierState() {
        return verifierState;
    }

    public void setVerifierState(String verifierState) {
        this.verifierState = verifierState;
    }

    @Basic
    @Column(name = "EMM_SOURCE")
    public String getEmmSource() {
        return emmSource;
    }

    public void setEmmSource(String emmSource) {
        this.emmSource = emmSource;
    }

    @Basic
    @Column(name = "EMM_ME_METHOD")
    public String getEmmMeMethod() {
        return emmMeMethod;
    }

    public void setEmmMeMethod(String emmMeMethod) {
        this.emmMeMethod = emmMeMethod;
    }

    @Basic
    @Column(name = "EMM_ME_UNCER")
    public String getEmmMeUncer() {
        return emmMeUncer;
    }

    public void setEmmMeUncer(String emmMeUncer) {
        this.emmMeUncer = emmMeUncer;
    }

    @Basic
    @Column(name = "EMM_GE_METHOD")
    public String getEmmGeMethod() {
        return emmGeMethod;
    }

    public void setEmmGeMethod(String emmGeMethod) {
        this.emmGeMethod = emmGeMethod;
    }

    @Basic
    @Column(name = "EMM_GE_UNCER")
    public String getEmmGeUncer() {
        return emmGeUncer;
    }

    public void setEmmGeUncer(String emmGeUncer) {
        this.emmGeUncer = emmGeUncer;
    }

    @Basic
    @Column(name = "EMM_BO_METHOD2")
    public String getEmmBoMethod2() {
        return emmBoMethod2;
    }

    public void setEmmBoMethod2(String emmBoMethod2) {
        this.emmBoMethod2 = emmBoMethod2;
    }

    @Basic
    @Column(name = "EMM_BO_UNCER2")
    public String getEmmBoUncer2() {
        return emmBoUncer2;
    }

    public void setEmmBoUncer2(String emmBoUncer2) {
        this.emmBoUncer2 = emmBoUncer2;
    }

    @Basic
    @Column(name = "HFO_TONS")
    public BigDecimal getHfoTons() {
        return hfoTons;
    }

    public void setHfoTons(BigDecimal hfoTons) {
        this.hfoTons = hfoTons;
    }

    @Basic
    @Column(name = "LFO_TONS")
    public BigDecimal getLfoTons() {
        return lfoTons;
    }

    public void setLfoTons(BigDecimal lfoTons) {
        this.lfoTons = lfoTons;
    }

    @Basic
    @Column(name = "DGO_TONS")
    public BigDecimal getDgoTons() {
        return dgoTons;
    }

    public void setDgoTons(BigDecimal dgoTons) {
        this.dgoTons = dgoTons;
    }

    @Basic
    @Column(name = "EU_CO2_TONS")
    public BigDecimal getEuCo2Tons() {
        return euCo2Tons;
    }

    public void setEuCo2Tons(BigDecimal euCo2Tons) {
        this.euCo2Tons = euCo2Tons;
    }

    @Basic
    @Column(name = "EU_INNER_CO2_TONS")
    public BigDecimal getEuInnerCo2Tons() {
        return euInnerCo2Tons;
    }

    public void setEuInnerCo2Tons(BigDecimal euInnerCo2Tons) {
        this.euInnerCo2Tons = euInnerCo2Tons;
    }

    @Basic
    @Column(name = "EU_TO_CO2_TONS")
    public BigDecimal getEuToCo2Tons() {
        return euToCo2Tons;
    }

    public void setEuToCo2Tons(BigDecimal euToCo2Tons) {
        this.euToCo2Tons = euToCo2Tons;
    }

    @Basic
    @Column(name = "EU_FR_CO2_TONS")
    public BigDecimal getEuFrCo2Tons() {
        return euFrCo2Tons;
    }

    public void setEuFrCo2Tons(BigDecimal euFrCo2Tons) {
        this.euFrCo2Tons = euFrCo2Tons;
    }

    @Basic
    @Column(name = "EU_BERTH_CO2_TONS")
    public BigDecimal getEuBerthCo2Tons() {
        return euBerthCo2Tons;
    }

    public void setEuBerthCo2Tons(BigDecimal euBerthCo2Tons) {
        this.euBerthCo2Tons = euBerthCo2Tons;
    }

    @Basic
    @Column(name = "EU_LADEN_CONS")
    public String getEuLadenCons() {
        return euLadenCons;
    }

    public void setEuLadenCons(String euLadenCons) {
        this.euLadenCons = euLadenCons;
    }

    @Basic
    @Column(name = "EU_CARGOHEAT_CONS")
    public String getEuCargoheatCons() {
        return euCargoheatCons;
    }

    public void setEuCargoheatCons(String euCargoheatCons) {
        this.euCargoheatCons = euCargoheatCons;
    }

    @Basic
    @Column(name = "DIST_ONSEA")
    public BigDecimal getDistOnsea() {
        return distOnsea;
    }

    public void setDistOnsea(BigDecimal distOnsea) {
        this.distOnsea = distOnsea;
    }

    @Basic
    @Column(name = "DIST_ICE")
    public BigDecimal getDistIce() {
        return distIce;
    }

    public void setDistIce(BigDecimal distIce) {
        this.distIce = distIce;
    }

    @Basic
    @Column(name = "HOUR_ONSEA")
    public BigDecimal getHourOnsea() {
        return hourOnsea;
    }

    public void setHourOnsea(BigDecimal hourOnsea) {
        this.hourOnsea = hourOnsea;
    }

    @Basic
    @Column(name = "HOUR_ICE")
    public BigDecimal getHourIce() {
        return hourIce;
    }

    public void setHourIce(BigDecimal hourIce) {
        this.hourIce = hourIce;
    }

    @Basic
    @Column(name = "TRANSPORT_WORK")
    public BigDecimal getTransportWork() {
        return transportWork;
    }

    public void setTransportWork(BigDecimal transportWork) {
        this.transportWork = transportWork;
    }

    @Basic
    @Column(name = "FUEL_CONS_PER_MILE")
    public BigDecimal getFuelConsPerMile() {
        return fuelConsPerMile;
    }

    public void setFuelConsPerMile(BigDecimal fuelConsPerMile) {
        this.fuelConsPerMile = fuelConsPerMile;
    }

    @Basic
    @Column(name = "FUEL_CONS_PER_WORK")
    public BigDecimal getFuelConsPerWork() {
        return fuelConsPerWork;
    }

    public void setFuelConsPerWork(BigDecimal fuelConsPerWork) {
        this.fuelConsPerWork = fuelConsPerWork;
    }

    @Basic
    @Column(name = "CO2_PER_MILE2")
    public BigDecimal getCo2PerMile2() {
        return co2PerMile2;
    }

    public void setCo2PerMile2(BigDecimal co2PerMile2) {
        this.co2PerMile2 = co2PerMile2;
    }

    @Basic
    @Column(name = "CO2_PER_WORK2")
    public BigDecimal getCo2PerWork2() {
        return co2PerWork2;
    }

    public void setCo2PerWork2(BigDecimal co2PerWork2) {
        this.co2PerWork2 = co2PerWork2;
    }

    @Basic
    @Column(name = "FILE_ID")
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "REC_STATUS")
    public String getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
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
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    @Basic
    @Column(name = "IS_DELETE")
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Basic
    @Column(name = "EIV_VAL")
    public BigDecimal getEivVal() {
        return eivVal;
    }

    public void setEivVal(BigDecimal eivVal) {
        this.eivVal = eivVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EuEmissionRpt that = (EuEmissionRpt) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(imono, that.imono) &&
                Objects.equals(startTm, that.startTm) &&
                Objects.equals(endTm, that.endTm) &&
                Objects.equals(rptSource, that.rptSource) &&
                Objects.equals(shipName, that.shipName) &&
                Objects.equals(homePortCode, that.homePortCode) &&
                Objects.equals(homePortName, that.homePortName) &&
                Objects.equals(countryFlag, that.countryFlag) &&
                Objects.equals(spTypeCode, that.spTypeCode) &&
                Objects.equals(iceClass, that.iceClass) &&
                Objects.equals(eediVal, that.eediVal) &&
                Objects.equals(ownerName, that.ownerName) &&
                Objects.equals(ownerRegAddr, that.ownerRegAddr) &&
                Objects.equals(docName, that.docName) &&
                Objects.equals(ownerAddr, that.ownerAddr) &&
                Objects.equals(docRegAddr, that.docRegAddr) &&
                Objects.equals(docAddr, that.docAddr) &&
                Objects.equals(mrvpTitle, that.mrvpTitle) &&
                Objects.equals(mrvpFname, that.mrvpFname) &&
                Objects.equals(mrvpSname, that.mrvpSname) &&
                Objects.equals(mrvpJobtitle, that.mrvpJobtitle) &&
                Objects.equals(mrvpTele, that.mrvpTele) &&
                Objects.equals(mrvpEmail, that.mrvpEmail) &&
                Objects.equals(verifierName, that.verifierName) &&
                Objects.equals(verifierAddr, that.verifierAddr) &&
                Objects.equals(accrNum, that.accrNum) &&
                Objects.equals(verifierState, that.verifierState) &&
                Objects.equals(emmSource, that.emmSource) &&
                Objects.equals(emmMeMethod, that.emmMeMethod) &&
                Objects.equals(emmMeUncer, that.emmMeUncer) &&
                Objects.equals(emmGeMethod, that.emmGeMethod) &&
                Objects.equals(emmGeUncer, that.emmGeUncer) &&
                Objects.equals(emmBoMethod2, that.emmBoMethod2) &&
                Objects.equals(emmBoUncer2, that.emmBoUncer2) &&
                Objects.equals(hfoTons, that.hfoTons) &&
                Objects.equals(lfoTons, that.lfoTons) &&
                Objects.equals(dgoTons, that.dgoTons) &&
                Objects.equals(euCo2Tons, that.euCo2Tons) &&
                Objects.equals(euInnerCo2Tons, that.euInnerCo2Tons) &&
                Objects.equals(euToCo2Tons, that.euToCo2Tons) &&
                Objects.equals(euFrCo2Tons, that.euFrCo2Tons) &&
                Objects.equals(euBerthCo2Tons, that.euBerthCo2Tons) &&
                Objects.equals(euLadenCons, that.euLadenCons) &&
                Objects.equals(euCargoheatCons, that.euCargoheatCons) &&
                Objects.equals(distOnsea, that.distOnsea) &&
                Objects.equals(distIce, that.distIce) &&
                Objects.equals(hourOnsea, that.hourOnsea) &&
                Objects.equals(hourIce, that.hourIce) &&
                Objects.equals(transportWork, that.transportWork) &&
                Objects.equals(fuelConsPerMile, that.fuelConsPerMile) &&
                Objects.equals(fuelConsPerWork, that.fuelConsPerWork) &&
                Objects.equals(co2PerMile2, that.co2PerMile2) &&
                Objects.equals(co2PerWork2, that.co2PerWork2) &&
                Objects.equals(fileId, that.fileId) &&
                Objects.equals(recStatus, that.recStatus) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete) &&
                Objects.equals(eivVal, that.eivVal);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, imono, startTm, endTm, rptSource, shipName, homePortCode, homePortName, countryFlag, spTypeCode, iceClass, eediVal, ownerName, ownerRegAddr, docName, ownerAddr, docRegAddr, docAddr, mrvpTitle, mrvpFname, mrvpSname, mrvpJobtitle, mrvpTele, mrvpEmail, verifierName, verifierAddr, accrNum, verifierState, emmSource, emmMeMethod, emmMeUncer, emmGeMethod, emmGeUncer, emmBoMethod2, emmBoUncer2, hfoTons, lfoTons, dgoTons, euCo2Tons, euInnerCo2Tons, euToCo2Tons, euFrCo2Tons, euBerthCo2Tons, euLadenCons, euCargoheatCons, distOnsea, distIce, hourOnsea, hourIce, transportWork, fuelConsPerMile, fuelConsPerWork, co2PerMile2, co2PerWork2, fileId, recStatus, creator, createDate, opuser, opdate, isDelete, eivVal);
    }
}
