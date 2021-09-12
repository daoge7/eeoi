package cn.ccsit.eeoi.ship.dto;

import cn.ccsit.eeoi.ship.entity.ShipEquipmentFuelMap;
import cn.ccsit.eeoi.ship.vo.ShipEquipmentFuelMapVo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class OiShipInfoDto {
    private List<String> shipIds;
    /**
     * 内河船id
     */
    private List<String> clientId;
    private List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps;

    public List<ShipEquipmentFuelMapVo> getShipEquipmentFuelMaps() {
        return shipEquipmentFuelMaps;
    }

    public void setShipEquipmentFuelMaps(List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps) {
        this.shipEquipmentFuelMaps = shipEquipmentFuelMaps;
    }

    /**
     * 船籍港
     */
    private String homePort;
    private String ownerAddr;

    public String getOwnerAddr() {
        return ownerAddr;
    }

    public void setOwnerAddr(String ownerAddr) {
        this.ownerAddr = ownerAddr;
    }

    public String getHomePort() {
        return homePort;
    }

    public void setHomePort(String homePort) {
        this.homePort = homePort;
    }

    public List<String> getClientId() {
        return clientId;
    }

    public void setClientId(List<String> clientId) {
        this.clientId = clientId;
    }

    public List<String> getShipIds() {
        return shipIds;
    }

    public void setShipIds(List<String> shipIds) {
        this.shipIds = shipIds;
    }

    private String id;

    private String affiliation;

    private String builder;

    private String builderId;

    private Date buildContractTm;

    public Date getBuildContractTm() {
        return buildContractTm;
    }

    public void setBuildContractTm(Date buildContractTm) {
        this.buildContractTm = buildContractTm;
    }

    private Integer cars;

    private String ccsno;
    @NotNull
    private BigDecimal serviceSpeed;

    public BigDecimal getServiceSpeed() {
        return serviceSpeed;
    }

    public void setServiceSpeed(BigDecimal serviceSpeed) {
        if(serviceSpeed == null){
            serviceSpeed = BigDecimal.ZERO;
        }
        this.serviceSpeed = serviceSpeed;
    }

    public String getFlagCode() {
        return flagCode;
    }

    public void setFlagCode(String flagCode) {
        this.flagCode = flagCode;
    }

    private String flagCode;

    private String certDate;

    private String certNo;

    private String certType;

    private String class_;

    private String classhis;

    private String consMethod;
    private BigDecimal eiv;
    public BigDecimal getEiv() {
        return eiv;
    }

    public void setEiv(BigDecimal eiv) {
        if(eiv == null){
            eiv = BigDecimal.ZERO;
        }
        this.eiv = eiv;
    }

    public String getConsMethod() {
        return consMethod;
    }

    public void setConsMethod(String consMethod) {
        this.consMethod = consMethod;
    }

    private Date contract;

    private String dataSource;

    private String docId;

    private String docmanager;
    @NotNull
    private BigDecimal dw;

    private BigDecimal eedi;

    private BigDecimal eedivalue;

    private BigDecimal eeoi;
    @NotBlank
    @Email
    private String email;

    private Date finish;

    private String firstname;

    private String flag;

    private String flaghis;

    private BigDecimal gross;

    private String ice;
    /**
     * imo号
     */
    @NotBlank
    private String imono;

    private String isScrubber;

    private String jobtitle;

    private Date lastwu;

    private BigDecimal net;

    private String operateType;

    private String operator;

    private String operatorId;

    private String operatorShip;

    private String owner;

    private String ownerId;

    private Integer passengers;

    private String registerno;

    private String remark;

    private Integer source;
    private BigDecimal speed;
    /**
     * 船舶英文名字
     */
    @NotBlank
    private String spname;
    /**
     * 船舶中文名字
     */
    @NotBlank
    private String spnameCn;

    private String sptype;

    private String sptypeSub;
    private String sptypeOther;

    public String getSptypeOther() {
        return sptypeOther;
    }

    public void setSptypeOther(String sptypeOther) {
        this.sptypeOther = sptypeOther;
    }

    private Integer status;

    private String surname;

    private Date takenTime;

    private String telephone;

    private Integer teus;
    /**
     * mrv联系人
     */
    private String title;

    private String uncertaintygrade;

    private Date updateTime;

    private String zxmon;

    public OiShipInfoDto() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAffiliation() {
        return this.affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getBuilder() {
        return this.builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public String getBuilderId() {
        return this.builderId;
    }

    public void setBuilderId(String builderId) {
        this.builderId = builderId;
    }

    public Integer getCars() {
        return this.cars;
    }

    public void setCars(Integer cars) {
        if(cars == null){
            cars = 0;
        }
        this.cars = cars;
    }

    public String getCcsno() {
        return this.ccsno;
    }

    public void setCcsno(String ccsno) {
        this.ccsno = ccsno;
    }

    public String getCertDate() {
        return this.certDate;
    }

    public void setCertDate(String certDate) {
        this.certDate = certDate;
    }

    public String getCertNo() {
        return this.certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertType() {
        return this.certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getClass_() {
        return this.class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getClasshis() {
        return this.classhis;
    }

    public void setClasshis(String classhis) {
        this.classhis = classhis;
    }

    public Date getContract() {
        return this.contract;
    }

    public void setContract(Date contract) {
        this.contract = contract;
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDocId() {
        return this.docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocmanager() {
        return this.docmanager;
    }

    public void setDocmanager(String docmanager) {
        this.docmanager = docmanager;
    }

    public BigDecimal getDw() {
        return this.dw;
    }

    public void setDw(BigDecimal dw) {
        this.dw = dw;
    }

    public BigDecimal getEedi() {
        return this.eedi;
    }

    public void setEedi(BigDecimal eedi) {
        if(eedi == null){
            eedi = BigDecimal.ZERO;
        }
        this.eedi = eedi;
    }

    public BigDecimal getEedivalue() {
        return eedivalue;
    }

    public void setEedivalue(BigDecimal eedivalue) {
        if(eedivalue == null){
            eedivalue = BigDecimal.ZERO;
        }
        this.eedivalue = eedivalue;
    }

    public BigDecimal getEeoi() {
        return this.eeoi;
    }

    public void setEeoi(BigDecimal eeoi) {
        this.eeoi = eeoi;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFinish() {
        return this.finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlaghis() {
        return this.flaghis;
    }

    public void setFlaghis(String flaghis) {
        this.flaghis = flaghis;
    }


    public String getIce() {
        return this.ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getImono() {
        return this.imono;
    }

    public void setImono(String imono) {
        this.imono = imono;
    }

    public String getIsScrubber() {
        return this.isScrubber;
    }

    public void setIsScrubber(String isScrubber) {
        this.isScrubber = isScrubber;
    }

    public String getJobtitle() {
        return this.jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public Date getLastwu() {
        return this.lastwu;
    }

    public void setLastwu(Date lastwu) {
        this.lastwu = lastwu;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }

    public String getOperateType() {
        return this.operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorShip() {
        return this.operatorShip;
    }

    public void setOperatorShip(String operatorShip) {
        this.operatorShip = operatorShip;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getPassengers() {
        return this.passengers;
    }

    public void setPassengers(Integer passengers) {
        if(passengers == null){
            passengers = 0;
        }
        this.passengers = passengers;
    }

    public String getRegisterno() {
        return this.registerno;
    }

    public void setRegisterno(String registerno) {
        this.registerno = registerno;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSource() {
        return this.source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public BigDecimal getSpeed() {
        return this.speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public String getSpname() {
        return this.spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

    public String getSpnameCn() {
        return this.spnameCn;
    }

    public void setSpnameCn(String spnameCn) {
        this.spnameCn = spnameCn;
    }

    public String getSptype() {
        return this.sptype;
    }

    public void setSptype(String sptype) {
        this.sptype = sptype;
    }

    public String getSptypeSub() {
        return this.sptypeSub;
    }

    public void setSptypeSub(String sptypeSub) {
        this.sptypeSub = sptypeSub;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getTakenTime() {
        return this.takenTime;
    }

    public void setTakenTime(Date takenTime) {
        this.takenTime = takenTime;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getTeus() {
        return this.teus;
    }

    public void setTeus(Integer teus) {
        if(teus == null){
            teus = 0;
        }
        this.teus = teus;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUncertaintygrade() {
        return this.uncertaintygrade;
    }

    public void setUncertaintygrade(String uncertaintygrade) {
        this.uncertaintygrade = uncertaintygrade;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getZxmon() {
        return this.zxmon;
    }

    public void setZxmon(String zxmon) {
        this.zxmon = zxmon;
    }

}