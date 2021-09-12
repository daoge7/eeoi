package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OI_SHIP_PROPELLER",  catalog = "")
public class OiShipPropeller {
    private String id;
    private String shipId;
    private String shipName;
    private String xh;
    private String ice;
    private String builder;
    private String turnTo;
    private Integer leafNum;
    private BigDecimal pitch;
    private BigDecimal weight;
    private String material;
    private String certNu;
    private Integer count;
    private String type;
    private Date takenTime;
    private String datatype;
    private String dataSource;
    private String creator;
    private Date createTm;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    private Integer diameter;
    @Basic
    @Column(name = "DIAMETER")
    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator = "idGenerator")
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
    @Column(name = "SHIP_NAME")
    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    @Basic
    @Column(name = "XH")
    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    @Basic
    @Column(name = "ICE")
    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    @Basic
    @Column(name = "BUILDER")
    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    @Basic
    @Column(name = "TURN_TO")
    public String getTurnTo() {
        return turnTo;
    }

    public void setTurnTo(String turnTo) {
        this.turnTo = turnTo;
    }

    @Basic
    @Column(name = "LEAF_NUM")
    public Integer getLeafNum() {
        return leafNum;
    }

    public void setLeafNum(Integer leafNum) {
        this.leafNum = leafNum;
    }

    @Basic
    @Column(name = "PITCH")
    public BigDecimal getPitch() {
        return pitch;
    }

    public void setPitch(BigDecimal pitch) {
        this.pitch = pitch;
    }

    @Basic
    @Column(name = "WEIGHT")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "MATERIAL")
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Basic
    @Column(name = "CERT_NU")
    public String getCertNu() {
        return certNu;
    }

    public void setCertNu(String certNu) {
        this.certNu = certNu;
    }

    @Basic
    @Column(name = "COUNT")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "TAKEN_TIME")
    public Date getTakenTime() {
        return takenTime;
    }

    public void setTakenTime(Date takenTime) {
        this.takenTime = takenTime;
    }

    @Basic
    @Column(name = "DATATYPE")
    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    @Basic
    @Column(name = "DATA_SOURCE")
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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
    @Column(name = "CREATE_TM")
    public Date getCreateTm() {
        return createTm;
    }

    public void setCreateTm(Date createTm) {
        this.createTm = createTm;
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
        OiShipPropeller that = (OiShipPropeller) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shipId, that.shipId) &&
                Objects.equals(shipName, that.shipName) &&
                Objects.equals(xh, that.xh) &&
                Objects.equals(ice, that.ice) &&
                Objects.equals(builder, that.builder) &&
                Objects.equals(turnTo, that.turnTo) &&
                Objects.equals(leafNum, that.leafNum) &&
                Objects.equals(pitch, that.pitch) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(material, that.material) &&
                Objects.equals(certNu, that.certNu) &&
                Objects.equals(count, that.count) &&
                Objects.equals(type, that.type) &&
                Objects.equals(takenTime, that.takenTime) &&
                Objects.equals(datatype, that.datatype) &&
                Objects.equals(dataSource, that.dataSource) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shipId, shipName, xh, ice, builder, turnTo, leafNum, pitch, weight, material, certNu, count, type, takenTime, datatype, dataSource, creator, createTm, opuser, opdate, isDelete);
    }
}
