package cn.ccsit.eeoi.system.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import cn.ccsit.eeoi.data.entity.CommonEntity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SYS_USER_CLIENT_MAP database table.
 * 
 */
@Entity
@Table(name="SYS_USER_CLIENT_MAP")
@NamedQuery(name="SysUserClientMap.findAll", query="SELECT s FROM SysUserClientMap s")
public class SysUserClientMap extends CommonEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7523419371867183727L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name="USER_ID")
	private String userId;
	
	private String gcid;
	
	@Column(name="CLIENT_CODE")
	private String clientCode;
	
	@Column(name="CLIENT_IACS")
	private String clientIacs;
	
	private String creator;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TM")
	private Date createTm;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date opdate;

	private String opuser;
	
	@Column(name="IS_DELETE")
	private int isDelete;


	public SysUserClientMap() {
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getGcid() {
		return gcid;
	}


	public void setGcid(String gcid) {
		this.gcid = gcid;
	}


	public String getClientCode() {
		return clientCode;
	}


	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}


	public String getClientIacs() {
		return clientIacs;
	}


	public void setClientIacs(String clientIacs) {
		this.clientIacs = clientIacs;
	}


	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}


	public Date getCreateTm() {
		return createTm;
	}


	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}


	public Date getOpdate() {
		return opdate;
	}


	public void setOpdate(Date opdate) {
		this.opdate = opdate;
	}


	public String getOpuser() {
		return opuser;
	}


	public void setOpuser(String opuser) {
		this.opuser = opuser;
	}


	public int getIsDelete() {
		return isDelete;
	}


	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	

}