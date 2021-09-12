package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the OI_SHIP_TURBINE database table.
 * 
 */
@Entity
@Table(name="OI_SHIP_TURBINE")
@NamedQuery(name="OiShipTurbine.findAll", query="SELECT o FROM OiShipTurbine o")
public class OiShipTurbine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
	@GeneratedValue(generator = "idGenerator")
	private String id;

	@OneToMany(mappedBy = "equipmentId",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
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

	private String ccsno;

	@Column(name="CONSUMP_RATE")
	private BigDecimal consumpRate;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TM")
	private Date createTm;

	private String creator;

	@Column(name="DESIGN_NAME")
	private String designName;

	private String energyid;

	@Column(name="ENGINE_TYPE")
	private String engineType;

	@Column(name="IS_DELETE")
	private Integer isDelete;

	@Column(name="MFRS_NAME")
	private String mfrsName;

	@Temporal(TemporalType.DATE)
	private Date opdate;

	private String opuser;

	private BigDecimal powner;

	@Temporal(TemporalType.DATE)
	@Column(name="PRODUCE_TIME")
	private Date produceTime;

	@Column(name="RATE_POWER")
	private BigDecimal ratePower;

	@Column(name="RATED_SPEED")
	private BigDecimal ratedSpeed;

	private String remark;

	@Column(name="SERIAL_NO")
	private String serialNo;

	@Column(name="SHIP_ID")
	private String shipId;

	@Column(name="SHIP_NAME")
	private String shipName;

	@Column(name="TURBINE_OILTYPE")
	private String turbineOiltype;

	@Column(name="TURBINE_TYPE")
	private String turbineType;

	@Temporal(TemporalType.DATE)
	private Date updatedate;

	private String xh;

	public OiShipTurbine() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCcsno() {
		return this.ccsno;
	}

	public void setCcsno(String ccsno) {
		this.ccsno = ccsno;
	}

	public BigDecimal getConsumpRate() {
		return this.consumpRate;
	}

	public void setConsumpRate(BigDecimal consumpRate) {
		this.consumpRate = consumpRate;
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

	public String getDesignName() {
		return this.designName;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public String getEnergyid() {
		return this.energyid;
	}

	public void setEnergyid(String energyid) {
		this.energyid = energyid;
	}

	public String getEngineType() {
		return this.engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getMfrsName() {
		return this.mfrsName;
	}

	public void setMfrsName(String mfrsName) {
		this.mfrsName = mfrsName;
	}

	public Date getOpdate() {
		return this.opdate;
	}

	public void setOpdate(Date opdate) {
		this.opdate = opdate;
	}

	public String getOpuser() {
		return this.opuser;
	}

	public void setOpuser(String opuser) {
		this.opuser = opuser;
	}

	public BigDecimal getPowner() {
		return this.powner;
	}

	public void setPowner(BigDecimal powner) {
		this.powner = powner;
	}

	public Date getProduceTime() {
		return this.produceTime;
	}

	public void setProduceTime(Date produceTime) {
		this.produceTime = produceTime;
	}

	public BigDecimal getRatePower() {
		return this.ratePower;
	}

	public void setRatePower(BigDecimal ratePower) {
		this.ratePower = ratePower;
	}

	public BigDecimal getRatedSpeed() {
		return this.ratedSpeed;
	}

	public void setRatedSpeed(BigDecimal ratedSpeed) {
		this.ratedSpeed = ratedSpeed;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	public String getTurbineOiltype() {
		return this.turbineOiltype;
	}

	public void setTurbineOiltype(String turbineOiltype) {
		this.turbineOiltype = turbineOiltype;
	}

	public String getTurbineType() {
		return this.turbineType;
	}

	public void setTurbineType(String turbineType) {
		this.turbineType = turbineType;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getXh() {
		return this.xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

}