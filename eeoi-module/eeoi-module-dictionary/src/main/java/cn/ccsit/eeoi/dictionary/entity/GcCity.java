package cn.ccsit.eeoi.dictionary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * @Author LuHao
 * @Date 2020-05-12 13:14:33 
 */
@Entity
@Table ( name ="GC_CITY" )
public class GcCity implements Serializable {

	private static final long serialVersionUID =  2816064369159807708L;

	@Id
   	@Column(name = "ID" )
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

   	@Column(name = "VERSION" )
	private Date version;

   	@Column(name = "STATE" )
	private String state;

   	@Column(name = "VALID" )
	private String valid;

   	@Column(name = "UPDATE_TIME" )
	private Date updateTime;

   	@Column(name = "UPDATE_USER" )
	private String updateUser;

   	@Column(name = "CODE" )
	private String code;

   	@Column(name = "EN_NAME" )
	private String enName;

   	@Column(name = "CN_NAME" )
	private String cnName;

   	@Column(name = "NATION_CN" )
	private String nationCn;

   	@Column(name = "NATION_EN" )
	private String nationEn;

   	@Column(name = "THREE_CODE" )
	private String threeCode;

   	@Column(name = "REMARK_INFO" )
	private String remarkInfo;

   	@Column(name = "BRANCH" )
	private String branch;

   	@Column(name = "LONGITUDE" )
	private String longitude;

    @Column(name = "LATITUDE" )
    private String latitude;

    @Column(name = "PROVINCE" )
    private String province;

   	@Column(name = "CREATOR" )
	private String creator;

   	@Column(name = "CREATE_TM" )
	private Date createTm;

	/**
	 * 原字段：OPERATOR
	 */
   	@Column(name = "OPUSER" )
	private String opuser;

	/**
	 * 原字段：UPDATE_TIME
	 */
   	@Column(name = "OPDATE" )
	private Date opdate;

	/**
	 * 0:正常  1:删除
	 */
   	@Column(name = "IS_DELETE" )
	private String isDelete;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public Date getVersion() {
    return version;
  }

  public void setVersion(Date version) {
    this.version = version;
  }


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }


  public String getValid() {
    return valid;
  }

  public void setValid(String valid) {
    this.valid = valid;
  }


  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }


  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  public String getEnName() {
    return enName;
  }

  public void setEnName(String enName) {
    this.enName = enName;
  }


  public String getCnName() {
    return cnName;
  }

  public void setCnName(String cnName) {
    this.cnName = cnName;
  }


  public String getNationCn() {
    return nationCn;
  }

  public void setNationCn(String nationCn) {
    this.nationCn = nationCn;
  }


  public String getNationEn() {
    return nationEn;
  }

  public void setNationEn(String nationEn) {
    this.nationEn = nationEn;
  }


  public String getThreeCode() {
    return threeCode;
  }

  public void setThreeCode(String threeCode) {
    this.threeCode = threeCode;
  }


  public String getRemarkInfo() {
    return remarkInfo;
  }

  public void setRemarkInfo(String remarkInfo) {
    this.remarkInfo = remarkInfo;
  }


  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }


  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
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


  public String getOpuser() {
    return opuser;
  }

  public void setOpuser(String opuser) {
    this.opuser = opuser;
  }


  public Date getOpdate() {
    return opdate;
  }

  public void setOpdate(Date opdate) {
    this.opdate = opdate;
  }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(String isDelete) {
    this.isDelete = isDelete;
  }

}
