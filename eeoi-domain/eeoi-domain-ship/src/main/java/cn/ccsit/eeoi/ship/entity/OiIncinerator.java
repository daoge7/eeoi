package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the OI_INCINERATOR database table.
 */
@Entity
@Table(name = "OI_INCINERATOR")
@NamedQuery(name = "OiIncinerator.findAll", query = "SELECT o FROM OiIncinerator o")
public class OiIncinerator implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator = "idGenerator")
    private String id;
    @OneToMany(mappedBy = "equipmentId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShipEquipmentFuelMap> shipEquipmentFuelMaps;
    @Transient
    private List<String> fuelIds = new ArrayList<>();

    public List<ShipEquipmentFuelMap> getShipEquipmentFuelMaps() {
        return shipEquipmentFuelMaps;
    }

    public void setShipEquipmentFuelMaps(List<ShipEquipmentFuelMap> shipEquipmentFuelMaps) {
        this.shipEquipmentFuelMaps = shipEquipmentFuelMaps;
    }

    public List<String> getFuelIds() {
        return fuelIds;
    }

    public void setFuelIds(List<String> fuelIds) {
        this.fuelIds = fuelIds;
    }

    private String about;

    private String ccsno;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TM")
    private Date createTm;

    private String creator;

    @Column(name = "DATA_SOURCE")
    private String dataSource;
    @Column(name = "ENERGYID")
    private String energyId;

    public String getEnergyId() {
        return energyId;
    }

    public void setEnergyId(String energyId) {
        this.energyId = energyId;
    }

    private String inctype;

    @Column(name = "IS_DELETE")
    private Integer isDelete;

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "MFRS_NAME")
    private String mfrsName;

    private String model;

    @Temporal(TemporalType.DATE)
    private Date opdate;

    private String operator;

    private String opuser;

    @Temporal(TemporalType.DATE)
    @Column(name = "PRODUCE_TIME")
    private Date produceTime;

    private String remark;

    @Column(name = "SHIP_ID")
    private String shipId;

    @Column(name = "SHIP_NAME")
    private String shipName;

    private String source;

    private Integer status;

    @Temporal(TemporalType.DATE)
    @Column(name = "TAKEN_TIME")
    private Date takenTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    private String xh;

    public OiIncinerator() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbout() {
        return this.about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCcsno() {
        return this.ccsno;
    }

    public void setCcsno(String ccsno) {
        this.ccsno = ccsno;
    }

    public Date getCreateTm() {
        return this.createTm;
    }

    public void setCreateTm(Date createTm) {
        this.createTm = createTm;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getInctype() {
        return this.inctype;
    }

    public void setInctype(String inctype) {
        this.inctype = inctype;
    }


    public String getMfrsName() {
        return this.mfrsName;
    }

    public void setMfrsName(String mfrsName) {
        this.mfrsName = mfrsName;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getOpdate() {
        return this.opdate;
    }

    public void setOpdate(Date opdate) {
        this.opdate = opdate;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOpuser() {
        return this.opuser;
    }

    public void setOpuser(String opuser) {
        this.opuser = opuser;
    }

    public Date getProduceTime() {
        return this.produceTime;
    }

    public void setProduceTime(Date produceTime) {
        this.produceTime = produceTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShipId() {
        return this.shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getShipName() {
        return this.shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getTakenTime() {
        return this.takenTime;
    }

    public void setTakenTime(Date takenTime) {
        this.takenTime = takenTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getXh() {
        return this.xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

}