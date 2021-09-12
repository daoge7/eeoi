package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the oi_ship_boiler database table.
 * 
 */
@Entity
@Table(name="oi_ship_boiler")
@NamedQuery(name="OiShipBoiler.findAll", query="SELECT o FROM OiShipBoiler o")
public class OiShipBoiler implements Serializable {
	private static final long serialVersionUID = 1L;

	public OiShipBoiler(String shipId) {
		this.shipId = shipId;
	}

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

	private String about;

	@Column(name="boiler_sub")
	private String boilerSub;

	@Column(name="boiler_type")
	private String boilerType;

	private String energyId;

	private String operator;
	@Column(name = "MFRS_NAME")
	private String mfrsName;
	@Column(name = "IS_DELETE")
	private Integer isDelete;
	@Column(name = "CREATE_TM")
	private Date createTm;

	public Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getMfrsName() {
		return mfrsName;
	}

	public void setMfrsName(String mfrsName) {
		this.mfrsName = mfrsName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="produce_time")
	private Date produceTime;

	@Column(name="ship_id")
	private String shipId;

	@Column(name="ship_name")
	private String shipName;

	private String source;

	private Integer status;

	@Temporal(TemporalType.DATE)
	@Column(name="taken_time")
	private Date takenTime;

	@Temporal(TemporalType.DATE)
	@Column(name="update_time")
	private Date updateTime;

	private String xh;

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public OiShipBoiler() {
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

	public String getBoilerSub() {
		return this.boilerSub;
	}

	public void setBoilerSub(String boilerSub) {
		this.boilerSub = boilerSub;
	}

	public String getBoilerType() {
		return this.boilerType;
	}

	public void setBoilerType(String boilerType) {
		this.boilerType = boilerType;
	}

	public String getEnergyId() {
		return this.energyId;
	}

	public void setEnergyId(String energyId) {
		this.energyId = energyId;
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

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}