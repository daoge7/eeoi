package cn.ccsit.eeoi.ship.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the oi_prime_engine database table.
 * 
 */
@Entity
@Table(name="oi_prime_engine")
@NamedQuery(name="OiPrimeEngine.findAll", query="SELECT o FROM OiPrimeEngine o")
public class OiPrimeEngine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String about;

	private String ccsno;

	@Column(name="CONSUMP_RATE")
	private float consumpRate;

	@Column(name="DATA_SOURCE")
	private String dataSource;

	private String datatype;

	private String energyid;

	@Column(name="ENGINE_TYPE")
	private String engineType;

	private String operator;

	@Temporal(TemporalType.DATE)
	@Column(name="PRODUCE_TIME")
	private Date produceTime;

	@Column(name="RATE_POWER")
	private float ratePower;

	@Column(name="RATED_SPEED")
	private float ratedSpeed;

	@Column(name="SHIP_ID")
	private String shipId;

	@Column(name="SHIP_NAME")
	private String shipName;

	private String source;

	private BigDecimal status;

	@Temporal(TemporalType.DATE)
	@Column(name="TAKEN_TIME")
	private Date takenTime;

	@Column(name="UNIT_NUM")
	private BigDecimal unitNum;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_TIME")
	private Date updateTime;

	private BigDecimal xh;

	public OiPrimeEngine() {
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

	public float getConsumpRate() {
		return this.consumpRate;
	}

	public void setConsumpRate(float consumpRate) {
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

	public float getRatePower() {
		return this.ratePower;
	}

	public void setRatePower(float ratePower) {
		this.ratePower = ratePower;
	}

	public float getRatedSpeed() {
		return this.ratedSpeed;
	}

	public void setRatedSpeed(float ratedSpeed) {
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

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public Date getTakenTime() {
		return this.takenTime;
	}

	public void setTakenTime(Date takenTime) {
		this.takenTime = takenTime;
	}

	public BigDecimal getUnitNum() {
		return this.unitNum;
	}

	public void setUnitNum(BigDecimal unitNum) {
		this.unitNum = unitNum;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getXh() {
		return this.xh;
	}

	public void setXh(BigDecimal xh) {
		this.xh = xh;
	}

}