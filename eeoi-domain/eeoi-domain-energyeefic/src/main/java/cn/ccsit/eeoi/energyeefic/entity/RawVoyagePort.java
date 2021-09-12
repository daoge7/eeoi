package cn.ccsit.eeoi.energyeefic.entity;

import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "RAW_VOYAGE_PORT", catalog = "")
public class


RawVoyagePort {
    private String id;
    private String shipId;
    /**
     * 港口唯一识别码
     */
    private String refCode;

    @Basic
    @Column(name = "SHIP_ID")
    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    private String taskId;
    private String taskIdNext;
    private String taskCode;

    private String recordType;
    private String portcn;
    private String porten;
    private String portid;
    private Integer isEu;
    private Date arrTm;
    private BigDecimal arrZone;
    private Date deptTm;
    private BigDecimal deptZone;
    private BigDecimal distance;
    private String inPort;
    private String portWork;
    private BigDecimal shorePower;
    private String recStatus;
    private String creator;
    private Date createTm;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    private OiShipInfo oiShipInfo;
    private List<RawVoyagePortoil> rawVoyagePortoils;
    private List<RawVoyagePortloading> rawVoyagePortloadings;
    private List<RawVoyageAddoil> rawVoyageAddoils;
    private List<RawVoyageOutoil> rawVoyageOutoils;
    private List<RawVoyageSludge> rawVoyageSludges;
    @Basic
    @Column(name = "REF_CODE")
    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    @OneToMany(mappedBy = "portinfoId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<RawVoyageAddoil> getRawVoyageAddoils() {
        return rawVoyageAddoils;
    }

    public void setRawVoyageAddoils(List<RawVoyageAddoil> rawVoyageAddoils) {
        this.rawVoyageAddoils = rawVoyageAddoils;
    }

    @OneToMany(mappedBy = "portinfoId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<RawVoyageOutoil> getRawVoyageOutoils() {
        return rawVoyageOutoils;
    }

    public void setRawVoyageOutoils(List<RawVoyageOutoil> rawVoyageOutoils) {
        this.rawVoyageOutoils = rawVoyageOutoils;
    }

    @OneToMany(mappedBy = "portinfoId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<RawVoyageSludge> getRawVoyageSludges() {
        return rawVoyageSludges;
    }

    public void setRawVoyageSludges(List<RawVoyageSludge> rawVoyageSludges) {
        this.rawVoyageSludges = rawVoyageSludges;
    }

    @OneToMany(mappedBy = "portinfoId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<RawVoyagePortoil> getRawVoyagePortoils() {
        return rawVoyagePortoils;
    }

    public void setRawVoyagePortoils(List<RawVoyagePortoil> rawVoyagePortoils) {
        this.rawVoyagePortoils = rawVoyagePortoils;
    }

    @Basic
    @Column(name = "TASK_ID_NEXT")
    public String getTaskIdNext() {
        return taskIdNext;
    }

    public void setTaskIdNext(String taskIdNext) {
        this.taskIdNext = taskIdNext;
    }

    @OneToMany(mappedBy = "portinfoId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    public List<RawVoyagePortloading> getRawVoyagePortloadings() {
        return rawVoyagePortloadings;
    }

    public void setRawVoyagePortloadings(List<RawVoyagePortloading> rawVoyagePortloadings) {
        this.rawVoyagePortloadings = rawVoyagePortloadings;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SHIP_ID", insertable = false, updatable = false)
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
    @Column(name = "TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "TASK_CODE")
    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
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
    @Column(name = "PORTCN")
    public String getPortcn() {
        return portcn;
    }

    public void setPortcn(String portcn) {
        this.portcn = portcn;
    }

    @Basic
    @Column(name = "PORTEN")
    public String getPorten() {
        return porten;
    }

    public void setPorten(String porten) {
        this.porten = porten;
    }

    @Basic
    @Column(name = "PORTID")
    public String getPortid() {
        return portid;
    }

    public void setPortid(String portid) {
        this.portid = portid;
    }

    @Basic
    @Column(name = "IS_EU")
    public Integer getIsEu() {
        return isEu;
    }

    public void setIsEu(Integer isEu) {
        this.isEu = isEu;
    }


    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ARR_TM")
    public Date getArrTm() {
        return arrTm;
    }

    public void setArrTm(Date arrTm) {
        this.arrTm = arrTm;
    }

    @Basic
    @Column(name = "ARR_ZONE")
    public BigDecimal getArrZone() {
        return arrZone;
    }

    public void setArrZone(BigDecimal arrZone) {
        this.arrZone = arrZone;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DEPT_TM")
    public Date getDeptTm() {
        return deptTm;
    }

    public void setDeptTm(Date deptTm) {
        this.deptTm = deptTm;
    }

    @Basic
    @Column(name = "DEPT_ZONE")
    public BigDecimal getDeptZone() {
        return deptZone;
    }

    public void setDeptZone(BigDecimal deptZone) {
        this.deptZone = deptZone;
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
    @Column(name = "IN_PORT")
    public String getInPort() {
        return inPort;
    }

    public void setInPort(String inPort) {
        this.inPort = inPort;
    }

    @Basic
    @Column(name = "PORT_WORK")
    public String getPortWork() {
        return portWork;
    }

    public void setPortWork(String portWork) {
        this.portWork = portWork;
    }

    @Basic
    @Column(name = "SHORE_POWER")
    public BigDecimal getShorePower() {
        return shorePower;
    }

    public void setShorePower(BigDecimal shorePower) {
        this.shorePower = shorePower;
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
        RawVoyagePort that = (RawVoyagePort) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(taskCode, that.taskCode) &&
                Objects.equals(recordType, that.recordType) &&
                Objects.equals(portcn, that.portcn) &&
                Objects.equals(porten, that.porten) &&
                Objects.equals(portid, that.portid) &&
                Objects.equals(isEu, that.isEu) &&
                Objects.equals(arrTm, that.arrTm) &&
                Objects.equals(arrZone, that.arrZone) &&
                Objects.equals(deptTm, that.deptTm) &&
                Objects.equals(deptZone, that.deptZone) &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(inPort, that.inPort) &&
                Objects.equals(portWork, that.portWork) &&
                Objects.equals(shorePower, that.shorePower) &&
                Objects.equals(recStatus, that.recStatus) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, taskId, taskCode, recordType, portcn, porten, portid, isEu, arrTm, arrZone, deptTm, deptZone, distance, inPort, portWork, shorePower, recStatus, creator, createTm, opuser, opdate, isDelete);
    }
}
