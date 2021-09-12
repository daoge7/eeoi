package cn.ccsit.eeoi.energyeefic.entity;

import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "IMO_STDRPT",  catalog = "")
public class ImoStdrpt {
    private String id;
    private String refCode;
    private String rptSource;
    private String shipId;
    private String imono;
    private String shipName;
    private String countryFlag;
    private String docIacs;
    private String docName;
    private String bdnFileId;
    private String cdFileId;
    private Date startTm;
    private Date endTm;
    private String spType;
    private BigDecimal grossTons;
    private BigDecimal netTons;
    private BigDecimal dwTons;
    private BigDecimal eediVal;
    private String iceClass;
    private String mePower;
    private String gePower;
    private BigDecimal distOnsea;
    private BigDecimal hourOnsea;
    private String fileId;
    private String recStatus;
    private String creator;
    private Date createDate;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    private List<ImoDataSummary> imoDataSummaries;
    private List<ImoBdnSummary> imoBdnSummaries;

    @OneToMany(mappedBy = "rptId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<ImoDataSummary> getImoDataSummaries() {
        return imoDataSummaries;
    }

    public void setImoDataSummaries(List<ImoDataSummary> imoDataSummaries) {
        this.imoDataSummaries = imoDataSummaries;
    }

    @OneToMany(mappedBy = "rptId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<ImoBdnSummary> getImoBdnSummaries() {
        return imoBdnSummaries;
    }

    public void setImoBdnSummaries(List<ImoBdnSummary> imoBdnSummaries) {
        this.imoBdnSummaries = imoBdnSummaries;
    }

//    private ImoStdrptAdjust imoStdrptAdjust;
    private List<ImoOil> imoOils;
    private OiShipInfo oiShipInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SHIP_ID", insertable = false, updatable = false)
    @Where(clause = "id is not null")
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

    @OneToMany(mappedBy = "rptId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<ImoOil> getImoOils() {
        return imoOils;
    }

    public void setImoOils(List<ImoOil> imoOils) {
        this.imoOils = imoOils;
    }

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "ID", referencedColumnName = "RPT_ID", insertable = false, updatable = false)
//    public ImoStdrptAdjust getImoStdrptAdjust() {
//        return imoStdrptAdjust;
//    }
//
//    public void setImoStdrptAdjust(ImoStdrptAdjust imoStdrptAdjust) {
//        this.imoStdrptAdjust = imoStdrptAdjust;
//    }

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
    @Column(name = "START_TM")
    public Date getStartTm() {
        return startTm;
    }

    public void setStartTm(Date startTm) {
        this.startTm = startTm;
    }

    @Basic
    @Column(name = "BDN_FILE_ID")
    public String getBdnFileId() {
        return bdnFileId;
    }

    public void setBdnFileId(String bdnFileId) {
        this.bdnFileId = bdnFileId;
    }

    @Basic
    @Column(name = "CD_FILE_ID")
    public String getCdFileId() {
        return cdFileId;
    }

    public void setCdFileId(String cdFileId) {
        this.cdFileId = cdFileId;
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
        // imo报告 冰级如果为 0 或者null，建议升级的时候 直接改成N/A
        if(iceClass == null || iceClass.equals("0") || iceClass.equals("")){
            return "N/A";
        }
        return iceClass;
    }

    public void setIceClass(String iceClass) {
        this.iceClass = iceClass;
    }

    @Basic
    @Column(name = "ME_POWER")
    public String getMePower() {
        return mePower;
    }

    public void setMePower(String mePower) {
        this.mePower = mePower;
    }

    @Basic
    @Column(name = "GE_POWER")
    public String getGePower() {
        return gePower;
    }

    public void setGePower(String gePower) {
        this.gePower = gePower;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImoStdrpt imoStdrpt = (ImoStdrpt) o;
        return Objects.equals(id, imoStdrpt.id) &&
                Objects.equals(refCode, imoStdrpt.refCode) &&
                Objects.equals(rptSource, imoStdrpt.rptSource) &&
                Objects.equals(imono, imoStdrpt.imono) &&
                Objects.equals(shipName, imoStdrpt.shipName) &&
                Objects.equals(countryFlag, imoStdrpt.countryFlag) &&
                Objects.equals(docIacs, imoStdrpt.docIacs) &&
                Objects.equals(docName, imoStdrpt.docName) &&
                Objects.equals(startTm, imoStdrpt.startTm) &&
                Objects.equals(endTm, imoStdrpt.endTm) &&
                Objects.equals(spType, imoStdrpt.spType) &&
                Objects.equals(grossTons, imoStdrpt.grossTons) &&
                Objects.equals(netTons, imoStdrpt.netTons) &&
                Objects.equals(dwTons, imoStdrpt.dwTons) &&
                Objects.equals(eediVal, imoStdrpt.eediVal) &&
                Objects.equals(iceClass, imoStdrpt.iceClass) &&
                Objects.equals(mePower, imoStdrpt.mePower) &&
                Objects.equals(gePower, imoStdrpt.gePower) &&
                Objects.equals(distOnsea, imoStdrpt.distOnsea) &&
                Objects.equals(hourOnsea, imoStdrpt.hourOnsea) &&
                Objects.equals(fileId, imoStdrpt.fileId) &&
                Objects.equals(recStatus, imoStdrpt.recStatus) &&
                Objects.equals(creator, imoStdrpt.creator) &&
                Objects.equals(createDate, imoStdrpt.createDate) &&
                Objects.equals(opuser, imoStdrpt.opuser) &&
                Objects.equals(opdate, imoStdrpt.opdate) &&
                Objects.equals(isDelete, imoStdrpt.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, refCode, rptSource, imono, shipName, countryFlag, docIacs, docName, startTm, endTm, spType, grossTons, netTons, dwTons, eediVal, iceClass, mePower, gePower, distOnsea, hourOnsea, fileId, recStatus, creator, createDate, opuser, opdate, isDelete);
    }
}
