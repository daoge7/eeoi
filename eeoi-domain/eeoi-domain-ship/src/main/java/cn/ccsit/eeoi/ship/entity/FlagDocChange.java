package cn.ccsit.eeoi.ship.entity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/** 
 * @Author LuHao 
 * @Date 2020-07-30 10:15:20 
 */
@Entity
@Table ( name = "FLAG_DOC_CHANGE")
public class FlagDocChange  implements Serializable {

	private static final long serialVersionUID =  3814807975229604977L;

	@Id
   	@Column(name = "ID" )
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

   	@Column(name = "IMO_NO" )
	private String imoNo;

   	@Column(name = "SHIP_ID" )
	private String shipId;

   	@Column(name = "SHIP_NAME" )
	private String shipName;

   	@Column(name = "SHIP_NAME_EC" )
	private String shipNameEc;

   	@Column(name = "CHANGE_TYPE" )
	private String changeType;// 1:是船旗国变更 2：是doc变更

   	@Column(name = "DOC_OLD" )
	private String docOld;

   	@Column(name = "DOC_NEW" )
	private String docNew;

   	@Column(name = "FLAG_THREE_CODE_OLD")
	private String flagThreeCodeOld;

   	@Column(name = "FLAG_THREE_CODE_NEW")
	private String flagThreeCodeNew;

   	@Column(name = "EFFECTIVE_DATE" )
	private Date effectiveDate;

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

    @Transient
    private String oldDocName;

    @Transient
    private String newDocName;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getImoNo() {
    return imoNo;
  }

  public void setImoNo(String imoNo) {
    this.imoNo = imoNo;
  }


  public String getShipId() {
    return shipId;
  }

  public void setShipId(String shipId) {
    this.shipId = shipId;
  }


  public String getShipName() {
    return shipName;
  }

  public void setShipName(String shipName) {
    this.shipName = shipName;
  }


  public String getShipNameEc() {
    return shipNameEc;
  }

  public void setShipNameEc(String shipNameEc) {
    this.shipNameEc = shipNameEc;
  }


  public String getChangeType() {
    return changeType;
  }

  public void setChangeType(String changeType) {
    this.changeType = changeType;
  }


  public String getDocOld() {
    return docOld;
  }

  public void setDocOld(String docOld) {
    this.docOld = docOld;
  }


  public String getDocNew() {
    return docNew;
  }

  public void setDocNew(String docNew) {
    this.docNew = docNew;
  }


  public String getFlagThreeCodeOld() {
    return flagThreeCodeOld;
  }

  public void setFlagThreeCodeOld(String flagThreeCodeOld) {
    this.flagThreeCodeOld = flagThreeCodeOld;
  }


  public String getFlagThreeCodeNew() {
    return flagThreeCodeNew;
  }

  public void setFlagThreeCodeNew(String flagThreeCodeNew) {
    this.flagThreeCodeNew = flagThreeCodeNew;
  }


  public Date getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(Date effectiveDate) {
    this.effectiveDate = effectiveDate;
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

    public String getOldDocName() {
        return oldDocName;
    }

    public void setOldDocName(String oldDocName) {
        this.oldDocName = oldDocName;
    }

    public String getNewDocName() {
        return newDocName;
    }

    public void setNewDocName(String newDocName) {
        this.newDocName = newDocName;
    }
}
