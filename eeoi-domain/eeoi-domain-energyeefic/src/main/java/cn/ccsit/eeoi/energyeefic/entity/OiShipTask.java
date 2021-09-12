package cn.ccsit.eeoi.energyeefic.entity;

import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

@Entity
@Table(name = "OI_SHIP_TASK",  catalog = "")
public class OiShipTask {
    private String id;
    private String task;
    private String shipId;

    public OiShipTask(String shipId, String taskyear, BigDecimal eeoiValue) {
        this.shipId = shipId;
        this.taskyear = taskyear;
        this.eeoiValue = eeoiValue;
    }

    public OiShipTask() {
    }

    @Basic
    @Column(name = "SHIP_ID")
    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    private Date startTime;
    private Date endTime;
    private String saillineId;
    private String taskyear;
    private BigDecimal eeoiValue;
    private BigDecimal co2Per;
    private BigDecimal speedrate;
    private BigDecimal userate;
    private BigDecimal emptyrate;
    private String startPort;
    private String destPort;
//    private String operator;
//    private Date updateTime;
    private BigDecimal co2Cost;
    private BigDecimal eeoiFc;
    private BigDecimal eeoiTd;
    private BigDecimal distance;
    private BigDecimal speedDistance;
    private BigDecimal emptyDistance;
    private BigDecimal eeoiContainerNumber;
    private BigDecimal eeoiCarNumber;
    private BigDecimal eeoiPeopleNumber;
    private BigDecimal containerNumberDistance;
    private BigDecimal carNumberDistance;
    private BigDecimal peopleNumberDistance;
    private String recStatus;
    private String creator;
    private Date createTm;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    private OiShipInfo oiShipInfo;
    private List<OiShipVoyage> oiShipVoyages;
    private List<RawVoyagePort> rawVoyagePorts;
    private List<RawVoyagePort> rawVoyagePortsNext;
    private List<RawVoyageSpec> rawVoyageSpecs;
    @OneToMany(mappedBy = "taskIdNext",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<RawVoyagePort> getRawVoyagePortsNext() {
        return rawVoyagePortsNext;
    }

    public void setRawVoyagePortsNext(List<RawVoyagePort> rawVoyagePortsNext) {
        this.rawVoyagePortsNext = rawVoyagePortsNext;
    }
    @OneToMany(mappedBy = "taskId",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<RawVoyagePort> getRawVoyagePorts() {
        return rawVoyagePorts;
    }

    public void setRawVoyagePorts(List<RawVoyagePort> rawVoyagePorts) {
        this.rawVoyagePorts = rawVoyagePorts;
    }
    @OneToMany(mappedBy = "taskId",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<RawVoyageSpec> getRawVoyageSpecs() {
        return rawVoyageSpecs;
    }

    public void setRawVoyageSpecs(List<RawVoyageSpec> rawVoyageSpecs) {
        this.rawVoyageSpecs = rawVoyageSpecs;
    }

    @OneToMany(mappedBy = "taskid",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<OiShipVoyage> getOiShipVoyages() {
        return oiShipVoyages;
    }

    public void setOiShipVoyages(List<OiShipVoyage> oiShipVoyages) {
        this.oiShipVoyages = oiShipVoyages;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SHIP_ID",insertable = false,updatable = false)
    public OiShipInfo getOiShipInfo() {
        return oiShipInfo;
    }

    public void setOiShipInfo(OiShipInfo oiShipInfo) {
        this.oiShipInfo = oiShipInfo;
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
    @Column(name = "TASK")
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Basic
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "SAILLINE_ID")
    public String getSaillineId() {
        return saillineId;
    }

    public void setSaillineId(String saillineId) {
        this.saillineId = saillineId;
    }

    @Basic
    @Column(name = "TASKYEAR")
    public String getTaskyear() {
        return taskyear;
    }

    public void setTaskyear(String taskyear) {
        this.taskyear = taskyear;
    }

    @Basic
    @Column(name = "EEOI_VALUE")
    public BigDecimal getEeoiValue() {
        return eeoiValue;
    }

    public void setEeoiValue(BigDecimal eeoiValue) {
        this.eeoiValue = eeoiValue;
    }

    @Basic
    @Column(name = "CO2PER")
    public BigDecimal getCo2Per() {
        return co2Per;
    }

    public void setCo2Per(BigDecimal co2Per) {
        this.co2Per = co2Per;
    }

    @Basic
    @Column(name = "SPEEDRATE")
    public BigDecimal getSpeedrate() {
        return speedrate;
    }

    public void setSpeedrate(BigDecimal speedrate) {
        this.speedrate = speedrate;
    }

    @Basic
    @Column(name = "USERATE")
    public BigDecimal getUserate() {
        return userate;
    }

    public void setUserate(BigDecimal userate) {
        this.userate = userate;
    }

    @Basic
    @Column(name = "EMPTYRATE")
    public BigDecimal getEmptyrate() {
        return emptyrate;
    }

    public void setEmptyrate(BigDecimal emptyrate) {
        this.emptyrate = emptyrate;
    }

    @Basic
    @Column(name = "START_PORT")
    public String getStartPort() {
        return startPort;
    }

    public void setStartPort(String startPort) {
        this.startPort = startPort;
    }

    @Basic
    @Column(name = "DEST_PORT")
    public String getDestPort() {
        return destPort;
    }

    public void setDestPort(String destPort) {
        this.destPort = destPort;
    }

//    @Basic
//    @Column(name = "OPERATOR")
//    public String getOperator() {
//        return operator;
//    }
//
//    public void setOperator(String operator) {
//        this.operator = operator;
//    }
//
//    @Basic
//    @Column(name = "UPDATE_TIME")
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }

    @Basic
    @Column(name = "CO2COST")
    public BigDecimal getCo2Cost() {
        return co2Cost;
    }

    public void setCo2Cost(BigDecimal co2Cost) {
        this.co2Cost = co2Cost;
    }

    @Basic
    @Column(name = "EEOI_FC")
    public BigDecimal getEeoiFc() {
        return eeoiFc;
    }

    public void setEeoiFc(BigDecimal eeoiFc) {
        this.eeoiFc = eeoiFc;
    }

    @Basic
    @Column(name = "EEOI_TD")
    public BigDecimal getEeoiTd() {
        return eeoiTd;
    }

    public void setEeoiTd(BigDecimal eeoiTd) {
        this.eeoiTd = eeoiTd;
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
    @Column(name = "SPEED_DISTANCE")
    public BigDecimal getSpeedDistance() {
        return speedDistance;
    }

    public void setSpeedDistance(BigDecimal speedDistance) {
        this.speedDistance = speedDistance;
    }

    @Basic
    @Column(name = "EMPTY_DISTANCE")
    public BigDecimal getEmptyDistance() {
        return emptyDistance;
    }

    public void setEmptyDistance(BigDecimal emptyDistance) {
        this.emptyDistance = emptyDistance;
    }

    @Basic
    @Column(name = "EEOI_CONTAINER_NUMBER")
    public BigDecimal getEeoiContainerNumber() {
        return eeoiContainerNumber;
    }

    public void setEeoiContainerNumber(BigDecimal eeoiContainerNumber) {
        this.eeoiContainerNumber = eeoiContainerNumber;
    }

    @Basic
    @Column(name = "EEOI_CAR_NUMBER")
    public BigDecimal getEeoiCarNumber() {
        return eeoiCarNumber;
    }

    public void setEeoiCarNumber(BigDecimal eeoiCarNumber) {
        this.eeoiCarNumber = eeoiCarNumber;
    }

    @Basic
    @Column(name = "EEOI_PEOPLE_NUMBER")
    public BigDecimal getEeoiPeopleNumber() {
        return eeoiPeopleNumber;
    }

    public void setEeoiPeopleNumber(BigDecimal eeoiPeopleNumber) {
        this.eeoiPeopleNumber = eeoiPeopleNumber;
    }

    @Basic
    @Column(name = "CONTAINER_NUMBER_DISTANCE")
    public BigDecimal getContainerNumberDistance() {
        return containerNumberDistance;
    }

    public void setContainerNumberDistance(BigDecimal containerNumberDistance) {
        this.containerNumberDistance = containerNumberDistance;
    }

    @Basic
    @Column(name = "CAR_NUMBER_DISTANCE")
    public BigDecimal getCarNumberDistance() {
        return carNumberDistance;
    }

    public void setCarNumberDistance(BigDecimal carNumberDistance) {
        this.carNumberDistance = carNumberDistance;
    }

    @Basic
    @Column(name = "PEOPLE_NUMBER_DISTANCE")
    public BigDecimal getPeopleNumberDistance() {
        return peopleNumberDistance;
    }

    public void setPeopleNumberDistance(BigDecimal peopleNumberDistance) {
        this.peopleNumberDistance = peopleNumberDistance;
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
        OiShipTask that = (OiShipTask) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(task, that.task) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(saillineId, that.saillineId) &&
                Objects.equals(taskyear, that.taskyear) &&
                Objects.equals(eeoiValue, that.eeoiValue) &&
                Objects.equals(co2Per, that.co2Per) &&
                Objects.equals(speedrate, that.speedrate) &&
                Objects.equals(userate, that.userate) &&
                Objects.equals(emptyrate, that.emptyrate) &&
                Objects.equals(startPort, that.startPort) &&
                Objects.equals(destPort, that.destPort) &&
//                Objects.equals(operator, that.operator) &&
//                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(co2Cost, that.co2Cost) &&
                Objects.equals(eeoiFc, that.eeoiFc) &&
                Objects.equals(eeoiTd, that.eeoiTd) &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(speedDistance, that.speedDistance) &&
                Objects.equals(emptyDistance, that.emptyDistance) &&
                Objects.equals(eeoiContainerNumber, that.eeoiContainerNumber) &&
                Objects.equals(eeoiCarNumber, that.eeoiCarNumber) &&
                Objects.equals(eeoiPeopleNumber, that.eeoiPeopleNumber) &&
                Objects.equals(containerNumberDistance, that.containerNumberDistance) &&
                Objects.equals(carNumberDistance, that.carNumberDistance) &&
                Objects.equals(peopleNumberDistance, that.peopleNumberDistance) &&
                Objects.equals(recStatus, that.recStatus) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, task, startTime, endTime, saillineId, taskyear, eeoiValue, co2Per, speedrate, userate, emptyrate, startPort, destPort, co2Cost, eeoiFc, eeoiTd, distance, speedDistance, emptyDistance, eeoiContainerNumber, eeoiCarNumber, eeoiPeopleNumber, containerNumberDistance, carNumberDistance, peopleNumberDistance, recStatus, creator, createTm, opuser, opdate, isDelete);
    }
}
