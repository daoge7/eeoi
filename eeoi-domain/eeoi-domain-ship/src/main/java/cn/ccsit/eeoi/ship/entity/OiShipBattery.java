package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the OI_SHIP_BATTERY database table.
 * 
 */
@Entity
@Table(name="OI_SHIP_BATTERY")
@NamedQuery(name="OiShipBattery.findAll", query="SELECT o FROM OiShipBattery o")
public class OiShipBattery implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
	@GeneratedValue(generator = "idGenerator")
	private String id;

	@Column(name="BATTERY_MODEL")
	private String batteryModel;

	private BigDecimal capacity;

	@Column(name="CELL_MODEL")
	private String cellModel;

	@Column(name="CELL_PARAM")
	private BigDecimal cellParam;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TM")
	private Date createTm;

	private String creator;

	@Column(name="DATA_SOURCE")
	private String dataSource;

	@Column(name="IS_DELETE")
	private Integer isDelete;

	private String memo;

	@Column(name="MFRS_NAME")
	private String mfrsName;

	@Temporal(TemporalType.DATE)
	private Date opdate;

	private String opuser;

	@Temporal(TemporalType.DATE)
	@Column(name="PRODUCT_DATE")
	private Date productDate;

	@Column(name="SERIAL_NO")
	private String serialNo;

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

	private BigDecimal voltage;

	private BigDecimal weight;

	private String xh;

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

	public Integer getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(Integer unitNum) {
		this.unitNum = unitNum;
	}

	public OiShipBattery() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatteryModel() {
		return this.batteryModel;
	}

	public void setBatteryModel(String batteryModel) {
		this.batteryModel = batteryModel;
	}

	public BigDecimal getCapacity() {
		return this.capacity;
	}

	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}

	public String getCellModel() {
		return this.cellModel;
	}

	public void setCellModel(String cellModel) {
		this.cellModel = cellModel;
	}

	public BigDecimal getCellParam() {
		return this.cellParam;
	}

	public void setCellParam(BigDecimal cellParam) {
		this.cellParam = cellParam;
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

	public Date getProductDate() {
		return this.productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
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


	public Date getTakenTime() {
		return this.takenTime;
	}

	public void setTakenTime(Date takenTime) {
		this.takenTime = takenTime;
	}


	public BigDecimal getVoltage() {
		return this.voltage;
	}

	public void setVoltage(BigDecimal voltage) {
		this.voltage = voltage;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getXh() {
		return this.xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

}