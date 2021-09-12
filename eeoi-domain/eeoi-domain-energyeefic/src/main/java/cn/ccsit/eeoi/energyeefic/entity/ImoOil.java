package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "IMO_OIL", catalog = "")
public class ImoOil {
    private String id;
    private String rptId;
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

    public ImoOil(String rptId,String oilId, String oilName, String oilMethod, BigDecimal fuelTons, String recStatus, Integer isDelete) {
        this.rptId = rptId;
        this.oilId = oilId;
        this.oilName = oilName;
        this.oilMethod = oilMethod;
        this.fuelTons = fuelTons;
        this.recStatus = recStatus;
        this.isDelete = isDelete;
    }

    public ImoOil() {
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
        ImoOil imoOil = (ImoOil) o;
        return Objects.equals(id, imoOil.id) &&
                Objects.equals(rptId, imoOil.rptId) &&
                Objects.equals(oilId, imoOil.oilId) &&
                Objects.equals(oilName, imoOil.oilName) &&
                Objects.equals(oilMethod, imoOil.oilMethod) &&
                Objects.equals(fuelTons, imoOil.fuelTons) &&
                Objects.equals(recStatus, imoOil.recStatus) &&
                Objects.equals(creator, imoOil.creator) &&
                Objects.equals(createTm, imoOil.createTm) &&
                Objects.equals(opuser, imoOil.opuser) &&
                Objects.equals(opdate, imoOil.opdate) &&
                Objects.equals(isDelete, imoOil.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, rptId, oilId, oilName, oilMethod, fuelTons, recStatus, creator, createTm, opuser, opdate, isDelete);
    }
}
