package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OI_SHIP_TFC", catalog = "")
public class OiShipTfc {
    private String id;
    private String shipid;
    private Integer averageSpeed;
    private String creator;
    private Date createTm;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    private BigDecimal boilerFuelCons;
    private BigDecimal auxFuelCons;
    private BigDecimal mainEngineFuelCons;
    private BigDecimal shipFuelCons;

    @Basic
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
    @Column(name = "SHIPID")
    public String getShipid() {
        return shipid;
    }

    public void setShipid(String shipid) {
        this.shipid = shipid;
    }

    @Basic
    @Column(name = "AVERAGE_SPEED")
    public Integer getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Integer averageSpeed) {
        this.averageSpeed = averageSpeed;
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
        OiShipTfc oiShipTfc = (OiShipTfc) o;
        return Objects.equals(id, oiShipTfc.id) &&
                Objects.equals(shipid, oiShipTfc.shipid) &&
                Objects.equals(averageSpeed, oiShipTfc.averageSpeed) &&
                Objects.equals(creator, oiShipTfc.creator) &&
                Objects.equals(createTm, oiShipTfc.createTm) &&
                Objects.equals(opuser, oiShipTfc.opuser) &&
                Objects.equals(opdate, oiShipTfc.opdate) &&
                Objects.equals(isDelete, oiShipTfc.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shipid,averageSpeed, creator, createTm, opuser, opdate, isDelete);
    }

    @Basic
    @Column(name = "BOILER_FUEL_CONS")
    public BigDecimal getBoilerFuelCons() {
        return boilerFuelCons;
    }

    public void setBoilerFuelCons(BigDecimal boilerFuelCons) {
        this.boilerFuelCons = boilerFuelCons;
    }

    @Basic
    @Column(name = "AUX_FUEL_CONS")
    public BigDecimal getAuxFuelCons() {
        return auxFuelCons;
    }

    public void setAuxFuelCons(BigDecimal auxFuelCons) {
        this.auxFuelCons = auxFuelCons;
    }

    @Basic
    @Column(name = "MAIN_ENGINE_FUEL_CONS")
    public BigDecimal getMainEngineFuelCons() {
        return mainEngineFuelCons;
    }

    public void setMainEngineFuelCons(BigDecimal mainEngineFuelCons) {
        this.mainEngineFuelCons = mainEngineFuelCons;
    }

    @Basic
    @Column(name = "SHIP_FUEL_CONS")
    public BigDecimal getShipFuelCons() {
        return shipFuelCons;
    }

    public void setShipFuelCons(BigDecimal shipFuelCons) {
        this.shipFuelCons = shipFuelCons;
    }
}
