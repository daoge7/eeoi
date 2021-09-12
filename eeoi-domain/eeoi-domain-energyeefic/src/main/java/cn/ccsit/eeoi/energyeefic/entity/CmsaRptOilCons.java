package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "CMSA_RPT_OIL_CONS",  catalog = "")
public class CmsaRptOilCons {
    private String id;
    private String rptId;
    private String imono;
    private String cmsaFuelId;
    private BigDecimal tons;
    private String usedMethod;
    private String recStatus;
    private String creator;
    private Date createDate;
    private String opuser;
    private Date opdate;
    private Integer isDelete;

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
    @Column(name = "CMSA_FUEL_ID")
    public String getCmsaFuelId() {
        return cmsaFuelId;
    }

    public void setCmsaFuelId(String cmsaFuelId) {
        this.cmsaFuelId = cmsaFuelId;
    }


    @Basic
    @Column(name = "TONS")
    public BigDecimal getTons() {
        return tons;
    }

    public void setTons(BigDecimal tons) {
        this.tons = tons;
    }

    @Basic
    @Column(name = "USED_METHOD")
    public String getUsedMethod() {
        return usedMethod;
    }

    public void setUsedMethod(String usedMethod) {
        this.usedMethod = usedMethod;
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
        CmsaRptOilCons that = (CmsaRptOilCons) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(rptId, that.rptId) &&
                Objects.equals(imono, that.imono) &&
                Objects.equals(cmsaFuelId, that.cmsaFuelId) &&
                Objects.equals(tons, that.tons) &&
                Objects.equals(usedMethod, that.usedMethod) &&
                Objects.equals(recStatus, that.recStatus) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, rptId, imono, cmsaFuelId, tons, usedMethod, recStatus, creator, createDate, opuser, opdate, isDelete);
    }
}
