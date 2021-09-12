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
 * @Date 2020-06-03 14:21:00 
 */
@Entity
@Table ( name ="DIC_SHIP_SUB" )
public class ShipSub  implements Serializable {

	private static final long serialVersionUID =  1965583549178753444L;

	@Id
   	@Column(name = "ID" )
	private String id;

   	@Column(name = "SHIP_TYPE" )
	private String shipType;

   	@Column(name = "SUB_CODE" )
	private String subCode;

   	@Column(name = "SUB_NAME" )
	private String subName;

   	@Column(name = "EXPLAIN" )
	private String explain;

   	@Column(name = "OPERATOR_ID" )
	private String operatorId;

   	@Column(name = "UPDATE_TIME" )
	private Date updateTime;

   	@Column(name = "STATE" )
	private String state;

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


  public String getShipType() {
    return shipType;
  }

  public void setShipType(String shipType) {
    this.shipType = shipType;
  }


  public String getSubCode() {
    return subCode;
  }

  public void setSubCode(String subCode) {
    this.subCode = subCode;
  }


  public String getSubName() {
    return subName;
  }

  public void setSubName(String subName) {
    this.subName = subName;
  }


  public String getExplain() {
    return explain;
  }

  public void setExplain(String explain) {
    this.explain = explain;
  }


  public String getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }


  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
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
