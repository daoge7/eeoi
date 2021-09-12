package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "RAW_VOYAGE_PORTLOADING", catalog = "")
public class RawVoyagePortloading {
    private String id;
    private String portinfoId;
    private String shipId;
    private String taskId;
    private String taskIdNext;

    private String loadingType;

    private BigDecimal cargoTons;
    private BigDecimal ballastTons;
    private Integer allBoxNum;
    private Integer heavyBoxNum;
    private Integer peopleNum;
    private Integer carsNum;
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
    @Basic
    @Column(name = "TASK_ID_NEXT")
    public String getTaskIdNext() {
        return taskIdNext;
    }

    public void setTaskIdNext(String taskIdNext) {
        this.taskIdNext = taskIdNext;
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
    @Column(name = "LOADING_TYPE")
    public String getLoadingType() {
        return loadingType;
    }

    public void setLoadingType(String loadingType) {
        this.loadingType = loadingType;
    }

    @Basic
    @Column(name = "CARGO_TONS")
    public BigDecimal getCargoTons() {
        return cargoTons;
    }

    public void setCargoTons(BigDecimal cargoTons) {
        this.cargoTons = cargoTons;
    }

    @Basic
    @Column(name = "BALLAST_TONS")
    public BigDecimal getBallastTons() {
        return ballastTons;
    }

    public void setBallastTons(BigDecimal ballastTons) {
        this.ballastTons = ballastTons;
    }

    @Basic
    @Column(name = "ALL_BOX_NUM")
    public Integer getAllBoxNum() {
        return allBoxNum;
    }

    public void setAllBoxNum(Integer allBoxNum) {
        this.allBoxNum = allBoxNum;
    }

    @Basic
    @Column(name = "HEAVY_BOX_NUM")
    public Integer getHeavyBoxNum() {
        return heavyBoxNum;
    }

    public void setHeavyBoxNum(Integer heavyBoxNum) {
        this.heavyBoxNum = heavyBoxNum;
    }

    @Basic
    @Column(name = "PEOPLE_NUM")
    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    @Basic
    @Column(name = "CARS_NUM")
    public Integer getCarsNum() {
        return carsNum;
    }

    public void setCarsNum(Integer carsNum) {
        this.carsNum = carsNum;
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
        RawVoyagePortloading that = (RawVoyagePortloading) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(portinfoId, that.portinfoId) &&
                Objects.equals(shipId, that.shipId) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(loadingType, that.loadingType) &&
                Objects.equals(cargoTons, that.cargoTons) &&
                Objects.equals(ballastTons, that.ballastTons) &&
                Objects.equals(allBoxNum, that.allBoxNum) &&
                Objects.equals(heavyBoxNum, that.heavyBoxNum) &&
                Objects.equals(peopleNum, that.peopleNum) &&
                Objects.equals(carsNum, that.carsNum) &&
                Objects.equals(recStatus, that.recStatus) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, portinfoId, shipId, taskId, loadingType, cargoTons, ballastTons, allBoxNum, heavyBoxNum, peopleNum, carsNum, recStatus, creator, createTm, opuser, opdate, isDelete);
    }
}
