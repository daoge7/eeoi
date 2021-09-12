package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "IMO_BDN_SUMMARY", catalog = "")
public class ImoBdnSummary {
    private String id;
    private String rptId;
    private String imono;
    private String shipName;
    private String countryFlag;
    private String docIcas;
    private String docName;
    private Integer recType;
    private Date startTm;
    private String recStatus;
    private String creator;
    private Date createDate;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    private List<BdnOil> bdnOils;
    @OneToMany(mappedBy = "bdnId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<BdnOil> getBdnOils() {
        return bdnOils;
    }

    public void setBdnOils(List<BdnOil> bdnOils) {
        this.bdnOils = bdnOils;
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
    @Column(name = "RPT_ID")
    public String getRptId() {
        return rptId;
    }

    public void setRptId(String rptId) {
        this.rptId = rptId;
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
    @Column(name = "DOC_ICAS")
    public String getDocIcas() {
        return docIcas;
    }

    public void setDocIcas(String docIcas) {
        this.docIcas = docIcas;
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
    @Column(name = "REC_TYPE")
    public Integer getRecType() {
        return recType;
    }

    public void setRecType(Integer recType) {
        this.recType = recType;
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
        ImoBdnSummary that = (ImoBdnSummary) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(rptId, that.rptId) &&
                Objects.equals(imono, that.imono) &&
                Objects.equals(shipName, that.shipName) &&
                Objects.equals(countryFlag, that.countryFlag) &&
                Objects.equals(docIcas, that.docIcas) &&
                Objects.equals(docName, that.docName) &&
                Objects.equals(recType, that.recType) &&
                Objects.equals(startTm, that.startTm) &&
                Objects.equals(recStatus, that.recStatus) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, rptId, imono, shipName, countryFlag, docIcas, docName, recType, startTm, recStatus, creator, createDate, opuser, opdate, isDelete);
    }
}
