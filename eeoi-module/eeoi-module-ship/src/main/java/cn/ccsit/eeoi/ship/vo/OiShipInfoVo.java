package cn.ccsit.eeoi.ship.vo;

public class OiShipInfoVo {
	//船公司
	private String operator;
	//船舶名称/IMO No./船检登记号
	private String nameOrimo;
	//服务航速 起
	private String startSpeed;
	//服务航速 止
	private String endSpeed;
	//船型
	private String shipTypeCode;
	//船型细分
	private String shipSubTypeCode;
	//吨位范围
	private String gross;
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getNameOrimo() {
		return nameOrimo;
	}
	public void setNameOrimo(String nameOrimo) {
		this.nameOrimo = nameOrimo;
	}
	public String getStartSpeed() {
		return startSpeed;
	}
	public void setStartSpeed(String startSpeed) {
		this.startSpeed = startSpeed;
	}
	public String getEndSpeed() {
		return endSpeed;
	}
	public void setEndSpeed(String endSpeed) {
		this.endSpeed = endSpeed;
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
	public String getGross() {
		return gross;
	}
	public void setGross(String gross) {
		this.gross = gross;
	}

}
