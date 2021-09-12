package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "RAW_VOYAGE_SPEC_CONS", catalog = "")
public class RawVoyageSpecCons {
    private String id;
    private String specId;
    private String shipId;
    private String taskId;
    private String oilId;
    private String oilName;
    private BigDecimal consTons;
    private BigDecimal outTons;
    private Date outTm;
    @Basic
    @Column(name = "OUT_TIME")
    public Date getOutTm() {
        return outTm;
    }

    public void setOutTm(Date outTm) {
        this.outTm = outTm;
    }


    @Basic
    @Column(name = "OUT_TONS")
    public BigDecimal getOutTons() {
        return outTons;
    }

    public void setOutTons(BigDecimal outTons) {
        this.outTons = outTons;
    }

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
    @Column(name = "SPEC_ID")
    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
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
    @Column(name = "CONS_TONS")
    public BigDecimal getConsTons() {
        return consTons;
    }

    public void setConsTons(BigDecimal consTons) {
        this.consTons = consTons;
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
    @Temporal(TemporalType.TIMESTAMP)
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
    @Temporal(TemporalType.TIMESTAMP)
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
        RawVoyageSpecCons that = (RawVoyageSpecCons) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(specId, that.specId) &&
                Objects.equals(shipId, that.shipId) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(oilId, that.oilId) &&
                Objects.equals(oilName, that.oilName) &&
                Objects.equals(consTons, that.consTons) &&
                Objects.equals(recStatus, that.recStatus) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, specId, shipId, taskId, oilId, oilName, consTons, recStatus, creator, createTm, opuser, opdate, isDelete);
    }
}
