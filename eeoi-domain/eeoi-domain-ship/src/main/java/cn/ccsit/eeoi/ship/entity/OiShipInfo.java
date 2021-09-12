package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the oi_ship_info database table.
 */
@Entity
@Table(name = "oi_ship_info")
@NamedQuery(name = "OiShipInfo.findAll", query = "SELECT o FROM OiShipInfo o")
public class OiShipInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator = "idGenerator")
    private String id;

    private String affiliation;

    private String builder;

    @Column(name = "builder_id")
    private String builderId;

    private Integer cars;
    private BigDecimal depth;
    private BigDecimal width;
    private BigDecimal length;

    public BigDecimal getDepth() {
        return depth;
    }

    public void setDepth(BigDecimal depth) {
        this.depth = depth;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getLbp() {
        return lbp;
    }

    public void setLbp(BigDecimal lbp) {
        this.lbp = lbp;
    }

    private BigDecimal lbp;
    /**
     * 是否 是内河船
     */
    @Column(name = "IS_CMSA")
    private Integer isCmsa;

    public Integer getIsCmsa() {
        return isCmsa;
    }

    public void setIsCmsa(Integer isCmsa) {
        this.isCmsa = isCmsa;
    }

    @Column(name = "DOC_CUID")

    private String docCuid;

    public String getDocCuid() {
        return docCuid;
    }

    public void setDocCuid(String docCuid) {
        this.docCuid = docCuid;
    }

    private String ccsno;

    @Column(name = "cert_date")
    private String certDate;

    @Column(name = "cert_no")
    private String certNo;

    @Column(name = "cert_type")
    private String certType;

    @Column(name = "class")
    private String class_;

    @Column(name = "HOME_PORT")
    private String homePort;

    public String getHomePort() {
        return homePort;
    }

    public void setHomePort(String homePort) {
        this.homePort = homePort;
    }

    @Column(name = "BUILD_CONTRACT_TM")
    private Date buildContractTm;

    @Column(name = "CONS_MENTHOD")
    private String consMethod;

    public String getConsMethod() {
        return consMethod;
    }

    public void setConsMethod(String consMethod) {
        this.consMethod = consMethod;
    }

    public Date getBuildContractTm() {
        return buildContractTm;
    }

    public void setBuildContractTm(Date buildContractTm) {
        this.buildContractTm = buildContractTm;
    }

    @Column(name = "DATA_SOURCE")
    private String dataSource;

    @Column(name = "doc_id")
    private String docId;

    private String docmanager;
    @Column(name = "DOC_IACS")
    private String docIacs;

    public String getDocIacs() {
        return docIacs;
    }

    public void setDocIacs(String docIacs) {
        this.docIacs = docIacs;
    }

    private BigDecimal dw;

    private BigDecimal eedi;
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public BigDecimal getEiv() {
        return eiv;
    }

    public void setEiv(BigDecimal eiv) {
        this.eiv = eiv;
    }

    private BigDecimal eiv;

    private BigDecimal eedivalue;

    private BigDecimal eeoi;

    private String email;

    private Date finish;

    private String firstname;

    private String flag;
    @Column(name = "FLAG_CODE")
    private String flagCode;

    public String getFlagCode() {
        return flagCode;
    }

    public void setFlagCode(String flagCode) {
        this.flagCode = flagCode;
    }

    private BigDecimal gross;

    private String ice;

    private String imono;

    @Column(name = "IS_SCRUBBER")
    private String isScrubber;

    private String jobtitle;

    private Date lastwu;

    private BigDecimal net;

    @Column(name = "OPERATE_TYPE")
    private String operateType;

    @Column(name = "operator_id")
    private String operatorId;

    @Column(name = "operator_ship")
    private String operatorShip;

    private String owner;
    @Column(name = "OWNER_ADDR")
    private String ownerAddr;

    public String getOwnerAddr() {
        return ownerAddr;
    }

    public void setOwnerAddr(String ownerAddr) {
        this.ownerAddr = ownerAddr;
    }

    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "app_user")
    private String appUser;

    private Integer passengers;

    private String registerno;

    private String remark;

    private Integer source;

    private BigDecimal speed;
    @Column(name = "SERVICE_SPEED")
    private BigDecimal serviceSpeed;

    public BigDecimal getServiceSpeed() {
        return serviceSpeed;
    }

    public void setServiceSpeed(BigDecimal serviceSpeed) {
        this.serviceSpeed = serviceSpeed;
    }

    private String spname;

    @Column(name = "spname_cn")
    private String spnameCn;

    private String sptype;

    @Column(name = "sptype_sub")
    private String sptypeSub;
    @Column(name = "SPTYPE_OTHER")
    private String sptypeOther;

    public String getSptypeOther() {
        return sptypeOther;
    }

    public void setSptypeOther(String sptypeOther) {
        this.sptypeOther = sptypeOther;
    }

    private Integer status;

    private String surname;

    @Column(name = "taken_time")
    private Date takenTime;

    private String telephone;

    private Integer teus;

    private String title;

    private String uncertaintygrade;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "CREATE_TM")
    private Date createTm;
    @Column(name = "CREATOR")
    private String creator;
    @OneToMany(mappedBy = "shipId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OiShipCmsaMap> oiShipCmsaMaps;
    @OneToMany(mappedBy = "shipId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "EQUIPMENT_ID IS NULL")
    private List<ShipEquipmentFuelMap> shipEquipmentFuelMaps;
    @OneToMany(mappedBy = "shipId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    private List<OiMainEngine> oiMainEngines;
    @OneToMany(mappedBy = "shipId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    private List<OiShipGe> oiShipGes;
    @OneToMany(mappedBy = "shipId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    private List<OiShipGenerator> oiShipGenerators;
    @OneToMany(mappedBy = "shipId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "IS_DELETE=0")
    private List<OiShipBoiler> oiShipBoilers;
    public List<OiShipBoiler> getOiShipBoilers() {
        return oiShipBoilers;
    }

    public void setOiShipBoilers(List<OiShipBoiler> oiShipBoilers) {
        this.oiShipBoilers = oiShipBoilers;
    }

    public List<OiMainEngine> getOiMainEngines() {
        return oiMainEngines;
    }

    public void setOiMainEngines(List<OiMainEngine> oiMainEngines) {
        this.oiMainEngines = oiMainEngines;
    }

    public List<OiShipGe> getOiShipGes() {
        return oiShipGes;
    }

    public void setOiShipGes(List<OiShipGe> oiShipGes) {
        this.oiShipGes = oiShipGes;
    }

    public List<OiShipGenerator> getOiShipGenerators() {
        return oiShipGenerators;
    }

    public void setOiShipGenerators(List<OiShipGenerator> oiShipGenerators) {
        this.oiShipGenerators = oiShipGenerators;
    }
    @Transient
    private String homePortName;
    @Transient
    private String shipTypeName;

    public String getShipTypeName() {
        return shipTypeName;
    }

    public void setShipTypeName(String shipTypeName) {
        this.shipTypeName = shipTypeName;
    }

    public String getHomePortName() {
        return homePortName;
    }

    public void setHomePortName(String homePortName) {
        this.homePortName = homePortName;
    }

    @Transient
    private List<String> clientId = new ArrayList<>();
    @Transient
    private List<String> fuelIds = new ArrayList<>();

    public List<ShipEquipmentFuelMap> getShipEquipmentFuelMaps() {
        return shipEquipmentFuelMaps;
    }

    public void setShipEquipmentFuelMaps(List<ShipEquipmentFuelMap> shipEquipmentFuelMaps) {
        this.shipEquipmentFuelMaps = shipEquipmentFuelMaps;
    }

    public List<String> getFuelIds() {
        return fuelIds;
    }

    public void setFuelIds(List<String> fuelIds) {
        this.fuelIds = fuelIds;
    }

    public List<String> getClientId() {
        return clientId;
    }

    public void setClientId(List<String> clientId) {
        this.clientId = clientId;
    }

    public List<OiShipCmsaMap> getOiShipCmsaMaps() {
        return oiShipCmsaMaps;
    }

    public void setOiShipCmsaMaps(List<OiShipCmsaMap> oiShipCmsaMaps) {
        this.oiShipCmsaMaps = oiShipCmsaMaps;
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

    public OiShipInfo() {
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
        this.eedi = eedi;
    }

    public BigDecimal getEedivalue() {
        return eedivalue;
    }

    public void setEedivalue(BigDecimal eedivalue) {
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

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public String getIce() {
        if(ice == null || ice.equals("0") || ice.equals("")){
            return "N/A";
        }
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

    public BigDecimal getNet() {
        return this.net;
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

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }
}