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
 * @Date 2020-05-28 16:08:31 
 */
@Entity
@Table ( name ="DIC_ENERGY_FORM" )
public class EnergyForm  implements Serializable {

	private static final long serialVersionUID =  5272924054237075906L;

	@Id
   	@Column(name = "ID" )
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

   	@Column(name = "FORM_CODE" )
	private String formCode;

   	@Column(name = "FORM_NAME" )
	private String formName;

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


  public String getFormCode() {
    return formCode;
  }

  public void setFormCode(String formCode) {
    this.formCode = formCode;
  }


  public String getFormName() {
    return formName;
  }

  public void setFormName(String formName) {
    this.formName = formName;
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
