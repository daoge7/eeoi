package cn.ccsit.eeoi.energyeefic.entity;

import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OI_SHIP_VOYAGE",  catalog = "")
public class OiShipVoyage {
    private String id;
    private String shipId;
    private String shiptypeId;
    private String taskid;
    private String voyageno;
    private Integer voyType;
    private String lineClient;
    private String lineCcs;
    private String startportcn;
    private String startporten;
    private String startportid;
    private Date startTime;
    private String endportcn;
    private String endporten;
    private String endportid;
    private Date endTime;
    private BigDecimal waitTime;
    private BigDecimal stopTime;
    private BigDecimal driftingTime;
    private BigDecimal ballast;
    private BigDecimal cargo;
    private Integer emptyNum;
    private Integer heavyNum;
    private Integer allNum;
    private Integer peopleNum;
    private Integer carNum;
    private BigDecimal oiHfo;
    private BigDecimal oiLfo;
    private BigDecimal oiChai;
    private BigDecimal oiBing;
    private BigDecimal oiDing;
    private BigDecimal oiTian;
    private BigDecimal oiOther;
    private BigDecimal oiethanol;
    private BigDecimal stopoiHfo;
    private BigDecimal stopoiLfo;
    private BigDecimal stopoiChai;
    private BigDecimal stopoiBing;
    private BigDecimal stopoiDing;
    private BigDecimal stopoiTian;
    private BigDecimal stopoiOther;
    private BigDecimal stopethanol;
    private BigDecimal iceHfo;
    private BigDecimal iceLfo;
    private BigDecimal iceChai;
    private BigDecimal iceBing;
    private BigDecimal iceDing;
    private BigDecimal iceTian;
    private BigDecimal iceMethanol;
    private BigDecimal iceEthanol;
    private BigDecimal rescueHfo;
    private BigDecimal rescueLfo;
    private BigDecimal rescueChai;
    private BigDecimal rescueBing;
    private BigDecimal rescueDing;
    private BigDecimal rescueTian;
    private BigDecimal rescueMethanol;
    private BigDecimal rescueEthanol;
    private String windpower;
    private String windpostion;
    private String speed;
    private String wavepower;
    private String wavepostion;
    private String shipposition;
    private String explain;
    private BigDecimal co2Cost;
    private BigDecimal tancost;
    private BigDecimal distance;
    private BigDecimal totalCargo;
    private BigDecimal avgspeed;
    private String lessAnergyWork;
    private BigDecimal huaoinum;
    private String storeType;
    private BigDecimal waterLine;
    private BigDecimal timeOnSea;
    private BigDecimal hostOilSea;
    private BigDecimal hostOilBerth;
    private BigDecimal auxiliaryOilSea;
    private BigDecimal auxiliaryOilBerth;
    private BigDecimal boilerOil;
    private BigDecimal boilerUnloadGood;
    private BigDecimal boiletWater;
    private BigDecimal boilerOther;
    private BigDecimal hostavgspeed;
    private BigDecimal hostboiler;
    private BigDecimal avgspeedwater;
    private BigDecimal daowater;
    private BigDecimal weiwater;
    private BigDecimal avgwater;
    private BigDecimal chawater;
    private BigDecimal icedate;
    private BigDecimal icedistince;
    private BigDecimal icemethanol;
    private BigDecimal iceethanol;
    private BigDecimal rescuemethanol;
    private BigDecimal rescueethanol;
    private String startEurope;
    private String endEurope;
    private String isAmended;
    private BigDecimal oiHfo1;
    private BigDecimal oiHfo2;
    private BigDecimal oiHfo3;
    private BigDecimal oiLfo1;
    private BigDecimal oiLfo2;
    private BigDecimal oiLfo3;
    private BigDecimal stopoiHfo1;
    private BigDecimal stopoiHfo2;
    private BigDecimal stopoiHfo3;
    private BigDecimal stopoiLfo1;
    private BigDecimal stopoiLfo2;
    private BigDecimal stopoiLfo3;
    private BigDecimal shorePower;
    private String creator;
    private Date createTm;
    private String opuser;
    private Date opdate;
    private Integer isDelete;
    private BigDecimal sailTime;//航行时间

    @Transient
    public BigDecimal getSailTime() {
        return sailTime;
    }

    public void setSailTime(BigDecimal sailTime) {
        this.sailTime = sailTime;
    }

    //    private RawVoyagePort startPort;
    private BigDecimal emptyDistance;
    @Basic
    @Column(name = "EMPTY_DISTANCE")
    public BigDecimal getEmptyDistance() {
        return emptyDistance;
    }

    public void setEmptyDistance(BigDecimal emptyDistance) {
        this.emptyDistance = emptyDistance;
    }

    public OiShipVoyage() {
    }

    public OiShipVoyage(BigDecimal oiHfo, BigDecimal oiLfo, BigDecimal oiChai, BigDecimal oiBing, BigDecimal oiDing, BigDecimal oiTian, BigDecimal oiOther, BigDecimal oiethanol) {
        this.oiHfo = oiHfo;
        this.oiLfo = oiLfo;
        this.oiChai = oiChai;
        this.oiBing = oiBing;
        this.oiDing = oiDing;
        this.oiTian = oiTian;
        this.oiOther = oiOther;
        this.oiethanol = oiethanol;
    }

