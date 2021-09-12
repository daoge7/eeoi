package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the oi_main_engine database table.
 * 
 */
@Entity
@Table(name="oi_main_engine")
@NamedQuery(name="OiMainEngine.findAll", query="SELECT o FROM OiMainEngine o")
public class OiMainEngine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
	@GeneratedValue(generator = "idGenerator")
	private String id;

	private String about;

	private String ccsno;
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

	@Column(name="consump_rate")
	private BigDecimal consumpRate;

	@Column(name="data_source")
	private String dataSource;

	private String datatype;

	private String energyId;
	@Column(name="SERIAL_NO")
	private String serialNo;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name="engine_type")
	private String engineType;

	private String operator;
	@Column(name="MFRS_NAME")
	private String mfrsName;

	public String getMfrsName() {
		return mfrsName;
	}

	public void setMfrsName(String mfrsName) {
		this.mfrsName = mfrsName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="produce_time")
	private Date produceTime;

	@Column(name="rate_power")
	private BigDecimal ratePower;

	@Column(name="rated_speed")
	private BigDecimal ratedSpeed;

	@Column(name="ship_id")
	private String shipId;

	@Column(name="ship_name")
	private String shipName;

	private String source;

	private Integer status;
	private String xh;
	@Column(name="IS_DELETE")
	private Integer isDelete;

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name="taken_time")
	private Date takenTime;

	@Column(name="unit_num")
	private Integer unitNum;

	@Column(name="update_time")
	private Date updateTime;
	@Column(name="CREATE_TM")
	private Date createTm;

	public Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	public OiMainEngine() {
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

	public BigDecimal getConsumpRate() {
		return this.consumpRate;
	}

	public void setConsumpRate(BigDecimal consumpRate) {
		this.consumpRate = consumpRate;
	}

	public String getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getDatatype() {
		return this.datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getEnergyId() {
		return this.energyId;
	}

	public void setEnergyId(String energyId) {
		this.energyId = energyId;
	}

	public String getEngineType() {
		return this.engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getTakenTime() {
		return this.takenTime;
	}

	public void setTakenTime(Date takenTime) {
		this.takenTime = takenTime;
	}

	public Integer getUnitNum() {
		return this.unitNum;
	}

	public void setUnitNum(Integer unitNum) {
		this.unitNum = unitNum;
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