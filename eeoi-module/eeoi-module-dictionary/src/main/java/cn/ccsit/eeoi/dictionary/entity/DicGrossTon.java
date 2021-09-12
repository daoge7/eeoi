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
 * @Date 2020-05-20 16:25:47
 */
@Entity
@Table ( name ="DIC_GROSS_TON" )
public class DicGrossTon  implements Serializable {

    private static final long serialVersionUID =  2237228725356472541L;

    @Id
    @Column(name = "ID" )
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "TON_SMALL" )
    private String tonSmall;

    @Column(name = "TON_BIG" )
    private String tonBig;

    @Column(name = "EXPLAIN" )
    private String explain;

    @Column(name = "OPERATOR_ID" )
    private String operatorId;

    @Column(name = "UPDATE_TIME" )
    private Date updateTime;

    @Column(name = "STATE" )
    private String state;

    @Column(name = "TON_CODE" )
    private String tonCode;

    @Column(name = "TON_NAME" )
    private String tonName;

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


    public String getTonSmall() {
        return tonSmall;
    }

    public void setTonSmall(String tonSmall) {
        this.tonSmall = tonSmall;
    }


    public String getTonBig() {
        return tonBig;
    }

    public void setTonBig(String tonBig) {
        this.tonBig = tonBig;
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


    public String getTonCode() {
        return tonCode;
    }

    public void setTonCode(String tonCode) {
        this.tonCode = tonCode;
    }


    public String getTonName() {
        return tonName;
    }

    public void setTonName(String tonName) {
        this.tonName = tonName;
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
