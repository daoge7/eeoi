package cn.ccsit.eeoi.energyeefic.entity;

import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CMSA_RPT", catalog = "")
public class CmsaRpt {
    private String id;
    private String refCode;
    private String rptSource;
    private String imono;
    private String shipName;
    private String rptType;
    private Date startTm;
    private Date endTm;
    private String rptOrg;
    private String verifierName;
    private String countryFlag;
    private String docIacs;
    private String docName;
    private String spCate;
    private String spType;
    private BigDecimal grossTons;
    private BigDecimal netTons;
    private BigDecimal dwTons;
    private Integer teu;
    private BigDecimal eediVal;
    private String iceClass;
    private BigDecimal designSpeed;
    private BigDecimal mePower;
    private BigDecimal gePower;
    private BigDecimal boPower;
    private BigDecimal tonsNm;
    private BigDecimal teuNms;
    private BigDecimal pesNms;
    private BigDecimal distOnsea;
    private BigDecimal hourOnsea;
    private BigDecimal hourOpera;
    private BigDecimal landPowerKwh;
    private String captain;
    private String statPerson;
    private String fillPerson;
    private String contactPh;
    private String recStatus;
    private String creator;
    private Date createDate;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    private String shipId;
    private String taskId;

    @Basic
    @Column(name = "TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    private OiShipInfo oiShipInfo;
    private List<CmsaRptOilCons> cmsaRptOilCons;

    @OneToMany(mappedBy = "rptId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<CmsaRptOilCons> getCmsaRptOilCons() {
        return cmsaRptOilCons;
    }

    public void setCmsaRptOilCons(List<CmsaRptOilCons> cmsaRptOilCons) {
        this.cmsaRptOilCons = cmsaRptOilCons;
    }


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SHIP_ID", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
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
    @Column(name = "REF_CODE")
    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
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
    @Column(name = "IMONO")
    public String getImono() {
        return imono;
    }

    public void setImono(String imono) {
        this.imono = imono;
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
    @Column(name = "RPT_TYPE")
    public String getRptType() {
        return rptType;
    }

    public void setRptType(String rptType) {
        this.rptType = rptType;
    }

    @Basic
    @Column(name = "START_TM")
    public Date getStartTm() {
        return startTm;
    }

    public void setStartTm(Date startTm) {
        this.startTm = startTm;
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
    @Column(name = "RPT_ORG")
    public String getRptOrg() {
        return rptOrg;
    }

    public void setRptOrg(String rptOrg) {
        this.rptOrg = rptOrg;
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
    @Column(name = "COUNTRY_FLAG")
    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    @Basic
    @Column(name = "DOC_IACS")
    public String getDocIacs() {
        return docIacs;
    }

    public void setDocIacs(String docIacs) {
        this.docIacs = docIacs;
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
    @Column(name = "SP_CATE")
    public String getSpCate() {
        return spCate;
    }

    public void setSpCate(String spCate) {
        this.spCate = spCate;
    }

    @Basic
    @Column(name = "SP_TYPE")
    public String getSpType() {
        return spType;
    }

    public void setSpType(String spType) {
        this.spType = spType;
    }

    @Basic
    @Column(name = "GROSS_TONS")
    public BigDecimal getGrossTons() {
        return grossTons;
    }

    public void setGrossTons(BigDecimal grossTons) {
        this.grossTons = grossTons;
    }

    @Basic
    @Column(name = "NET_TONS")
    public BigDecimal getNetTons() {
        return netTons;
    }

    public void setNetTons(BigDecimal netTons) {
        this.netTons = netTons;
    }

    @Basic
    @Column(name = "DW_TONS")
    public BigDecimal getDwTons() {
        return dwTons;
    }

    public void setDwTons(BigDecimal dwTons) {
        this.dwTons = dwTons;
    }

    @Basic
    @Column(name = "TEU")
    public Integer getTeu() {
        return teu;
    }

    public void setTeu(Integer teu) {
        this.teu = teu;
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
    @Column(name = "ICE_CLASS")
    public String getIceClass() {
        return iceClass;
    }

    public void setIceClass(String iceClass) {
        this.iceClass = iceClass;
    }

    @Basic
    @Column(name = "DESIGN_SPEED")
    public BigDecimal getDesignSpeed() {
        return designSpeed;
    }

    public void setDesignSpeed(BigDecimal designSpeed) {
        this.designSpeed = designSpeed;
    }

    @Basic
    @Column(name = "ME_POWER")
    public BigDecimal getMePower() {
        return mePower;
    }

    public void setMePower(BigDecimal mePower) {
        this.mePower = mePower;
    }

    @Basic
    @Column(name = "GE_POWER")
    public BigDecimal getGePower() {
        return gePower;
    }

    public void setGePower(BigDecimal gePower) {
        this.gePower = gePower;
    }

    @Basic
    @Column(name = "BO_POWER")
    public BigDecimal getBoPower() {
        return boPower;
    }

    public void setBoPower(BigDecimal boPower) {
        this.boPower = boPower;
    }

    @Basic
    @Column(name = "TONS_NM")
    public BigDecimal getTonsNm() {
        return tonsNm;
    }

    public void setTonsNm(BigDecimal tonsNm) {
        this.tonsNm = tonsNm;
    }

    @Basic
    @Column(name = "TEU_NMS")
    public BigDecimal getTeuNms() {
        return teuNms;
    }

    public void setTeuNms(BigDecimal teuNms) {
        this.teuNms = teuNms;
    }

    @Basic
    @Column(name = "PES_NMS")
    public BigDecimal getPesNms() {
        return pesNms;
    }

    public void setPesNms(BigDecimal pesNms) {
        this.pesNms = pesNms;
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
    @Column(name = "HOUR_ONSEA")
    public BigDecimal getHourOnsea() {
        return hourOnsea;
    }

    public void setHourOnsea(BigDecimal hourOnsea) {
        this.hourOnsea = hourOnsea;
    }

    @Basic
    @Column(name = "HOUR_OPERA")
    public BigDecimal getHourOpera() {
        return hourOpera;
    }

    public void setHourOpera(BigDecimal hourOpera) {
        this.hourOpera = hourOpera;
    }

    @Basic
    @Column(name = "LAND_POWER_KWH")
    public BigDecimal getLandPowerKwh() {
        return landPowerKwh;
    }

    public void setLandPowerKwh(BigDecimal landPowerKwh) {
        this.landPowerKwh = landPowerKwh;
    }

    @Basic
    @Column(name = "CAPTAIN")
    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    @Basic
    @Column(name = "STAT_PERSON")
    public String getStatPerson() {
        return statPerson;
    }

    public void setStatPerson(String statPerson) {
        this.statPerson = statPerson;
    }

    @Basic
    @Column(name = "FILL_PERSON")
    public String getFillPerson() {
        return fillPerson;
    }

    public void setFillPerson(String fillPerson) {
        this.fillPerson = fillPerson;
    }

    @Basic
    @Column(name = "CONTACT_PH")
    public String getContactPh() {
        return contactPh;
    }

    public void setContactPh(String contactPh) {
        this.contactPh = contactPh;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CmsaRpt cmsaRpt = (CmsaRpt) o;
        return Objects.equals(id, cmsaRpt.id) &&
                Objects.equals(refCode, cmsaRpt.refCode) &&
                Objects.equals(rptSource, cmsaRpt.rptSource) &&
                Objects.equals(imono, cmsaRpt.imono) &&
                Objects.equals(shipName, cmsaRpt.shipName) &&
                Objects.equals(rptType, cmsaRpt.rptType) &&
                Objects.equals(startTm, cmsaRpt.startTm) &&
                Objects.equals(endTm, cmsaRpt.endTm) &&
                Objects.equals(rptOrg, cmsaRpt.rptOrg) &&
                Objects.equals(verifierName, cmsaRpt.verifierName) &&
                Objects.equals(countryFlag, cmsaRpt.countryFlag) &&
                Objects.equals(docIacs, cmsaRpt.docIacs) &&
                Objects.equals(docName, cmsaRpt.docName) &&
                Objects.equals(spCate, cmsaRpt.spCate) &&
                Objects.equals(spType, cmsaRpt.spType) &&
                Objects.equals(grossTons, cmsaRpt.grossTons) &&
                Objects.equals(netTons, cmsaRpt.netTons) &&
                Objects.equals(dwTons, cmsaRpt.dwTons) &&
                Objects.equals(teu, cmsaRpt.teu) &&
                Objects.equals(eediVal, cmsaRpt.eediVal) &&
                Objects.equals(iceClass, cmsaRpt.iceClass) &&
                Objects.equals(designSpeed, cmsaRpt.designSpeed) &&
                Objects.equals(mePower, cmsaRpt.mePower) &&
                Objects.equals(gePower, cmsaRpt.gePower) &&
                Objects.equals(boPower, cmsaRpt.boPower) &&
                Objects.equals(tonsNm, cmsaRpt.tonsNm) &&
                Objects.equals(teuNms, cmsaRpt.teuNms) &&
                Objects.equals(pesNms, cmsaRpt.pesNms) &&
                Objects.equals(distOnsea, cmsaRpt.distOnsea) &&
                Objects.equals(hourOnsea, cmsaRpt.hourOnsea) &&
                Objects.equals(hourOpera, cmsaRpt.hourOpera) &&
                Objects.equals(landPowerKwh, cmsaRpt.landPowerKwh) &&
                Objects.equals(captain, cmsaRpt.captain) &&
                Objects.equals(statPerson, cmsaRpt.statPerson) &&
                Objects.equals(fillPerson, cmsaRpt.fillPerson) &&
                Objects.equals(contactPh, cmsaRpt.contactPh) &&
                Objects.equals(recStatus, cmsaRpt.recStatus) &&
                Objects.equals(creator, cmsaRpt.creator) &&
                Objects.equals(createDate, cmsaRpt.createDate) &&
                Objects.equals(opuser, cmsaRpt.opuser) &&
                Objects.equals(opdate, cmsaRpt.opdate) &&
                Objects.equals(isDelete, cmsaRpt.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, refCode, rptSource, imono, shipName, rptType, startTm, endTm, rptOrg, verifierName, countryFlag, docIacs, docName, spCate, spType, grossTons, netTons, dwTons, teu, eediVal, iceClass, designSpeed, mePower, gePower, boPower, tonsNm, teuNms, pesNms, distOnsea, hourOnsea, hourOpera, landPowerKwh, captain, statPerson, fillPerson, contactPh, recStatus, creator, createDate, opuser, opdate, isDelete);
    }
}
