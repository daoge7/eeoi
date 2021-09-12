package cn.ccsit.eeoi.system.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "OI_SHIP_CHGLOG", catalog = "")
public class OiShipChglog {
    private String id;
    private String shipId;
    private String imono;
    private String shipName;
    private Integer chgType;
    private String preFlag;
    private String newFlag;
    private String preDocId;
    private String newDocId;
    private Date effectiveDate;
    private String creator;
    private Date createDate;
    private String opuser;
    private Date opdate;
    private Integer isDelete;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SHIP_ID")
    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    @Basic
    @Column(name = "IMONO")
    public String getImono() {
        return imono;
    }

    public void setImono(String imono) {
        this.imono = imono;
    }

    @Basic
    @Column(name = "SHIP_NAME")
    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    @Basic
    @Column(name = "CHG_TYPE")
    public Integer getChgType() {
        return chgType;
    }

    public void setChgType(Integer chgType) {
        this.chgType = chgType;
    }

    @Basic
    @Column(name = "PRE_FLAG")
    public String getPreFlag() {
        return preFlag;
    }

    public void setPreFlag(String preFlag) {
        this.preFlag = preFlag;
    }

    @Basic
    @Column(name = "NEW_FLAG")
    public String getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(String newFlag) {
        this.newFlag = newFlag;
    }

    @Basic
    @Column(name = "PRE_DOC_ID")
    public String getPreDocId() {
        return preDocId;
    }

    public void setPreDocId(String preDocId) {
        this.preDocId = preDocId;
    }

    @Basic
    @Column(name = "NEW_DOC_ID")
    public String getNewDocId() {
        return newDocId;
    }

    public void setNewDocId(String newDocId) {
        this.newDocId = newDocId;
    }

    @Basic
    @Column(name = "EFFECTIVE_DATE")
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Basic
    @Column(name = "CREATOR")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "OPUSER")
    public String getOpuser() {
        return opuser;
    }

    public void setOpuser(String opuser) {
        this.opuser = opuser;
    }

    @Basic
    @Column(name = "OPDATE")
    public Date getOpdate() {
        return opdate;
    }

    public void setOpdate(Date opdate) {
        this.opdate = opdate;
    }

    @Basic
    @Column(name = "IS_DELETE")
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OiShipChglog that = (OiShipChglog) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shipId, that.shipId) &&
                Objects.equals(imono, that.imono) &&
                Objects.equals(shipName, that.shipName) &&
                Objects.equals(chgType, that.chgType) &&
                Objects.equals(preFlag, that.preFlag) &&
                Objects.equals(newFlag, that.newFlag) &&
                Objects.equals(preDocId, that.preDocId) &&
                Objects.equals(newDocId, that.newDocId) &&
                Objects.equals(effectiveDate, that.effectiveDate) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shipId, imono, shipName, chgType, preFlag, newFlag, preDocId, newDocId, effectiveDate, creator, createDate, opuser, opdate, isDelete);
    }
}
