package cn.ccsit.eeoi.system.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import cn.ccsit.eeoi.data.entity.CommonEntity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SYS_USER_ROLE database table.
 * 
 */
@Entity
@Table(name="SYS_USER_ROLE")
@NamedQuery(name="SysUserRole.findAll", query="SELECT s FROM SysUserRole s")
public class SysUserRole extends CommonEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4477255262280920896L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Temporal(TemporalType.TIME)
	@Column(name="CREATE_TM")
	private Date createTm;

	private String creator;

	@Column(name="IS_DELETE")
	private int isDelete;

	@Temporal(TemporalType.TIME)
	private Date opdate;

	private String opuser;

	@Column(name="ROLE_ID", nullable=false)
	private String roleId;

	@Column(name="USER_ID", nullable=false)
	private String userId;

	public SysUserRole() {
	}


	public String getId() {
		return id;
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

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}