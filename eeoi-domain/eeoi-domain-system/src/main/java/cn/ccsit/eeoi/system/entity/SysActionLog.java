package cn.ccsit.eeoi.system.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import cn.ccsit.eeoi.data.entity.CommonEntity;

import java.util.Date;


/**
 * The persistent class for the SYS_ACTION_LOG database table.
 * 
 */
@Entity
@Table(name="SYS_ACTION_LOG")
@NamedQuery(name="SysActionLog.findAll", query="SELECT s FROM SysActionLog s")
public class SysActionLog extends CommonEntity implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4207307003101817431L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(length=255)
	private String clazz;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TM")
	private Date createTm;

	@Column(length=128)
	private String creator;

	@Column(length=128)
	private String ipaddr;

	@Column(name="IS_DELETE")
	private int isDelete;

	@Column(name="LOG_TYPE", length=4)
	private String logType;

	@Column(name="MSG", length=4000)
	private String message;

	@Column(length=255)
	private String method;

	@Column(length=255)
	private String model;

	@Column(length=255)
	private String name;

	@Temporal(TemporalType.DATE)
	private Date opdate;

	@Column(length=128)
	private String opuser;

	@Column(name="RECORD_ID", length=128)
	private String recordId;

	public SysActionLog() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClazz() {
		return this.clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
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

	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
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

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

}