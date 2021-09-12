package cn.ccsit.eeoi.dictionary.entity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;
/** 
 * @Author LuHao 
 * @Date 2020-05-29 15:28:24 
 */
@Entity
@Table ( name ="GC_STATE" )
public class GcState  implements Serializable {

	private static final long serialVersionUID =  6416576373261033959L;

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

   	@Column(name = "SORTNO" )
	private String sortno;

   	@Column(name = "CN_BRIEF" )
	private String cnBrief;

   	@Column(name = "EN_BRIEF" )
	private String enBrief;

   	@Column(name = "TWO_CODE" )
	private String twoCode;

   	@Column(name = "THREE_CODE" )
	private String threeCode;

   	@Column(name = "NUMBER_CODE" )
	private String numberCode;

   	@Column(name = "CN_NAME" )
	private String cnName;

   	@Column(name = "EN_NAME" )
	private String enName;

   	@Column(name = "REMARK_INFO" )
	private String remarkInfo;

   	@Column(name = "AUTH_FLAG" )
	private String authFlag;

   	@Column(name = "CREATOR" )
	private String creator;

   	@Column(name = "CREATE_TM" )
	private Date createTm;

   	@Column(name = "OPUSER" )
	private String opuser;

   	@Column(name = "OPDATE" )
	private Date opdate;

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


  public String getSortno() {
    return sortno;
  }

  public void setSortno(String sortno) {
    this.sortno = sortno;
  }


  public String getCnBrief() {
    return cnBrief;
  }

  public void setCnBrief(String cnBrief) {
    this.cnBrief = cnBrief;
  }


  public String getEnBrief() {
    return enBrief;
  }

  public void setEnBrief(String enBrief) {
    this.enBrief = enBrief;
  }


  public String getTwoCode() {
    return twoCode;
  }

  public void setTwoCode(String twoCode) {
    this.twoCode = twoCode;
  }


  public String getThreeCode() {
    return threeCode;
  }

  public void setThreeCode(String threeCode) {
    this.threeCode = threeCode;
  }


  public String getNumberCode() {
    return numberCode;
  }

  public void setNumberCode(String numberCode) {
    this.numberCode = numberCode;
  }


  public String getCnName() {
    return cnName;
  }

  public void setCnName(String cnName) {
    this.cnName = cnName;
  }


  public String getEnName() {
    return enName;
  }

  public void setEnName(String enName) {
    this.enName = enName;
  }


  public String getRemarkInfo() {
    return remarkInfo;
  }

  public void setRemarkInfo(String remarkInfo) {
    this.remarkInfo = remarkInfo;
  }


  public String getAuthFlag() {
    return authFlag;
  }

  public void setAuthFlag(String authFlag) {
    this.authFlag = authFlag;
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


  public String getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(String isDelete) {
    this.isDelete = isDelete;
  }

}
