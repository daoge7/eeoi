package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "RAW_VOYAGE_ADDOIL",  catalog = "")
public class RawVoyageAddoil {
    private String id;
    private String portinfoId;
    private String shipId;
    private String taskId;
    private String taskIdNext;
    private String oilId;
    private String oilName;
    private BigDecimal addTons;
    private BigDecimal sulfurPercent;
    private BigDecimal viscosity;
    private String isoBrand;
    private String addBillNo;
    private Date addTm;
    private String recStatus;
    private String creator;
    private Date createTm;
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
    @Column(name = "PORTINFO_ID")
    public String getPortinfoId() {
        return portinfoId;
    }

    public void setPortinfoId(String portinfoId) {
        this.portinfoId = portinfoId;
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
    @Column(name = "TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "TASK_ID_NEXT")
    public String getTaskIdNext() {
        return taskIdNext;
    }

    public void setTaskIdNext(String taskIdNext) {
        this.taskIdNext = taskIdNext;
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
    @Column(name = "ADD_TONS")
    public BigDecimal getAddTons() {
        return addTons;
    }

    public void setAddTons(BigDecimal addTons) {
        this.addTons = addTons;
    }

    @Basic
    @Column(name = "SULFUR_PERCENT")
    public BigDecimal getSulfurPercent() {
        return sulfurPercent;
    }

    public void setSulfurPercent(BigDecimal sulfurPercent) {
        this.sulfurPercent = sulfurPercent;
    }

    @Basic
    @Column(name = "VISCOSITY")
    public BigDecimal getViscosity() {
        return viscosity;
    }

    public void setViscosity(BigDecimal viscosity) {
        this.viscosity = viscosity;
    }

    @Basic
    @Column(name = "ISO_BRAND")
    public String getIsoBrand() {
        return isoBrand;
    }

    public void setIsoBrand(String isoBrand) {
        this.isoBrand = isoBrand;
    }

    @Basic
    @Column(name = "ADD_BILL_NO")
    public String getAddBillNo() {
        return addBillNo;
    }

    public void setAddBillNo(String addBillNo) {
        this.addBillNo = addBillNo;
    }

    @Basic
    @Column(name = "ADD_TM")
    public Date getAddTm() {
        return addTm;
    }

    public void setAddTm(Date addTm) {
        this.addTm = addTm;
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
        RawVoyageAddoil that = (RawVoyageAddoil) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(portinfoId, that.portinfoId) &&
                Objects.equals(shipId, that.shipId) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(taskIdNext, that.taskIdNext) &&
                Objects.equals(oilId, that.oilId) &&
                Objects.equals(oilName, that.oilName) &&
                Objects.equals(addTons, that.addTons) &&
                Objects.equals(sulfurPercent, that.sulfurPercent) &&
                Objects.equals(viscosity, that.viscosity) &&
                Objects.equals(isoBrand, that.isoBrand) &&
                Objects.equals(addBillNo, that.addBillNo) &&
                Objects.equals(addTm, that.addTm) &&
                Objects.equals(recStatus, that.recStatus) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, portinfoId, shipId, taskId, taskIdNext, oilId, oilName, addTons, sulfurPercent, viscosity, isoBrand, addBillNo, addTm, recStatus, creator, createTm, opuser, opdate, isDelete);
    }
}
