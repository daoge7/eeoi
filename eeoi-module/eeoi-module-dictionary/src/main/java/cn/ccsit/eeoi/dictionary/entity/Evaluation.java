package cn.ccsit.eeoi.dictionary.entity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
/** 
 * @Author LuHao 
 * @Date 2020-05-27 14:55:46 
 */
@Entity
@Table ( name ="DIC_EVALUATION" )
public class Evaluation  implements Serializable {

	private static final long serialVersionUID =  4878137750148709233L;

	@Id
   	@Column(name = "ID" )
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

   	@Column(name = "PARAMNAME" )
	private String paramname;

   	@Column(name = "PARAMVALUE" )
	private BigDecimal paramvalue;

   	@Column(name = "UPDATEDATE" )
	private Date updatedate;

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


  public String getParamname() {
    return paramname;
  }

  public void setParamname(String paramname) {
    this.paramname = paramname;
  }


  public BigDecimal getParamvalue() {
    return paramvalue;
  }

  public void setParamvalue(BigDecimal paramvalue) {
    this.paramvalue = paramvalue;
  }


  public Date getUpdatedate() {
    return updatedate;
  }

  public void setUpdatedate(Date updatedate) {
    this.updatedate = updatedate;
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
