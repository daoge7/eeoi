package cn.ccsit.eeoi.dictionary.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;
/** 
 * @Author LuHao 
 * @Date 2020-06-03 16:25:03 
 */
@Entity
@Table ( name ="GC_SHIP_TYPE" )
public class ShipType  implements Serializable {

	private static final long serialVersionUID =  2984251052830724802L;

	@Id
   	@Column(name = "ID" )
	private String id;

   	@Column(name = "SPCODE" )
	private String spcode;

   	@Column(name = "SPTYPE" )
	private String sptype;

   	@Column(name = "CSPTYPE" )
	private String csptype;

   	@Column(name = "IMO_SPTYPE" )
	private String imoSptype;

   	@Column(name = "EU_SPTYPE" )
	private String euSptype;

   	@Column(name = "REMARK_INFO" )
	private String remarkInfo;

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


  public String getSpcode() {
    return spcode;
  }

  public void setSpcode(String spcode) {
    this.spcode = spcode;
  }


  public String getSptype() {
    return sptype;
  }

  public void setSptype(String sptype) {
    this.sptype = sptype;
  }


  public String getCsptype() {
    return csptype;
  }

  public void setCsptype(String csptype) {
    this.csptype = csptype;
  }


  public String getImoSptype() {
    return imoSptype;
  }

  public void setImoSptype(String imoSptype) {
    this.imoSptype = imoSptype;
  }


  public String getEuSptype() {
    return euSptype;
  }

  public void setEuSptype(String euSptype) {
    this.euSptype = euSptype;
  }


  public String getRemarkInfo() {
    return remarkInfo;
  }

  public void setRemarkInfo(String remarkInfo) {
    this.remarkInfo = remarkInfo;
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
