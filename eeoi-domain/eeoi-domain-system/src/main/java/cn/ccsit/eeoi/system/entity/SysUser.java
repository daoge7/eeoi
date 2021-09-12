package cn.ccsit.eeoi.system.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import cn.ccsit.eeoi.ship.entity.GcClient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_USER")
@NamedQuery(name = "SysUser.findAll", query = "SELECT s FROM SysUser s")
public class SysUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6531767305395882725L;

	@Id
	@Column(name = "ID")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TM")
	private Date createTm;

	private String creator;

	@Column(name = "DEPT_ID")
	private String deptId;

	private String email;

	@Column(name = "IS_DELETE")
	private int isDelete;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_TIME")
	private Date lastTime;

	private String nickname;

	@Column(name = "NICKNAME_EN")
	private String nicknameEn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date opdate;

	private String opuser;

	private String password;

	private String phone;

	private String picture;

	@Column(name = "PWDERR_TIMES")
	private Integer pwderrTimes;

	private String remark;

	private String salt;

	private String sex;

	@Column(name = "STAFF_DEP")
	private String staffDep;

	private int status;

	@Column(name = "USER_TYPE")
	private int userType;

	private String username;

	public SysUser() {
		// companys=new ArrayList<GcClient>();
		// userRoles = null;
	}

	public SysUser(SysUser c) {
		this.createTm = c.getCreateTm();
		this.creator = c.getCreator();
		this.deptId = c.getDeptId();
		this.email = c.getEmail();
		this.id = c.getId();
		this.isDelete = c.getIsDelete();
		this.lastTime = c.getLastTime();
		this.nickname = c.getNickname();
		this.nicknameEn = c.getNicknameEn();
		this.opdate = c.getOpdate();
		this.opuser = c.getOpuser();
		this.password = c.getPassword();
		this.phone = c.getPhone();
		this.status = c.getStatus();
		this.username = c.getUsername();
		this.companys = c.getCompanys();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNicknameEn() {
		return nicknameEn;
	}

	public void setNicknameEn(String nicknameEn) {
		this.nicknameEn = nicknameEn;
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

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Date getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getPwderrTimes() {
		return pwderrTimes;
	}

	public void setPwderrTimes(Integer pwderrTimes) {
		this.pwderrTimes = pwderrTimes;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStaffDep() {
		return this.staffDep;
	}

	public void setStaffDep(String staffDep) {
		this.staffDep = staffDep;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserType() {
		return this.userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	//@ManyToMany
	@ManyToMany(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinTable(name = "sys_user_client_map", foreignKey = @ForeignKey(name = "null"), joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "gcid", referencedColumnName = "id") })
	private Set<GcClient> companys = new HashSet<GcClient>();

	public Set<GcClient> getCompanys() {
		return companys;
	}

	public void setCompanys(Set<GcClient> companys) {
		this.companys = companys;
	}

	public Set<SysRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<SysRole> userRoles) {
		this.userRoles = userRoles;
	}

	@ManyToMany(cascade=CascadeType.REFRESH)
	@JoinTable(name = "sys_user_role", foreignKey = @ForeignKey(name = "null"), joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private Set<SysRole> userRoles = new HashSet<SysRole>();
}