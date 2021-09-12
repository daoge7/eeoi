package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the OI_SHIP_GENERATOR database table.
 * 
 */
@Entity
@Table(name="OI_SHIP_GENERATOR")
@NamedQuery(name="OiShipGenerator.findAll", query="SELECT o FROM OiShipGenerator o")
public class OiShipGenerator implements Serializable {
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

	private String axisstatus;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TM")
	private Date createTm;

	private String creator;

	@Column(name="DATA_SOURCE")
	private String dataSource;

	@Column(name="ENGINE_TYPE")
	private String engineType;

	@Column(name="IS_DELETE")
	private Integer isDelete;

	private String memo;

	@Column(name="MFRS_NAME")
	private String mfrsName;

	@Temporal(TemporalType.DATE)
	private Date opdate;

	private String opuser;

	@Temporal(TemporalType.DATE)
	@Column(name="PRODUCE_TIME")
	private Date produceTime;

	@Column(name="RATE_POWER")
	private BigDecimal ratePower;

	@Column(name="RATE_VOLTAGE")
	private BigDecimal rateVoltage;

	@Column(name="SHIP_ID")
	private String shipId;

	@Column(name="SHIP_NAME")
	private String shipName;

	private Integer status;

	@Temporal(TemporalType.DATE)
	@Column(name="TAKEN_TIME")
	private Date takenTime;

	@Column(name="UNIT_NUM")
	private Integer unitNum;

	private String xh;

	public OiShipGenerator() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAxisstatus() {
		return this.axisstatus;
	}

	public void setAxisstatus(String axisstatus) {
		this.axisstatus = axisstatus;
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

	public String getEngineType() {
		return this.engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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


	public Date getTakenTime() {
		return this.takenTime;
	}

	public void setTakenTime(Date takenTime) {
		this.takenTime = takenTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public BigDecimal getRateVoltage() {
		return rateVoltage;
	}

	public void setRateVoltage(BigDecimal rateVoltage) {
		this.rateVoltage = rateVoltage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(Integer unitNum) {
		this.unitNum = unitNum;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}
}