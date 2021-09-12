package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the OI_SHIP_OTHER database table.
 * 
 */
@Entity
@Table(name="OI_SHIP_OTHER")
@NamedQuery(name="OiShipOther.findAll", query="SELECT o FROM OiShipOther o")
public class OiShipOther implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
	@GeneratedValue(generator = "idGenerator")
	private String id;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TM")
	private Date createTm;

	private String creator;

	@Column(name="EQUIPMENT_NAME")
	private String equipmentName;

	@Column(name="IS_DELETE")
	private Integer isDelete;

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Temporal(TemporalType.DATE)
	private Date opdate;

	private String opuser;

	private BigDecimal powner;

	@Column(name="SHIP_ID")
	private String shipId;

	@Column(name="TYPE")
	private String type;

	@Temporal(TemporalType.DATE)
	private Date updatedate;

	private String xh;

	public OiShipOther() {
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

	public String getEquipmentName() {
		return this.equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
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

	public String getShipId() {
		return this.shipId;
	}

	public void setShipId(String shipId) {
		this.shipId = shipId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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