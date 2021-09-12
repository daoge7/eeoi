package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "RAW_VOYAGE_PORTOIL", catalog = "")
public class RawVoyagePortoil {
    private String id;
    private String portinfoId;
    private String shipId;
    private String taskId;
    private String taskIdNext;
    private String oilId;
    private String oilName;
    private BigDecimal arrTons;
    private BigDecimal deptTons;
    private BigDecimal correctTons;
    private Date correctTm;
    private String recStatus;
    private String creator;
    private Date createTm;
    private String opuser;
    private Date opdate;
    private Integer isDelete;

    public RawVoyagePortoil() {
    }

    public RawVoyagePortoil(String oilId, BigDecimal deptTons,BigDecimal arrTons,BigDecimal correctTons) {
        this.oilId = oilId;
        this.deptTons = deptTons;
        this.arrTons = arrTons;
        this.correctTons = correctTons;
    }

    public RawVoyagePortoil(String id, String oilId, BigDecimal arrTons) {
        this.id = id;
        this.oilId = oilId;
        this.arrTons = arrTons;
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
    @Column(name = "TASK_ID_NEXT")
    public String getTaskIdNext() {
        return taskIdNext;
    }

    public void setTaskIdNext(String taskIdNext) {
        this.taskIdNext = taskIdNext;
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
    @Column(name = "ARR_TONS")
    public BigDecimal getArrTons() {
        return arrTons;
    }

    public void setArrTons(BigDecimal arrTons) {
        this.arrTons = arrTons;
    }

    @Basic
    @Column(name = "DEPT_TONS")
    public BigDecimal getDeptTons() {
        return deptTons;
    }

    public void setDeptTons(BigDecimal deptTons) {
        this.deptTons = deptTons;
    }

    @Basic
    @Column(name = "CORRECT_TONS")
    public BigDecimal getCorrectTons() {
        return correctTons;
    }

    public void setCorrectTons(BigDecimal correctTons) {
        this.correctTons = correctTons;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CORRECT_TM")
    public Date getCorrectTm() {
        return correctTm;
    }

    public void setCorrectTm(Date correctTm) {
        this.correctTm = correctTm;
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
        RawVoyagePortoil that = (RawVoyagePortoil) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(portinfoId, that.portinfoId) &&
                Objects.equals(shipId, that.shipId) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(oilId, that.oilId) &&
                Objects.equals(oilName, that.oilName) &&
                Objects.equals(arrTons, that.arrTons) &&
                Objects.equals(deptTons, that.deptTons) &&
                Objects.equals(correctTons, that.correctTons) &&
                Objects.equals(correctTm, that.correctTm) &&
                Objects.equals(recStatus, that.recStatus) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, portinfoId, shipId, taskId, oilId, oilName, arrTons, deptTons,correctTons, correctTm, recStatus, creator, createTm, opuser, opdate, isDelete);
    }
}
