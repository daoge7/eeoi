package cn.ccsit.eeoi.system.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import cn.ccsit.eeoi.data.entity.CommonEntity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SYS_ROLE_MENU database table.
 * 
 */
@Entity
@Table(name="SYS_ROLE_MENU")
@NamedQuery(name="SysRoleMenu.findAll", query="SELECT s FROM SysRoleMenu s")
public class SysRoleMenu extends CommonEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2380459421278692892L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TM")
	private Date createTm;

	private String creator;

	@Column(name="IS_DELETE")
	private int isDelete;

	@Column(name="MENU_ID")
	private String menuId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date opdate;

	private String opuser;

	@Column(name="ROLE_ID")
	private String roleId;

	public SysRoleMenu() {
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

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
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

}