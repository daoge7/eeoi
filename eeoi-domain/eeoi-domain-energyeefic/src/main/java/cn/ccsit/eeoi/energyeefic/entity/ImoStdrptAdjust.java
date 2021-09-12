package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "IMO_STDRPT_ADJUST", catalog = "")
public class ImoStdrptAdjust {
    private String id;
    private String rptId;
    private String refCode;
    private String rptSource;
    private String imono;
    private BigDecimal distOnsea;
    private BigDecimal hourOnsea;
    private String creator;
    private Date createDate;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    private List<ImoCorrectionOil> imoCorrectionOils;

    @OneToMany(mappedBy = "rptCorrectionId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<ImoCorrectionOil> getImoCorrectionOils() {
        return imoCorrectionOils;
    }
    public void setImoCorrectionOils(List<ImoCorrectionOil> imoCorrectionOils) {
        this.imoCorrectionOils = imoCorrectionOils;
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
        ImoStdrptAdjust that = (ImoStdrptAdjust) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(rptId, that.rptId) &&
                Objects.equals(refCode, that.refCode) &&
                Objects.equals(rptSource, that.rptSource) &&
                Objects.equals(imono, that.imono) &&
                Objects.equals(distOnsea, that.distOnsea) &&
                Objects.equals(hourOnsea, that.hourOnsea) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, rptId, refCode, rptSource, imono, distOnsea, hourOnsea, creator, createDate, opuser, opdate, isDelete);
    }
}
