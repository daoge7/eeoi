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
 * @Date 2020-06-02 14:08:39 
 */
@Entity
@Table ( name ="GC_CLASSIFICAT" )
public class Classificat  implements Serializable {

	private static final long serialVersionUID =  1881220456810280888L;

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

   	@Column(name = "IACS" )
	private String iacs;

   	@Column(name = "IACSNOIACS" )
	private String iacsnoiacs;

   	@Column(name = "ABBRN" )
	private String abbrn;

   	@Column(name = "FULLNAME" )
	private String fullname;

   	@Column(name = "NATION" )
	private String nation;

   	@Column(name = "ADDR" )
	private String addr;

   	@Column(name = "PHONE" )
	private String phone;

   	@Column(name = "FAX" )
	private String fax;

   	@Column(name = "TELEX" )
	private String telex;

   	@Column(name = "EMAIL" )
	private String email;

   	@Column(name = "WEBSITE" )
	private String website;

   	@Column(name = "TELEGRAPH" )
	private String telegraph;

   	@Column(name = "CFULLNAME" )
	private String cfullname;

   	@Column(name = "CADDR" )
	private String caddr;

   	@Column(name = "POSTCODE" )
	private String postcode;

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


  public String getIacs() {
    return iacs;
  }

  public void setIacs(String iacs) {
    this.iacs = iacs;
  }


  public String getIacsnoiacs() {
    return iacsnoiacs;
  }

  public void setIacsnoiacs(String iacsnoiacs) {
    this.iacsnoiacs = iacsnoiacs;
  }


  public String getAbbrn() {
    return abbrn;
  }

  public void setAbbrn(String abbrn) {
    this.abbrn = abbrn;
  }


  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }


  public String getNation() {
    return nation;
  }

  public void setNation(String nation) {
    this.nation = nation;
  }


  public String getAddr() {
    return addr;
  }

  public void setAddr(String addr) {
    this.addr = addr;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }


  public String getTelex() {
    return telex;
  }

  public void setTelex(String telex) {
    this.telex = telex;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }


  public String getTelegraph() {
    return telegraph;
  }

  public void setTelegraph(String telegraph) {
    this.telegraph = telegraph;
  }


  public String getCfullname() {
    return cfullname;
  }

  public void setCfullname(String cfullname) {
    this.cfullname = cfullname;
  }


  public String getCaddr() {
    return caddr;
  }

  public void setCaddr(String caddr) {
    this.caddr = caddr;
  }


  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
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
