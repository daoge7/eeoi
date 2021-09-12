package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "RAW_VOYAGE_SPEC",  catalog = "")
public class RawVoyageSpec {
    private String id;
    private String shipId;
    private String taskId;
    private String recordType;
    private Date beginTm;
    private BigDecimal beginZone;
    private Date endTm;
    private BigDecimal endZone;
    private BigDecimal distance;
    private String recStatus;
    private String creator;
    private Date createTm;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    private List<RawVoyageSpecCons> rawVoyageSpecCons;
    @OneToMany(mappedBy = "specId",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<RawVoyageSpecCons> getRawVoyageSpecCons() {
        return rawVoyageSpecCons;
    }

    public void setRawVoyageSpecCons(List<RawVoyageSpecCons> rawVoyageSpecCons) {
        this.rawVoyageSpecCons = rawVoyageSpecCons;
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
    @Column(name = "RECORD_TYPE")
    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BEGIN_TM")
    public Date getBeginTm() {
        return beginTm;
    }

    public void setBeginTm(Date beginTm) {
        this.beginTm = beginTm;
    }

    @Basic
    @Column(name = "BEGIN_ZONE")
    public BigDecimal getBeginZone() {
        return beginZone;
    }

    public void setBeginZone(BigDecimal beginZone) {
        this.beginZone = beginZone;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_TM")
    public Date getEndTm() {
        return endTm;
    }

    public void setEndTm(Date endTm) {
        this.endTm = endTm;
    }

    @Basic
    @Column(name = "END_ZONE")
    public BigDecimal getEndZone() {
        return endZone;
    }

    public void setEndZone(BigDecimal endZone) {
        this.endZone = endZone;
    }

    @Basic
    @Column(name = "DISTANCE")
    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
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
        RawVoyageSpec that = (RawVoyageSpec) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shipId, that.shipId) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(recordType, that.recordType) &&
                Objects.equals(beginTm, that.beginTm) &&
                Objects.equals(beginZone, that.beginZone) &&
                Objects.equals(endTm, that.endTm) &&
                Objects.equals(endZone, that.endZone) &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(recStatus, that.recStatus) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shipId, taskId, recordType, beginTm, beginZone, endTm, endZone, distance, recStatus, creator, createTm, opuser, opdate, isDelete);
    }
}
