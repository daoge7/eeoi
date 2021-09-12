package cn.ccsit.eeoi.ship.vo;

import cn.ccsit.eeoi.ship.entity.OiShipInfo;

import java.math.BigDecimal;
import java.util.Date;


public class OiShipInfoShowVo {
	private String id;
	private String imono;
	private String registNo;
	private String shipName;
	private String shipNameCn;
	private String shipTypeCode;
	private String shipSubTypeCode;
	private String shipFlag;
	private String shipClass;
	private String owner;
	private String owner_id;
	private String operator;
	private String operator_id;
	private String manager;
	private String manager_id;
	private String builder;
	private String builder_id;
	private Date finishDate;
	private Date contractDate;
	private Date display_time;
	private String gross;
	private BigDecimal net;
	private String eediImp;
	private BigDecimal eedi;
	private BigDecimal passengers;
	private BigDecimal cars;
	private BigDecimal dwt;
	private BigDecimal speed;
	private BigDecimal eeoi;
	private String iceCode;
	private String isNX;
	private String mrvContacts;
	private String mrvPost;
	private String mrvPhone;
	private String mrvEmail;
	private BigDecimal teu;
	private Date lastwuDate;
	private String manageTypeCode;
	private String testTypeCode;
	private String eiv;
	private String remark;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImono() {
		return imono;
	}
	public void setImono(String imono) {
		this.imono = imono;
	}
	public String getRegistNo() {
		return registNo;
	}
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public String getShipNameCn() {
		return shipNameCn;
	}
	public void setShipNameCn(String shipNameCn) {
		this.shipNameCn = shipNameCn;
	}
	public String getShipTypeCode() {
		return shipTypeCode;
	}
	public void setShipTypeCode(String shipTypeCode) {
		this.shipTypeCode = shipTypeCode;
	}
	public String getShipSubTypeCode() {
		return shipSubTypeCode;
	}
	public void setShipSubTypeCode(String shipSubTypeCode) {
		this.shipSubTypeCode = shipSubTypeCode;
	}
	public String getShipFlag() {
		return shipFlag;
	}
	public void setShipFlag(String shipFlag) {
		this.shipFlag = shipFlag;
	}
	public String getShipClass() {
		return shipClass;
	}
	public void setShipClass(String shipClass) {
		this.shipClass = shipClass;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getManager_id() {
		return manager_id;
	}
	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}
	public String getBuilder() {
		return builder;
	}
	public void setBuilder(String builder) {
		this.builder = builder;
	}
	public String getBuilder_id() {
		return builder_id;
	}
	public void setBuilder_id(String builder_id) {
		this.builder_id = builder_id;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public Date getContractDate() {
		return contractDate;
	}
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	public Date getDisplay_time() {
		return display_time;
	}
	public void setDisplay_time(Date display_time) {
		this.display_time = display_time;
	}
	public String getGross() {
		return gross;
	}
	public void setGross(String gross) {
		this.gross = gross;
	}
	public BigDecimal getNet() {
		return net;
	}
	public void setNet(BigDecimal net) {
		this.net = net;
	}
	public String getEediImp() {
		return eediImp;
	}
	public void setEediImp(String eediImp) {
		this.eediImp = eediImp;
	}
	public BigDecimal getEedi() {
		return eedi;
	}
	public void setEedi(BigDecimal eedi) {
		this.eedi = eedi;
	}
	public BigDecimal getPassengers() {
		return passengers;
	}
	public void setPassengers(BigDecimal passengers) {
		this.passengers = passengers;
	}
	public BigDecimal getCars() {
		return cars;
	}
	public void setCars(BigDecimal cars) {
		this.cars = cars;
	}
	public BigDecimal getDwt() {
		return dwt;
	}
	public void setDwt(BigDecimal dwt) {
		this.dwt = dwt;
	}
	public BigDecimal getSpeed() {
		return speed;
	}
	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}
	public BigDecimal getEeoi() {
		return eeoi;
	}
	public void setEeoi(BigDecimal eeoi) {
		this.eeoi = eeoi;
	}
	public String getIceCode() {
		return iceCode;
	}
	public void setIceCode(String iceCode) {
		this.iceCode = iceCode;
	}
	public String getIsNX() {
		return isNX;
	}
	public void setIsNX(String isNX) {
		this.isNX = isNX;
	}
	public String getMrvContacts() {
		return mrvContacts;
	}
	public void setMrvContacts(String mrvContacts) {
		this.mrvContacts = mrvContacts;
	}
	public String getMrvPost() {
		return mrvPost;
	}
	public void setMrvPost(String mrvPost) {
		this.mrvPost = mrvPost;
	}
	public String getMrvPhone() {
		return mrvPhone;
	}
	public void setMrvPhone(String mrvPhone) {
		this.mrvPhone = mrvPhone;
	}
	public String getMrvEmail() {
		return mrvEmail;
	}
	public void setMrvEmail(String mrvEmail) {
		this.mrvEmail = mrvEmail;
	}
	public BigDecimal getTeu() {
		return teu;
	}
	public void setTeu(BigDecimal teu) {
		this.teu = teu;
	}
	public Date getLastwuDate() {
		return lastwuDate;
	}
	public void setLastwuDate(Date lastwuDate) {
		this.lastwuDate = lastwuDate;
	}
	public String getManageTypeCode() {
		return manageTypeCode;
	}
	public void setManageTypeCode(String manageTypeCode) {
		this.manageTypeCode = manageTypeCode;
	}
	public String getTestTypeCode() {
		return testTypeCode;
	}
	public void setTestTypeCode(String testTypeCode) {
		this.testTypeCode = testTypeCode;
	}
	public String getEiv() {
		return eiv;
	}
	public void setEiv(String eiv) {
		this.eiv = eiv;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public OiShipInfoShowVo() {
		
	}
	public OiShipInfoShowVo(OiShipInfo ship){
		this.shipNameCn =ship.getSpnameCn();
		this.imono =ship.getImono();
		this.registNo =ship.getRegisterno();
		this.shipFlag =ship.getFlag();
		this.gross =ship.getIsScrubber();
		this.speed =ship.getSpeed();
		this.shipClass =ship.getSptype();
		this.iceCode =ship.getIce();
	}

}
