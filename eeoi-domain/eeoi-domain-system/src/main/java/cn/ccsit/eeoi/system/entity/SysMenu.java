package cn.ccsit.eeoi.system.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import cn.ccsit.eeoi.data.entity.CommonEntity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SYS_MENU database table.
 * 
 */
@Entity
@Table(name="SYS_MENU")
@NamedQuery(name="SysMenu.findAll", query="SELECT s FROM SysMenu s")
public class SysMenu extends CommonEntity implements Serializable {


	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3797854995640419273L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TM")
	private Date createTm;

	private String creator;

	private String icon;

	@Column(name="IS_DELETE")
	private Integer isDelete;

	@Column(name="MENU_TYPE")
	private Integer menuType;

	@Temporal(TemporalType.DATE)
	private Date opdate;

	private String opuser;

	private String perms;

	private String pid;

	private String pids;

	private String remark;

	@Column(name="SEQ_NO")
	private Integer seqNo;

	@Column
	private Integer status;

	private String title;

	@Column(name="TITLE_EN")
	private String titleEn;
	
	private String url;

	public SysMenu() {
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

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getMenuType() {
		return this.menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
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

	public String getPerms() {
		return this.perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPids() {
		return this.pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}