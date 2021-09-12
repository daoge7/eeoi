package cn.ccsit.eeoi.system.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import cn.ccsit.eeoi.data.entity.CommonEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the SYS_ROLE database table.
 * 
 */
@Entity
@Table(name = "SYS_ROLE")
@NamedQuery(name = "SysRole.findAll", query = "SELECT s FROM SysRole s")
public class SysRole extends CommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4662598439133330284L;

	@Id
//	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
//	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TM")
	private Date createTm;

	private String creator;

	@Column(name = "IS_DELETE")
	private int isDelete;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	private Date opdate;

	private String opuser;

	private String remark;

	private int status;

	private String title;

	public SysRole() {
	}

	public SysRole(SysRole sysRole) {
		this.id=sysRole.getId();
		this.createTm=sysRole.getCreateTm();
		this.creator=sysRole.getCreator();
		this.isDelete=sysRole.getIsDelete();
		this.name=sysRole.getName();
		this.opdate=sysRole.getOpdate();
		this.opuser=sysRole.getOpuser();
		this.remark=sysRole.getRemark();
		this.status=sysRole.getStatus();
		this.title=sysRole.getTitle();
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

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}