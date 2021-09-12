package cn.ccsit.eeoi.system.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import cn.ccsit.eeoi.data.entity.CommonEntity;

import java.util.Date;


/**
 * The persistent class for the SYS_DICT database table.
 * 
 */
@Entity
@Table(name="SYS_DICT")
@NamedQuery(name="SysDict.findAll", query="SELECT s FROM SysDict s")
public class SysDict extends CommonEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8294440470911693417L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	@Column(unique=true, nullable=false, length=50)
	private String id;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TM")
	private Date createTm;

	@Column(length=128)
	private String creator;

	@Column(name="DIC_TYPE", length=4)
	private String dicType;

	@Column(name="IS_DELETE")
	private int isDelete;

	@Column(length=255)
	private String name;

	@Temporal(TemporalType.DATE)
	private Date opdate;

	@Column(length=128)
	private String opuser;

	@Column(length=255)
	private String remark;

	@Column
	private Integer status;

	@Column(length=255)
	private String title;

	@Column(length=2000)
	private String val;

	public SysDict() {
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

	public String getDicType() {
		return this.dicType;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVal() {
		return this.val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}