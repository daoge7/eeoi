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
 * @Date 2020-05-26 14:06:54 
 */
@Entity
@Table ( name ="DIC_FUEL" )
public class Fuel  implements Serializable {

	private static final long serialVersionUID =  221184637495497402L;

	/**
	 * guid
	 */
	@Id
   	@Column(name = "ID" )
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

   	@Column(name = "FUEL_CODE" )
	private String fuelCode;

   	@Column(name = "FUEL_NAME" )
	private String fuelName;

	/**
	 * 百分比，如0.87，即为87%
	 */
   	@Column(name = "CARBON_CONTEN" )
	private String carbonConten;

   	@Column(name = "CONVERSION" )
	private String conversion;

   	@Column(name = "REMARK" )
	private String remark;

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


  public String getFuelCode() {
    return fuelCode;
  }

  public void setFuelCode(String fuelCode) {
    this.fuelCode = fuelCode;
  }


  public String getFuelName() {
    return fuelName;
  }

  public void setFuelName(String fuelName) {
    this.fuelName = fuelName;
  }


  public String getCarbonConten() {
    return carbonConten;
  }

  public void setCarbonConten(String carbonConten) {
    this.carbonConten = carbonConten;
  }


  public String getConversion() {
    return conversion;
  }

  public void setConversion(String conversion) {
    this.conversion = conversion;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
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