    public OiShipVoyage(BigDecimal oiHfo, BigDecimal oiLfo, BigDecimal oiChai, BigDecimal oiBing, BigDecimal oiDing, BigDecimal oiTian, BigDecimal oiOther, BigDecimal oiethanol, BigDecimal stopoiHfo, BigDecimal stopoiLfo, BigDecimal stopoiChai, BigDecimal stopoiBing, BigDecimal stopoiDing, BigDecimal stopoiTian, BigDecimal stopoiOther, BigDecimal stopethanol) {
        this.oiHfo = oiHfo;
        this.oiLfo = oiLfo;
        this.oiChai = oiChai;
        this.oiBing = oiBing;
        this.oiDing = oiDing;
        this.oiTian = oiTian;
        this.oiOther = oiOther;
        this.oiethanol = oiethanol;
        this.stopoiHfo = stopoiHfo;
        this.stopoiLfo = stopoiLfo;
        this.stopoiChai = stopoiChai;
        this.stopoiBing = stopoiBing;
        this.stopoiDing = stopoiDing;
        this.stopoiTian = stopoiTian;
        this.stopoiOther = stopoiOther;
        this.stopethanol = stopethanol;
    }

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "STARTPORTID",insertable = false,updatable = false)
//    @NotFound(action= NotFoundAction.IGNORE)
//    public RawVoyagePort getStartPort() {
//        return startPort;
//    }
//
//    public void setStartPort(RawVoyagePort startPort) {
//        this.startPort = startPort;
//    }
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "ENDPORTID",insertable = false,updatable = false)
//    @NotFound(action=NotFoundAction.IGNORE)
//    public RawVoyagePort getEndPort() {
//        return endPort;
//    }
//
//    public void setEndPort(RawVoyagePort endPort) {
//        this.endPort = endPort;
//    }


