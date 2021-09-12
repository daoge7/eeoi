package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the OI_SHIP_INERT database table.
 * 
 */
@Entity
@Table(name="OI_SHIP_INERT")
@NamedQuery(name="OiShipInert.findAll", query="SELECT o FROM OiShipInert o")
public class OiShipInert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
	@GeneratedValue(generator = "idGenerator")
	private String id;

	@OneToMany(mappedBy = "equipmentId",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<ShipEquipmentFuelMap> shipEquipmentFuelMaps;

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

	@Transient
	private List<String> fuelIds = new ArrayList<>();

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TM")
	private Date createTm;

	private String creator;

	private String energyid;

	@Column(name="INERT_OILTYPE")
	private String inertOiltype;

	@Column(name="IS_DELETE")
	private Integer isDelete;

	@Column(name="MFRS_NAME")
	private String mfrsName;

	@Temporal(TemporalType.DATE)
	private Date opdate;

	private String opuser;

	@Column(name="POWER")
	private BigDecimal power;

	@Temporal(TemporalType.DATE)
	@Column(name="PRODUCE_TIME")
	private Date produceTime;

	private String remark;

	@Column(name="SHIP_ID")
	private String shipId;

	@Temporal(TemporalType.DATE)
	private Date updatedate;

	private String xh;

	public OiShipInert() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getEnergyid() {
		return this.energyid;
	}

	public void setEnergyid(String energyid) {
		this.energyid = energyid;
	}

	public String getInertOiltype() {
		return this.inertOiltype;
	}

	public void setInertOiltype(String inertOiltype) {
		this.inertOiltype = inertOiltype;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

	public BigDecimal getPower() {
		return this.power;
	}

	public void setPower(BigDecimal power) {
		this.power = power;
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