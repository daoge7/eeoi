package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "IMO_CORRECTION_OIL", catalog = "")
public class ImoCorrectionOil {
    private String id;
    private String rptCorrectionId;
    private String oilId;
    private String oilName;
    private BigDecimal fuelTons;
    private String recStatus;
    private String creator;
    private Date createTm;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    public ImoCorrectionOil() {
    }

    public ImoCorrectionOil(String rptCorrectionId,String oilId, String oilName,BigDecimal fuelTons, String recStatus, Integer isDelete) {
        this.rptCorrectionId = rptCorrectionId;
        this.oilId = oilId;
        this.oilName = oilName;
        this.fuelTons = fuelTons;
        this.recStatus = recStatus;
        this.isDelete = isDelete;
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
    @Column(name = "RPT_CORRECTION_ID")
    public String getRptCorrectionId() {
        return rptCorrectionId;
    }

    public void setRptCorrectionId(String rptCorrectionId) {
        this.rptCorrectionId = rptCorrectionId;
    }

    @Basic
    @Column(name = "OIL_ID")
    public String getOilId() {
        return oilId;
    }

    public void setOilId(String oilId) {
        this.oilId = oilId;
    }

    @Basic
    @Column(name = "OIL_NAME")
    public String getOilName() {
        return oilName;
    }

    public void setOilName(String oilName) {
        this.oilName = oilName;
    }

    @Basic
    @Column(name = "FUEL_TONS")
    public BigDecimal getFuelTons() {
        return fuelTons;
    }

    public void setFuelTons(BigDecimal fuelTons) {
        this.fuelTons = fuelTons;
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
        ImoCorrectionOil that = (ImoCorrectionOil) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(rptCorrectionId, that.rptCorrectionId) &&
                Objects.equals(oilId, that.oilId) &&
                Objects.equals(oilName, that.oilName) &&
                Objects.equals(fuelTons, that.fuelTons) &&
                Objects.equals(recStatus, that.recStatus) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, rptCorrectionId, oilId, oilName, fuelTons, recStatus, creator, createTm, opuser, opdate, isDelete);
    }
}