//    private RawVoyagePort endPort;

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
    private OiShipInfo oiShipInfo;

    @Basic
    @Column(name = "SHIPTYPE_ID")
    public String getShiptypeId() {
        return shiptypeId;
    }

    public void setShiptypeId(String shiptypeId) {
        this.shiptypeId = shiptypeId;
    }

    @Basic
    @Column(name = "TASKID")
    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    @Basic
    @Column(name = "VOYAGENO")
    public String getVoyageno() {
        return voyageno;
    }

    public void setVoyageno(String voyageno) {
        this.voyageno = voyageno;
    }
    @Basic
    @Column(name = "VOY_TYPE")
    public Integer getVoyType() {
        return voyType;
    }

    public void setVoyType(Integer voyType) {
        this.voyType = voyType;
    }

    @Basic
    @Column(name = "LINE_CLIENT")
    public String getLineClient() {
        return lineClient;
    }

    public void setLineClient(String lineClient) {
        this.lineClient = lineClient;
    }

    @Basic
    @Column(name = "LINE_CCS")
    public String getLineCcs() {
        return lineCcs;
    }

    public void setLineCcs(String lineCcs) {
        this.lineCcs = lineCcs;
    }

    @Basic
    @Column(name = "STARTPORTCN")
    public String getStartportcn() {
        return startportcn;
    }

    public void setStartportcn(String startportcn) {
        this.startportcn = startportcn;
    }

    @Basic
    @Column(name = "STARTPORTEN")
    public String getStartporten() {
        return startporten;
    }

    public void setStartporten(String startporten) {
        this.startporten = startporten;
    }

    @Basic
    @Column(name = "STARTPORTID")
    public String getStartportid() {
        return startportid;
    }

    public void setStartportid(String startportid) {
        this.startportid = startportid;
    }

    @Basic
    @Column(name = "START_TIME")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "ENDPORTCN")
    public String getEndportcn() {
        return endportcn;
    }

    public void setEndportcn(String endportcn) {
        this.endportcn = endportcn;
    }

    @Basic
    @Column(name = "ENDPORTEN")
    public String getEndporten() {
        return endporten;
    }

    public void setEndporten(String endporten) {
        this.endporten = endporten;
    }

    @Basic
    @Column(name = "ENDPORTID")
    public String getEndportid() {
        return endportid;
    }

    public void setEndportid(String endportid) {
        this.endportid = endportid;
    }

    @Basic
    @Column(name = "END_TIME")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "WAIT_TIME")
    public BigDecimal getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(BigDecimal waitTime) {
        this.waitTime = waitTime;
    }

    @Basic
    @Column(name = "STOP_TIME")
    public BigDecimal getStopTime() {
        return stopTime;
    }

    public void setStopTime(BigDecimal stopTime) {
        this.stopTime = stopTime;
    }
    @Basic
    @Column(name = "DRIFT_TIME")
    public BigDecimal getDriftingTime() {
        return driftingTime;
    }

    public void setDriftingTime(BigDecimal driftingTime) {
        this.driftingTime = driftingTime;
    }

    @Basic
    @Column(name = "BALLAST")
    public BigDecimal getBallast() {
        return ballast;
    }

    public void setBallast(BigDecimal ballast) {
        this.ballast = ballast;
    }

    @Basic
    @Column(name = "CARGO")
    public BigDecimal getCargo() {
        return cargo;
    }

    public void setCargo(BigDecimal cargo) {
        this.cargo = cargo;
    }

    @Basic
    @Column(name = "EMPTY_NUM")
    public Integer getEmptyNum() {
        return emptyNum;
    }

    public void setEmptyNum(Integer emptyNum) {
        this.emptyNum = emptyNum;
    }

    @Basic
    @Column(name = "HEAVY_NUM")
    public Integer getHeavyNum() {
        return heavyNum;
    }

    public void setHeavyNum(Integer heavyNum) {
        this.heavyNum = heavyNum;
    }

    @Basic
    @Column(name = "ALL_NUM")
    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    @Basic
    @Column(name = "PEOPLE_NUM")
    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    @Basic
    @Column(name = "CAR_NUM")
    public Integer getCarNum() {
        return carNum;
    }

    public void setCarNum(Integer carNum) {
        this.carNum = carNum;
    }

    @Basic
    @Column(name = "OI_HFO")
    public BigDecimal getOiHfo() {
        return oiHfo;
    }

    public void setOiHfo(BigDecimal oiHfo) {
        this.oiHfo = oiHfo;
    }

    @Basic
    @Column(name = "OI_LFO")
    public BigDecimal getOiLfo() {
        return oiLfo;
    }

    public void setOiLfo(BigDecimal oiLfo) {
        this.oiLfo = oiLfo;
    }

    @Basic
    @Column(name = "OI_CHAI")
    public BigDecimal getOiChai() {
        return oiChai;
    }

    public void setOiChai(BigDecimal oiChai) {
        this.oiChai = oiChai;
    }

    @Basic
    @Column(name = "OI_BING")
    public BigDecimal getOiBing() {
        return oiBing;
    }

    public void setOiBing(BigDecimal oiBing) {
        this.oiBing = oiBing;
    }

    @Basic
    @Column(name = "OI_DING")
    public BigDecimal getOiDing() {
        return oiDing;
    }

    public void setOiDing(BigDecimal oiDing) {
        this.oiDing = oiDing;
    }

    @Basic
    @Column(name = "OI_TIAN")
    public BigDecimal getOiTian() {
        return oiTian;
    }

    public void setOiTian(BigDecimal oiTian) {
        this.oiTian = oiTian;
    }

    @Basic
    @Column(name = "OI_OTHER")
    public BigDecimal getOiOther() {
        return oiOther;
    }

    public void setOiOther(BigDecimal oiOther) {
        this.oiOther = oiOther;
    }

    @Basic
    @Column(name = "OIETHANOL")
    public BigDecimal getOiethanol() {
        return oiethanol;
    }

    public void setOiethanol(BigDecimal oiethanol) {
        this.oiethanol = oiethanol;
    }

    @Basic
    @Column(name = "STOPOI_HFO")
    public BigDecimal getStopoiHfo() {
        return stopoiHfo;
    }

    public void setStopoiHfo(BigDecimal stopoiHfo) {
        this.stopoiHfo = stopoiHfo;
    }

    @Basic
    @Column(name = "STOPOI_LFO")
    public BigDecimal getStopoiLfo() {
        return stopoiLfo;
    }

    public void setStopoiLfo(BigDecimal stopoiLfo) {
        this.stopoiLfo = stopoiLfo;
    }

    @Basic
    @Column(name = "STOPOI_CHAI")
    public BigDecimal getStopoiChai() {
        return stopoiChai;
    }

    public void setStopoiChai(BigDecimal stopoiChai) {
        this.stopoiChai = stopoiChai;
    }

    @Basic
    @Column(name = "STOPOI_BING")
    public BigDecimal getStopoiBing() {
        return stopoiBing;
    }

    public void setStopoiBing(BigDecimal stopoiBing) {
        this.stopoiBing = stopoiBing;
    }

    @Basic
    @Column(name = "STOPOI_DING")
    public BigDecimal getStopoiDing() {
        return stopoiDing;
    }

    public void setStopoiDing(BigDecimal stopoiDing) {
        this.stopoiDing = stopoiDing;
    }

    @Basic
    @Column(name = "STOPOI_TIAN")
    public BigDecimal getStopoiTian() {
        return stopoiTian;
    }

    public void setStopoiTian(BigDecimal stopoiTian) {
        this.stopoiTian = stopoiTian;
    }

    @Basic
    @Column(name = "STOPOI_OTHER")
    public BigDecimal getStopoiOther() {
        return stopoiOther;
    }

    public void setStopoiOther(BigDecimal stopoiOther) {
        this.stopoiOther = stopoiOther;
    }

    @Basic
    @Column(name = "STOPETHANOL")
    public BigDecimal getStopethanol() {
        return stopethanol;
    }

    public void setStopethanol(BigDecimal stopethanol) {
        this.stopethanol = stopethanol;
    }

    @Basic
    @Column(name = "ICE_HFO")
    public BigDecimal getIceHfo() {
        return iceHfo;
    }

    public void setIceHfo(BigDecimal iceHfo) {
        this.iceHfo = iceHfo;
    }

    @Basic
    @Column(name = "ICE_LFO")
    public BigDecimal getIceLfo() {
        return iceLfo;
    }

    public void setIceLfo(BigDecimal iceLfo) {
        this.iceLfo = iceLfo;
    }

    @Basic
    @Column(name = "ICE_CHAI")
    public BigDecimal getIceChai() {
        return iceChai;
    }

    public void setIceChai(BigDecimal iceChai) {
        this.iceChai = iceChai;
    }

    @Basic
    @Column(name = "ICE_BING")
    public BigDecimal getIceBing() {
        return iceBing;
    }

    public void setIceBing(BigDecimal iceBing) {
        this.iceBing = iceBing;
    }

    @Basic
    @Column(name = "ICE_DING")
    public BigDecimal getIceDing() {
        return iceDing;
    }

    public void setIceDing(BigDecimal iceDing) {
        this.iceDing = iceDing;
    }

    @Basic
    @Column(name = "ICE_TIAN")
    public BigDecimal getIceTian() {
        return iceTian;
    }

    public void setIceTian(BigDecimal iceTian) {
        this.iceTian = iceTian;
    }

    @Basic
    @Column(name = "ICE_METHANOL")
    public BigDecimal getIceMethanol() {
        return iceMethanol;
    }

    public void setIceMethanol(BigDecimal iceMethanol) {
        this.iceMethanol = iceMethanol;
    }

    @Basic
    @Column(name = "ICE_ETHANOL")
    public BigDecimal getIceEthanol() {
        return iceEthanol;
    }

    public void setIceEthanol(BigDecimal iceEthanol) {
        this.iceEthanol = iceEthanol;
    }

    @Basic
    @Column(name = "RESCUE_HFO")
    public BigDecimal getRescueHfo() {
        return rescueHfo;
    }

    public void setRescueHfo(BigDecimal rescueHfo) {
        this.rescueHfo = rescueHfo;
    }

    @Basic
    @Column(name = "RESCUE_LFO")
    public BigDecimal getRescueLfo() {
        return rescueLfo;
    }

    public void setRescueLfo(BigDecimal rescueLfo) {
        this.rescueLfo = rescueLfo;
    }

    @Basic
    @Column(name = "RESCUE_CHAI")
    public BigDecimal getRescueChai() {
        return rescueChai;
    }

    public void setRescueChai(BigDecimal rescueChai) {
        this.rescueChai = rescueChai;
    }

    @Basic
    @Column(name = "RESCUE_BING")
    public BigDecimal getRescueBing() {
        return rescueBing;
    }

    public void setRescueBing(BigDecimal rescueBing) {
        this.rescueBing = rescueBing;
    }

    @Basic
    @Column(name = "RESCUE_DING")
    public BigDecimal getRescueDing() {
        return rescueDing;
    }

    public void setRescueDing(BigDecimal rescueDing) {
        this.rescueDing = rescueDing;
    }

    @Basic
    @Column(name = "RESCUE_TIAN")
    public BigDecimal getRescueTian() {
        return rescueTian;
    }

    public void setRescueTian(BigDecimal rescueTian) {
        this.rescueTian = rescueTian;
    }

    @Basic
    @Column(name = "RESCUE_METHANOL")
    public BigDecimal getRescueMethanol() {
        return rescueMethanol;
    }

    public void setRescueMethanol(BigDecimal rescueMethanol) {
        this.rescueMethanol = rescueMethanol;
    }

    @Basic
    @Column(name = "RESCUE_ETHANOL")
    public BigDecimal getRescueEthanol() {
        return rescueEthanol;
    }

    public void setRescueEthanol(BigDecimal rescueEthanol) {
        this.rescueEthanol = rescueEthanol;
    }

    @Basic
    @Column(name = "WINDPOWER")
    public String getWindpower() {
        return windpower;
    }

    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }

    @Basic
    @Column(name = "WINDPOSTION")
    public String getWindpostion() {
        return windpostion;
    }

    public void setWindpostion(String windpostion) {
        this.windpostion = windpostion;
    }

    @Basic
    @Column(name = "SPEED")
    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Basic
    @Column(name = "WAVEPOWER")
    public String getWavepower() {
        return wavepower;
    }

    public void setWavepower(String wavepower) {
        this.wavepower = wavepower;
    }

    @Basic
    @Column(name = "WAVEPOSTION")
    public String getWavepostion() {
        return wavepostion;
    }

    public void setWavepostion(String wavepostion) {
        this.wavepostion = wavepostion;
    }

    @Basic
    @Column(name = "SHIPPOSITION")
    public String getShipposition() {
        return shipposition;
    }

    public void setShipposition(String shipposition) {
        this.shipposition = shipposition;
    }

    @Basic
    @Column(name = "EXPLAIN")
    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Basic
    @Column(name = "CO2COST")
    public BigDecimal getCo2Cost() {
        return co2Cost;
    }

    public void setCo2Cost(BigDecimal co2Cost) {
        this.co2Cost = co2Cost;
    }

    @Basic
    @Column(name = "TANCOST")
    public BigDecimal getTancost() {
        return tancost;
    }

    public void setTancost(BigDecimal tancost) {
        this.tancost = tancost;
    }

    @Basic
    @Column(name = "DISTANCE")
    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    @Basic
    @Column(name = "TOTAL_CARGO")
    public BigDecimal getTotalCargo() {
        return totalCargo;
    }

    public void setTotalCargo(BigDecimal totalCargo) {
        this.totalCargo = totalCargo;
    }

    @Basic
    @Column(name = "AVGSPEED")
    public BigDecimal getAvgspeed() {
        return avgspeed;
    }

    public void setAvgspeed(BigDecimal avgspeed) {
        this.avgspeed = avgspeed;
    }

    @Basic
    @Column(name = "LESS_ANERGY_WORK")
    public String getLessAnergyWork() {
        return lessAnergyWork;
    }

    public void setLessAnergyWork(String lessAnergyWork) {
        this.lessAnergyWork = lessAnergyWork;
    }

    @Basic
    @Column(name = "HUAOINUM")
    public BigDecimal getHuaoinum() {
        return huaoinum;
    }

    public void setHuaoinum(BigDecimal huaoinum) {
        this.huaoinum = huaoinum;
    }

    @Basic
    @Column(name = "STORE_TYPE")
    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    @Basic
    @Column(name = "WATER_LINE")
    public BigDecimal getWaterLine() {
        return waterLine;
    }

    public void setWaterLine(BigDecimal waterLine) {
        this.waterLine = waterLine;
    }

    @Basic
    @Column(name = "TIME_ON_SEA")
    public BigDecimal getTimeOnSea() {
        return timeOnSea;
    }

    public void setTimeOnSea(BigDecimal timeOnSea) {
        this.timeOnSea = timeOnSea;
    }

    @Basic
    @Column(name = "HOST_OIL_SEA")
    public BigDecimal getHostOilSea() {
        return hostOilSea;
    }

    public void setHostOilSea(BigDecimal hostOilSea) {
        this.hostOilSea = hostOilSea;
    }

    @Basic
    @Column(name = "HOST_OIL_BERTH")
    public BigDecimal getHostOilBerth() {
        return hostOilBerth;
    }

    public void setHostOilBerth(BigDecimal hostOilBerth) {
        this.hostOilBerth = hostOilBerth;
    }

    @Basic
    @Column(name = "AUXILIARY_OIL_SEA")
    public BigDecimal getAuxiliaryOilSea() {
        return auxiliaryOilSea;
    }

    public void setAuxiliaryOilSea(BigDecimal auxiliaryOilSea) {
        this.auxiliaryOilSea = auxiliaryOilSea;
    }

    @Basic
    @Column(name = "AUXILIARY_OIL_BERTH")
    public BigDecimal getAuxiliaryOilBerth() {
        return auxiliaryOilBerth;
    }

    public void setAuxiliaryOilBerth(BigDecimal auxiliaryOilBerth) {
        this.auxiliaryOilBerth = auxiliaryOilBerth;
    }

    @Basic
    @Column(name = "BOILER_OIL")
    public BigDecimal getBoilerOil() {
        return boilerOil;
    }

    public void setBoilerOil(BigDecimal boilerOil) {
        this.boilerOil = boilerOil;
    }

    @Basic
    @Column(name = "BOILER_UNLOAD_GOOD")
    public BigDecimal getBoilerUnloadGood() {
        return boilerUnloadGood;
    }

    public void setBoilerUnloadGood(BigDecimal boilerUnloadGood) {
        this.boilerUnloadGood = boilerUnloadGood;
    }

    @Basic
    @Column(name = "BOILET_WATER")
    public BigDecimal getBoiletWater() {
        return boiletWater;
    }

    public void setBoiletWater(BigDecimal boiletWater) {
        this.boiletWater = boiletWater;
    }

    @Basic
    @Column(name = "BOILER_OTHER")
    public BigDecimal getBoilerOther() {
        return boilerOther;
    }

    public void setBoilerOther(BigDecimal boilerOther) {
        this.boilerOther = boilerOther;
    }

    @Basic
    @Column(name = "HOSTAVGSPEED")
    public BigDecimal getHostavgspeed() {
        return hostavgspeed;
    }

    public void setHostavgspeed(BigDecimal hostavgspeed) {
        this.hostavgspeed = hostavgspeed;
    }

    @Basic
    @Column(name = "HOSTBOILER")
    public BigDecimal getHostboiler() {
        return hostboiler;
    }

    public void setHostboiler(BigDecimal hostboiler) {
        this.hostboiler = hostboiler;
    }

    @Basic
    @Column(name = "AVGSPEEDWATER")
    public BigDecimal getAvgspeedwater() {
        return avgspeedwater;
    }

    public void setAvgspeedwater(BigDecimal avgspeedwater) {
        this.avgspeedwater = avgspeedwater;
    }

    @Basic
    @Column(name = "DAOWATER")
    public BigDecimal getDaowater() {
        return daowater;
    }

    public void setDaowater(BigDecimal daowater) {
        this.daowater = daowater;
    }

    @Basic
    @Column(name = "WEIWATER")
    public BigDecimal getWeiwater() {
        return weiwater;
    }

    public void setWeiwater(BigDecimal weiwater) {
        this.weiwater = weiwater;
    }

    @Basic
    @Column(name = "AVGWATER")
    public BigDecimal getAvgwater() {
        return avgwater;
    }

    public void setAvgwater(BigDecimal avgwater) {
        this.avgwater = avgwater;
    }

    @Basic
    @Column(name = "CHAWATER")
    public BigDecimal getChawater() {
        return chawater;
    }

    public void setChawater(BigDecimal chawater) {
        this.chawater = chawater;
    }

    @Basic
    @Column(name = "ICEDATE")
    public BigDecimal getIcedate() {
        return icedate;
    }

    public void setIcedate(BigDecimal icedate) {
        this.icedate = icedate;
    }

    @Basic
    @Column(name = "ICEDISTINCE")
    public BigDecimal getIcedistince() {
        return icedistince;
    }

    public void setIcedistince(BigDecimal icedistince) {
        this.icedistince = icedistince;
    }

    @Basic
    @Column(name = "ICEMETHANOL")
    public BigDecimal getIcemethanol() {
        return icemethanol;
    }

    public void setIcemethanol(BigDecimal icemethanol) {
        this.icemethanol = icemethanol;
    }

    @Basic
    @Column(name = "ICEETHANOL")
    public BigDecimal getIceethanol() {
        return iceethanol;
    }

    public void setIceethanol(BigDecimal iceethanol) {
        this.iceethanol = iceethanol;
    }

    @Basic
    @Column(name = "RESCUEMETHANOL")
    public BigDecimal getRescuemethanol() {
        return rescuemethanol;
    }

    public void setRescuemethanol(BigDecimal rescuemethanol) {
        this.rescuemethanol = rescuemethanol;
    }

    @Basic
    @Column(name = "RESCUEETHANOL")
    public BigDecimal getRescueethanol() {
        return rescueethanol;
    }

    public void setRescueethanol(BigDecimal rescueethanol) {
        this.rescueethanol = rescueethanol;
    }

    @Basic
    @Column(name = "START_EUROPE")
    public String getStartEurope() {
        return startEurope;
    }

    public void setStartEurope(String startEurope) {
        this.startEurope = startEurope;
    }

    @Basic
    @Column(name = "END_EUROPE")
    public String getEndEurope() {
        return endEurope;
    }

    public void setEndEurope(String endEurope) {
        this.endEurope = endEurope;
    }

    @Basic
    @Column(name = "IS_AMENDED")
    public String getIsAmended() {
        return isAmended;
    }

    public void setIsAmended(String isAmended) {
        this.isAmended = isAmended;
    }

    @Basic
    @Column(name = "OI_HFO1")
    public BigDecimal getOiHfo1() {
        return oiHfo1;
    }

    public void setOiHfo1(BigDecimal oiHfo1) {
        this.oiHfo1 = oiHfo1;
    }

    @Basic
    @Column(name = "OI_HFO2")
    public BigDecimal getOiHfo2() {
        return oiHfo2;
    }

    public void setOiHfo2(BigDecimal oiHfo2) {
        this.oiHfo2 = oiHfo2;
    }

    @Basic
    @Column(name = "OI_HFO3")
    public BigDecimal getOiHfo3() {
        return oiHfo3;
    }

    public void setOiHfo3(BigDecimal oiHfo3) {
        this.oiHfo3 = oiHfo3;
    }

    @Basic
    @Column(name = "OI_LFO1")
    public BigDecimal getOiLfo1() {
        return oiLfo1;
    }

    public void setOiLfo1(BigDecimal oiLfo1) {
        this.oiLfo1 = oiLfo1;
    }

    @Basic
    @Column(name = "OI_LFO2")
    public BigDecimal getOiLfo2() {
        return oiLfo2;
    }

    public void setOiLfo2(BigDecimal oiLfo2) {
        this.oiLfo2 = oiLfo2;
    }

    @Basic
    @Column(name = "OI_LFO3")
    public BigDecimal getOiLfo3() {
        return oiLfo3;
    }

    public void setOiLfo3(BigDecimal oiLfo3) {
        this.oiLfo3 = oiLfo3;
    }

    @Basic
    @Column(name = "STOPOI_HFO1")
    public BigDecimal getStopoiHfo1() {
        return stopoiHfo1;
    }

    public void setStopoiHfo1(BigDecimal stopoiHfo1) {
        this.stopoiHfo1 = stopoiHfo1;
    }

    @Basic
    @Column(name = "STOPOI_HFO2")
    public BigDecimal getStopoiHfo2() {
        return stopoiHfo2;
    }

    public void setStopoiHfo2(BigDecimal stopoiHfo2) {
        this.stopoiHfo2 = stopoiHfo2;
    }

    @Basic
    @Column(name = "STOPOI_HFO3")
    public BigDecimal getStopoiHfo3() {
        return stopoiHfo3;
    }

    public void setStopoiHfo3(BigDecimal stopoiHfo3) {
        this.stopoiHfo3 = stopoiHfo3;
    }

    @Basic
    @Column(name = "STOPOI_LFO1")
    public BigDecimal getStopoiLfo1() {
        return stopoiLfo1;
    }

    public void setStopoiLfo1(BigDecimal stopoiLfo1) {
        this.stopoiLfo1 = stopoiLfo1;
    }

    @Basic
    @Column(name = "STOPOI_LFO2")
    public BigDecimal getStopoiLfo2() {
        return stopoiLfo2;
    }

    public void setStopoiLfo2(BigDecimal stopoiLfo2) {
        this.stopoiLfo2 = stopoiLfo2;
    }

    @Basic
    @Column(name = "STOPOI_LFO3")
    public BigDecimal getStopoiLfo3() {
        return stopoiLfo3;
    }

    public void setStopoiLfo3(BigDecimal stopoiLfo3) {
        this.stopoiLfo3 = stopoiLfo3;
    }

    @Basic
    @Column(name = "SHORE_POWER")
    public BigDecimal getShorePower() {
        return shorePower;
    }

    public void setShorePower(BigDecimal shorePower) {
        this.shorePower = shorePower;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SHIP_ID",insertable = false,updatable = false)
    @NotFound(action=NotFoundAction.IGNORE)
    public OiShipInfo getOiShipInfo() {
        return oiShipInfo;
    }

    public void setOiShipInfo(OiShipInfo oiShipInfo) {
        this.oiShipInfo = oiShipInfo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OiShipVoyage that = (OiShipVoyage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shipId, that.shipId) &&
                Objects.equals(shiptypeId, that.shiptypeId) &&
                Objects.equals(taskid, that.taskid) &&
                Objects.equals(voyageno, that.voyageno) &&
                Objects.equals(lineClient, that.lineClient) &&
                Objects.equals(lineCcs, that.lineCcs) &&
                Objects.equals(startportcn, that.startportcn) &&
                Objects.equals(startporten, that.startporten) &&
                Objects.equals(startportid, that.startportid) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endportcn, that.endportcn) &&
                Objects.equals(endporten, that.endporten) &&
                Objects.equals(endportid, that.endportid) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(waitTime, that.waitTime) &&
                Objects.equals(stopTime, that.stopTime) &&
                Objects.equals(ballast, that.ballast) &&
                Objects.equals(cargo, that.cargo) &&
                Objects.equals(emptyNum, that.emptyNum) &&
                Objects.equals(heavyNum, that.heavyNum) &&
                Objects.equals(allNum, that.allNum) &&
                Objects.equals(peopleNum, that.peopleNum) &&
                Objects.equals(carNum, that.carNum) &&
                Objects.equals(oiHfo, that.oiHfo) &&
                Objects.equals(oiLfo, that.oiLfo) &&
                Objects.equals(oiChai, that.oiChai) &&
                Objects.equals(oiBing, that.oiBing) &&
                Objects.equals(oiDing, that.oiDing) &&
                Objects.equals(oiTian, that.oiTian) &&
                Objects.equals(oiOther, that.oiOther) &&
                Objects.equals(oiethanol, that.oiethanol) &&
                Objects.equals(stopoiHfo, that.stopoiHfo) &&
                Objects.equals(stopoiLfo, that.stopoiLfo) &&
                Objects.equals(stopoiChai, that.stopoiChai) &&
                Objects.equals(stopoiBing, that.stopoiBing) &&
                Objects.equals(stopoiDing, that.stopoiDing) &&
                Objects.equals(stopoiTian, that.stopoiTian) &&
                Objects.equals(stopoiOther, that.stopoiOther) &&
                Objects.equals(stopethanol, that.stopethanol) &&
                Objects.equals(iceHfo, that.iceHfo) &&
                Objects.equals(iceLfo, that.iceLfo) &&
                Objects.equals(iceChai, that.iceChai) &&
                Objects.equals(iceBing, that.iceBing) &&
                Objects.equals(iceDing, that.iceDing) &&
                Objects.equals(iceTian, that.iceTian) &&
                Objects.equals(iceMethanol, that.iceMethanol) &&
                Objects.equals(iceEthanol, that.iceEthanol) &&
                Objects.equals(rescueHfo, that.rescueHfo) &&
                Objects.equals(rescueLfo, that.rescueLfo) &&
                Objects.equals(rescueChai, that.rescueChai) &&
                Objects.equals(rescueBing, that.rescueBing) &&
                Objects.equals(rescueDing, that.rescueDing) &&
                Objects.equals(rescueTian, that.rescueTian) &&
                Objects.equals(rescueMethanol, that.rescueMethanol) &&
                Objects.equals(rescueEthanol, that.rescueEthanol) &&
                Objects.equals(windpower, that.windpower) &&
                Objects.equals(windpostion, that.windpostion) &&
                Objects.equals(speed, that.speed) &&
                Objects.equals(wavepower, that.wavepower) &&
                Objects.equals(wavepostion, that.wavepostion) &&
                Objects.equals(shipposition, that.shipposition) &&
                Objects.equals(explain, that.explain) &&
                Objects.equals(co2Cost, that.co2Cost) &&
                Objects.equals(tancost, that.tancost) &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(totalCargo, that.totalCargo) &&
                Objects.equals(avgspeed, that.avgspeed) &&
                Objects.equals(lessAnergyWork, that.lessAnergyWork) &&
                Objects.equals(huaoinum, that.huaoinum) &&
                Objects.equals(storeType, that.storeType) &&
                Objects.equals(waterLine, that.waterLine) &&
                Objects.equals(timeOnSea, that.timeOnSea) &&
                Objects.equals(hostOilSea, that.hostOilSea) &&
                Objects.equals(hostOilBerth, that.hostOilBerth) &&
                Objects.equals(auxiliaryOilSea, that.auxiliaryOilSea) &&
                Objects.equals(auxiliaryOilBerth, that.auxiliaryOilBerth) &&
                Objects.equals(boilerOil, that.boilerOil) &&
                Objects.equals(boilerUnloadGood, that.boilerUnloadGood) &&
                Objects.equals(boiletWater, that.boiletWater) &&
                Objects.equals(boilerOther, that.boilerOther) &&
                Objects.equals(hostavgspeed, that.hostavgspeed) &&
                Objects.equals(hostboiler, that.hostboiler) &&
                Objects.equals(avgspeedwater, that.avgspeedwater) &&
                Objects.equals(daowater, that.daowater) &&
                Objects.equals(weiwater, that.weiwater) &&
                Objects.equals(avgwater, that.avgwater) &&
                Objects.equals(chawater, that.chawater) &&
                Objects.equals(icedate, that.icedate) &&
                Objects.equals(icedistince, that.icedistince) &&
                Objects.equals(icemethanol, that.icemethanol) &&
                Objects.equals(iceethanol, that.iceethanol) &&
                Objects.equals(rescuemethanol, that.rescuemethanol) &&
                Objects.equals(rescueethanol, that.rescueethanol) &&
                Objects.equals(startEurope, that.startEurope) &&
                Objects.equals(endEurope, that.endEurope) &&
                Objects.equals(isAmended, that.isAmended) &&
                Objects.equals(oiHfo1, that.oiHfo1) &&
                Objects.equals(oiHfo2, that.oiHfo2) &&
                Objects.equals(oiHfo3, that.oiHfo3) &&
                Objects.equals(oiLfo1, that.oiLfo1) &&
                Objects.equals(oiLfo2, that.oiLfo2) &&
                Objects.equals(oiLfo3, that.oiLfo3) &&
                Objects.equals(stopoiHfo1, that.stopoiHfo1) &&
                Objects.equals(stopoiHfo2, that.stopoiHfo2) &&
                Objects.equals(stopoiHfo3, that.stopoiHfo3) &&
                Objects.equals(stopoiLfo1, that.stopoiLfo1) &&
                Objects.equals(stopoiLfo2, that.stopoiLfo2) &&
                Objects.equals(stopoiLfo3, that.stopoiLfo3) &&
                Objects.equals(shorePower, that.shorePower) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shipId, shiptypeId, taskid, voyageno, lineClient, lineCcs, startportcn, startporten, startportid, startTime, endportcn, endporten, endportid, endTime, waitTime, stopTime, ballast, cargo, emptyNum, heavyNum, allNum, peopleNum, carNum, oiHfo, oiLfo, oiChai, oiBing, oiDing, oiTian, oiOther, oiethanol, stopoiHfo, stopoiLfo, stopoiChai, stopoiBing, stopoiDing, stopoiTian, stopoiOther, stopethanol, iceHfo, iceLfo, iceChai, iceBing, iceDing, iceTian, iceMethanol, iceEthanol, rescueHfo, rescueLfo, rescueChai, rescueBing, rescueDing, rescueTian, rescueMethanol, rescueEthanol, windpower, windpostion, speed, wavepower, wavepostion, shipposition, explain, co2Cost, tancost, distance, totalCargo, avgspeed, lessAnergyWork, huaoinum, storeType, waterLine, timeOnSea, hostOilSea, hostOilBerth, auxiliaryOilSea, auxiliaryOilBerth, boilerOil, boilerUnloadGood, boiletWater, boilerOther, hostavgspeed, hostboiler, avgspeedwater, daowater, weiwater, avgwater, chawater, icedate, icedistince, icemethanol, iceethanol, rescuemethanol, rescueethanol, startEurope, endEurope, isAmended, oiHfo1, oiHfo2, oiHfo3, oiLfo1, oiLfo2, oiLfo3, stopoiHfo1, stopoiHfo2, stopoiHfo3, stopoiLfo1, stopoiLfo2, stopoiLfo3, shorePower, creator, createTm, opuser, opdate, isDelete);
    }
}
