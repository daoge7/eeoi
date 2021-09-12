package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "CD_OIL",  catalog = "")
public class CdOil {
    private String id;
    private String cdId;
    private String oilId;
    private String oilName;
    private String oilMethod;
    private BigDecimal fuelTons;
    private String recStatus;
    private String creator;
    private Date createTm;
    private String opuser;
    private Date opdate;
    private Integer isDelete;

    public CdOil() {
    }

    public CdOil(String cdId,String oilId, String oilName, String oilMethod, BigDecimal fuelTons, String recStatus, Integer isDelete) {
        this.cdId = cdId;
        this.oilId = oilId;
        this.oilName = oilName;
        this.oilMethod = oilMethod;
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
    @Column(name = "CD_ID")
    public String getCdId() {
        return cdId;
    }

    public void setCdId(String cdId) {
        this.cdId = cdId;
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
    @Column(name = "OIL_METHOD")
    public String getOilMethod() {
        return oilMethod;
    }

    public void setOilMethod(String oilMethod) {
        this.oilMethod = oilMethod;
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
        CdOil cdOil = (CdOil) o;
        return Objects.equals(id, cdOil.id) &&
                Objects.equals(cdId, cdOil.cdId) &&
                Objects.equals(oilId, cdOil.oilId) &&
                Objects.equals(oilName, cdOil.oilName) &&
                Objects.equals(oilMethod, cdOil.oilMethod) &&
                Objects.equals(fuelTons, cdOil.fuelTons) &&
                Objects.equals(recStatus, cdOil.recStatus) &&
                Objects.equals(creator, cdOil.creator) &&
                Objects.equals(createTm, cdOil.createTm) &&
                Objects.equals(opuser, cdOil.opuser) &&
                Objects.equals(opdate, cdOil.opdate) &&
                Objects.equals(isDelete, cdOil.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, cdId, oilId, oilName, oilMethod, fuelTons, recStatus, creator, createTm, opuser, opdate, isDelete);
    }
}
