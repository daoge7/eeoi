package cn.ccsit.eeoi.energyeefic.dto;

import java.util.Date;

/**
 * ImportVoyage entity. @author MyEclipse Persistence Tools
 */

public class ImportVoyage implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -7648492436705148761L;
	private String id;
	private String imono;
	private String registerno;
	private String shipname;// 船名
	private String voyageyear;// 航段年度
	private String task;// 航次号
	private String voyageno;// 航段号
	private String vsportcode;// 航次起始港代码
	private String vsportcn;// 航次起始港(中文)
	private String vsporten;// 航次起始港(英文)
	private String vsportTime;// 航次起始时间
	private String veportcode;// 航次结束港代码
	private String veportcn;// 航次结束港(中文)
	private String veporten;// 航次结束港(英文)
	private String veportTime;// 航次结束时间
	private String startportcode;// 航段出发港代码
	private String startportcn;// 航段出发港(中文)
	private String startporten;// 航段出发港(英文)（*标示欧盟港口）
	private String startTime;// 航段出发时间
	private String endportcode;// 航段到达港口代码
	private String endportcn;// 航段到达港(中文)
	private String endporten;// 航段到达港(英文)（*标示欧盟港口）
	private String endTime;// 航段到达时间
	private String stopTime;// 停泊时间(包括航行途中抛锚、漂航或靠港加装燃油、补给等未发生装卸货或登离乘客的停泊)（分钟）
	private String cargo;// 载货量(吨)
	private String ballast;// 载货量(吨)
	private String allNum;// 总箱数（TEU）
	private String heavyNum;// 重箱数（TEU）
	private String peopleNum;// 载人数量（人）
	private String carNum;// 载人数量（人）
	private String distance;// 航行里程(海里)
	private String oiHfo;// 航段重油总消耗(吨)
	private String oiLfo;// 航段轻油总消耗(吨)
	private String oiChai;// 航段柴油/汽油总消耗(吨)
	private String oiBing;// 航段液化石油气-丙烷(LPG)总消耗(吨)
	private String oiDing;// 航段液化石油气-丁烷(LPG)总消耗(吨)
	private String oiTian;// 航段液化天然气总消耗(吨)
	private String oiOther;// 航段甲醇/乙醇总消耗(吨)
	private String stopoiHfo;// 停港重油消耗(吨)
	private String stopoiLfo;// 停港轻油消耗(吨)
	private String stopoiChai;// 停港柴油/汽油消耗(吨)
	private String stopoiBing;// 停港液化石油气-丙烷（LPG）消耗(吨)
	private String stopoiDing;// 停港液化石油气-丁烷(LPG）消耗(吨)
	private String stopoiTian;// 停港液化天然气(LNG)消耗(吨)
	private String stopoiOther;// 停港甲醇/乙醇总消耗(吨)
	private String huaoinum;// 航段滑油总消耗(吨)
	private String lessAnergyWork;// 采取节能措施
	private String explain;// 影响能效的异常情况说明
	private String storeType;// 货物种类
	private String speed;// 洋流海流
	private String windpower;// 风力
	private String windpostion;// 风向
	private String wavepower;// 海浪级别
	private String wavepostion;// 海浪方向
	private String note;// 备注
	/***** start 系统自动填充 ******/
	private String warning;
	private String operator;
	private String fileName;
	private String fileId;
	private String result;
	private String ipaddress;
	private Date updateTime;
	private Long status;
	private Date modifyTime;
	private String voyageid;
	private String shipId;
	private String shiptypeId;
	private String startEurope;
	private String endEurope;
	/***** end ******/
	private String hostOilSea;// 主机燃油消耗(海上)
	private String hostOilBerth;// 主机燃油消耗(停泊)
	private String auxiliaryOilSea;// 辅机燃油消耗(海上)
	private String auxiliaryOilBerth;// 辅机燃油消耗(停泊)
	private String boilerOil;// 锅炉燃油(货油加温)
	private String boilerUnloadGood;// (锅炉燃油)卸货
	private String boilerWater;// (锅炉燃油)压载水置换
	private String boilerOther;// (锅炉燃油)其他
	private String host_avgspeed;// 主机平均转速(RPM)
	private String host_boiler;// 主机海上航行油耗(吨)
	private String avg_speedwater;// 平均航速(kn)
	private String dao_water;// 艏吃水(m)
	private String wei_water;// 艉吃水(m)
	private String avg_water;// 平均吃水(m)
	private String cha_water;// 吃水差(m)
	// MRV
	private String iceHfo;// 冰区重油消耗（吨）
	private String iceLfo;// 冰区轻油消耗（吨）
	private String iceChai;// 冰区柴油/汽油消耗(吨)
	private String iceBing;// 冰区液化石油气（丙烷）消耗(吨)
	private String iceDing;// 冰区液化石油气（丁烷）消耗(吨)
	private String iceTian;// 冰区液化天然气消耗(吨)
	private String iceOther;// 冰区甲醇/乙醇总消耗(吨) 按照0/0格式进行录入
	private String iceDate;// 冰区航行时间
	private String iceDistince;// 冰区航行里程
	private String rescueHfo;// 救援重油消耗（吨）
	private String rescueLfo;// 救援轻油消耗（吨）
	private String rescueChai;// 救援柴油/汽油消耗(吨)
	private String rescueBing;// 救援液化石油气（丙烷）消耗(吨)
	private String rescueDing;// 救援液化石油气（丁烷）消耗(吨)
	private String rescueTian;// 救援液化天然气消耗(吨)
	private String rescueOther;// 救援甲醇/乙醇总消耗(吨) 按照0/0格式进行录入

	private String oiHfo1; // 航行Hfo S>0.5%
	private String oiHfo2; // 航行Hfo 0.1%<S<=0.5%
	private String oiHfo3; // 航行Hfo S<=0.1%

	private String oiLfo1; // 航行Lfo S>0.5%
	private String oiLfo2; // 航行Lfo 0.1%<S<=0.5%
	private String oiLfo3; // 航行Lfo S<=0.1%

	private String stopoiHfo1; // 停泊Hfo S>0.5%
	private String stopoiHfo2; // 停泊Hfo 0.1%<S<=0.5%
	private String stopoiHfo3; // 停泊Hfo S<=0.1%

	private String stopoiLfo1; // 停泊Lfo S>0.5%
	private String stopoiLfo2; // 停泊Lfo 0.1%<S<=0.5%
	private String stopoiLfo3; // 停泊Lfo S<=0.1%

	private String shorePower; // 岸电
	
	// Constructors
	private String msg;// 数据异常提示信息

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/** default constructor */
	public ImportVoyage() {
	}

	/** minimal constructor */
	public ImportVoyage(String id) {
		this.id = id;
	}

	/** full constructor */
	public ImportVoyage(String id, String imono, String registerno, String shipname, String voyageyear, String task, String voyageno, String vsportcode, String vsportcn, String vsporten,
                        String vsportTime, String veportcode, String veportcn, String veporten, String veportTime, String startportcode, String startportcn, String startporten, String startTime,
                        String endportcode, String endportcn, String endporten, String endTime, String stopTime, String cargo, String ballast, String allNum, String heavyNum, String peopleNum, String carNum,
                        String distance, String oiHfo, String oiLfo, String oiChai, String oiBing, String oiDing, String oiTian, String oiOther, String stopoiHfo, String stopoiLfo, String stopoiChai,
                        String stopoiBing, String stopoiDing, String stopoiTian, String stopoiOther, String huaoinum, String lessAnergyWork, String explain, String storeType, String speed, String windpower,
                        String windpostion, String wavepower, String wavepostion, String note, String warning, String operator, String fileName, String fileId, String result, String ipaddress, Date updateTime,
                        Long status, Date modifyTime, String voyageid) {
		this.id = id;
		this.imono = imono;
		this.registerno = registerno;
		this.shipname = shipname;
		this.voyageyear = voyageyear;
		this.task = task;
		this.voyageno = voyageno;
		this.vsportcode = vsportcode;
		this.vsportcn = vsportcn;
		this.vsporten = vsporten;
		this.vsportTime = vsportTime;
		this.veportcode = veportcode;
		this.veportcn = veportcn;
		this.veporten = veporten;
		this.veportTime = veportTime;
		this.startportcode = startportcode;
		this.startportcn = startportcn;
		this.startporten = startporten;
		this.startTime = startTime;
		this.endportcode = endportcode;
		this.endportcn = endportcn;
		this.endporten = endporten;
		this.endTime = endTime;
		this.stopTime = stopTime;
		this.cargo = cargo;
		this.ballast = ballast;
		this.allNum = allNum;
		this.heavyNum = heavyNum;
		this.peopleNum = peopleNum;
		this.carNum = carNum;
		this.distance = distance;
		this.oiHfo = oiHfo;
		this.oiLfo = oiLfo;
		this.oiChai = oiChai;
		this.oiBing = oiBing;
		this.oiDing = oiDing;
		this.oiTian = oiTian;
		this.oiOther = oiOther;
		this.stopoiHfo = stopoiHfo;
		this.stopoiLfo = stopoiLfo;
		this.stopoiChai = stopoiChai;
		this.stopoiBing = stopoiBing;
		this.stopoiDing = stopoiDing;
		this.stopoiTian = stopoiTian;
		this.stopoiOther = stopoiOther;
		this.huaoinum = huaoinum;
		this.lessAnergyWork = lessAnergyWork;
		this.explain = explain;
		this.storeType = storeType;
		this.speed = speed;
		this.windpower = windpower;
		this.windpostion = windpostion;
		this.wavepower = wavepower;
		this.wavepostion = wavepostion;
		this.note = note;
		this.warning = warning;
		this.operator = operator;
		this.fileName = fileName;
		this.fileId = fileId;
		this.result = result;
		this.ipaddress = ipaddress;
		this.updateTime = updateTime;
		this.status = status;
		this.modifyTime = modifyTime;
		this.voyageid = voyageid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImono() {
		return this.imono;
	}

	public void setImono(String imono) {
		this.imono = imono;
	}

	public String getRegisterno() {
		return this.registerno;
	}

	public void setRegisterno(String registerno) {
		this.registerno = registerno;
	}

	public String getShipname() {
		return this.shipname;
	}

	public void setShipname(String shipname) {
		this.shipname = shipname;
	}

	public String getVoyageyear() {
		return this.voyageyear;
	}

	public void setVoyageyear(String voyageyear) {
		this.voyageyear = voyageyear;
	}

	public String getTask() {
		return this.task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getVoyageno() {
		return this.voyageno;
	}

	public void setVoyageno(String voyageno) {
		this.voyageno = voyageno;
	}

	public String getVsportcode() {
		return this.vsportcode;
	}

	public void setVsportcode(String vsportcode) {
		this.vsportcode = vsportcode;
	}

	public String getVsportcn() {
		return this.vsportcn;
	}

	public void setVsportcn(String vsportcn) {
		this.vsportcn = vsportcn;
	}

	public String getVsporten() {
		return this.vsporten;
	}

	public void setVsporten(String vsporten) {
		this.vsporten = vsporten;
	}

	public String getVsportTime() {
		return this.vsportTime;
	}

	public void setVsportTime(String vsportTime) {
		this.vsportTime = vsportTime;
	}

	public String getVeportcode() {
		return this.veportcode;
	}

	public void setVeportcode(String veportcode) {
		this.veportcode = veportcode;
	}

	public String getVeportcn() {
		return this.veportcn;
	}

	public void setVeportcn(String veportcn) {
		this.veportcn = veportcn;
	}

	public String getVeporten() {
		return this.veporten;
	}

	public void setVeporten(String veporten) {
		this.veporten = veporten;
	}

	public String getVeportTime() {
		return this.veportTime;
	}

	public void setVeportTime(String veportTime) {
		this.veportTime = veportTime;
	}

	public String getStartportcode() {
		return this.startportcode;
	}

	public void setStartportcode(String startportcode) {
		this.startportcode = startportcode;
	}

	public String getStartportcn() {
		return this.startportcn;
	}

	public void setStartportcn(String startportcn) {
		this.startportcn = startportcn;
	}

	public String getStartporten() {
		return this.startporten;
	}

	public void setStartporten(String startporten) {
		this.startporten = startporten;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndportcode() {
		return this.endportcode;
	}

	public void setEndportcode(String endportcode) {
		this.endportcode = endportcode;
	}

	public String getEndportcn() {
		return this.endportcn;
	}

	public void setEndportcn(String endportcn) {
		this.endportcn = endportcn;
	}

	public String getEndporten() {
		return this.endporten;
	}

	public void setEndporten(String endporten) {
		this.endporten = endporten;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getBallast() {
		return this.ballast;
	}

	public void setBallast(String ballast) {
		this.ballast = ballast;
	}

	public String getAllNum() {
		return this.allNum;
	}

	public void setAllNum(String allNum) {
		this.allNum = allNum;
	}

	public String getHeavyNum() {
		return this.heavyNum;
	}

	public void setHeavyNum(String heavyNum) {
		this.heavyNum = heavyNum;
	}

	public String getPeopleNum() {
		return this.peopleNum;
	}

	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getCarNum() {
		return this.carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getDistance() {
		return this.distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getOiHfo() {
		return this.oiHfo;
	}

	public void setOiHfo(String oiHfo) {
		this.oiHfo = oiHfo;
	}

	public String getOiLfo() {
		return this.oiLfo;
	}

	public void setOiLfo(String oiLfo) {
		this.oiLfo = oiLfo;
	}

	public String getOiChai() {
		return this.oiChai;
	}

	public void setOiChai(String oiChai) {
		this.oiChai = oiChai;
	}

	public String getOiBing() {
		return this.oiBing;
	}

	public void setOiBing(String oiBing) {
		this.oiBing = oiBing;
	}

	public String getOiDing() {
		return this.oiDing;
	}

	public void setOiDing(String oiDing) {
		this.oiDing = oiDing;
	}

	public String getOiTian() {
		return this.oiTian;
	}

	public void setOiTian(String oiTian) {
		this.oiTian = oiTian;
	}

	public String getOiOther() {
		return this.oiOther;
	}

	public void setOiOther(String oiOther) {
		this.oiOther = oiOther;
	}

	public String getStopoiHfo() {
		return this.stopoiHfo;
	}

	public void setStopoiHfo(String stopoiHfo) {
		this.stopoiHfo = stopoiHfo;
	}

	public String getStopoiLfo() {
		return this.stopoiLfo;
	}

	public void setStopoiLfo(String stopoiLfo) {
		this.stopoiLfo = stopoiLfo;
	}

	public String getStopoiChai() {
		return this.stopoiChai;
	}

	public void setStopoiChai(String stopoiChai) {
		this.stopoiChai = stopoiChai;
	}

	public String getStopoiBing() {
		return this.stopoiBing;
	}

	public void setStopoiBing(String stopoiBing) {
		this.stopoiBing = stopoiBing;
	}

	public String getStopoiDing() {
		return this.stopoiDing;
	}

	public void setStopoiDing(String stopoiDing) {
		this.stopoiDing = stopoiDing;
	}

	public String getStopoiTian() {
		return this.stopoiTian;
	}

	public void setStopoiTian(String stopoiTian) {
		this.stopoiTian = stopoiTian;
	}

	public String getStopoiOther() {
		return this.stopoiOther;
	}

	public void setStopoiOther(String stopoiOther) {
		this.stopoiOther = stopoiOther;
	}

	public String getHuaoinum() {
		return this.huaoinum;
	}

	public void setHuaoinum(String huaoinum) {
		this.huaoinum = huaoinum;
	}

	public String getLessAnergyWork() {
		return this.lessAnergyWork;
	}

	public void setLessAnergyWork(String lessAnergyWork) {
		this.lessAnergyWork = lessAnergyWork;
	}

	public String getExplain() {
		return this.explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getStoreType() {
		return this.storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getSpeed() {
		return this.speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getWindpower() {
		return this.windpower;
	}

	public void setWindpower(String windpower) {
		this.windpower = windpower;
	}

	public String getWindpostion() {
		return this.windpostion;
	}

	public void setWindpostion(String windpostion) {
		this.windpostion = windpostion;
	}

	public String getWavepower() {
		return this.wavepower;
	}

	public void setWavepower(String wavepower) {
		this.wavepower = wavepower;
	}

	public String getWavepostion() {
		return this.wavepostion;
	}

	public void setWavepostion(String wavepostion) {
		this.wavepostion = wavepostion;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getWarning() {
		return this.warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getVoyageid() {
		return this.voyageid;
	}

	public void setVoyageid(String voyageid) {
		this.voyageid = voyageid;
	}

	// 后补加信息

	public String getHostOilSea() {
		return hostOilSea;
	}

	public void setHostOilSea(String hostOilSea) {
		this.hostOilSea = hostOilSea;
	}

	public String getHostOilBerth() {
		return hostOilBerth;
	}

	public void setHostOilBerth(String hostOilBerth) {
		this.hostOilBerth = hostOilBerth;
	}

	public String getAuxiliaryOilSea() {
		return auxiliaryOilSea;
	}

	public void setAuxiliaryOilSea(String auxiliaryOilSea) {
		this.auxiliaryOilSea = auxiliaryOilSea;
	}

	public String getAuxiliaryOilBerth() {
		return auxiliaryOilBerth;
	}

	public void setAuxiliaryOilBerth(String auxiliaryOilBerth) {
		this.auxiliaryOilBerth = auxiliaryOilBerth;
	}

	public String getBoilerOil() {
		return boilerOil;
	}

	public void setBoilerOil(String boilerOil) {
		this.boilerOil = boilerOil;
	}

	public String getBoilerUnloadGood() {
		return boilerUnloadGood;
	}

	public void setBoilerUnloadGood(String boilerUnloadGood) {
		this.boilerUnloadGood = boilerUnloadGood;
	}

	public String getBoilerWater() {
		return boilerWater;
	}

	public void setBoilerWater(String boilerWater) {
		this.boilerWater = boilerWater;
	}

	public String getBoilerOther() {
		return boilerOther;
	}

	public void setBoilerOther(String boilerOther) {
		this.boilerOther = boilerOther;
	}

	public String getShipId() {
		return shipId;
	}

	public void setShipId(String shipId) {
		this.shipId = shipId;
	}

	public String getShiptypeId() {
		return shiptypeId;
	}

	public void setShiptypeId(String shiptypeId) {
		this.shiptypeId = shiptypeId;
	}

	public String getHost_avgspeed() {
		return host_avgspeed;
	}

	public void setHost_avgspeed(String host_avgspeed) {
		this.host_avgspeed = host_avgspeed;
	}

	public String getHost_boiler() {
		return host_boiler;
	}

	public void setHost_boiler(String host_boiler) {
		this.host_boiler = host_boiler;
	}

	public String getAvg_speedwater() {
		return avg_speedwater;
	}

	public void setAvg_speedwater(String avg_speedwater) {
		this.avg_speedwater = avg_speedwater;
	}

	public String getDao_water() {
		return dao_water;
	}

	public void setDao_water(String dao_water) {
		this.dao_water = dao_water;
	}

	public String getWei_water() {
		return wei_water;
	}

	public void setWei_water(String wei_water) {
		this.wei_water = wei_water;
	}

	public String getAvg_water() {
		return avg_water;
	}

	public void setAvg_water(String avg_water) {
		this.avg_water = avg_water;
	}

	public String getCha_water() {
		return cha_water;
	}

	public void setCha_water(String cha_water) {
		this.cha_water = cha_water;
	}

	public String getStartEurope() {
		return startEurope;
	}

	public void setStartEurope(String startEurope) {
		this.startEurope = startEurope;
	}

	public String getEndEurope() {
		return endEurope;
	}

	public void setEndEurope(String endEurope) {
		this.endEurope = endEurope;
	}

	public String getIceHfo() {
		return iceHfo;
	}

	public void setIceHfo(String iceHfo) {
		this.iceHfo = iceHfo;
	}

	public String getIceLfo() {
		return iceLfo;
	}

	public void setIceLfo(String iceLfo) {
		this.iceLfo = iceLfo;
	}

	public String getIceChai() {
		return iceChai;
	}

	public void setIceChai(String iceChai) {
		this.iceChai = iceChai;
	}

	public String getIceBing() {
		return iceBing;
	}

	public void setIceBing(String iceBing) {
		this.iceBing = iceBing;
	}

	public String getIceDing() {
		return iceDing;
	}

	public void setIceDing(String iceDing) {
		this.iceDing = iceDing;
	}

	public String getIceTian() {
		return iceTian;
	}

	public void setIceTian(String iceTian) {
		this.iceTian = iceTian;
	}

	public String getRescueHfo() {
		return rescueHfo;
	}

	public void setRescueHfo(String rescueHfo) {
		this.rescueHfo = rescueHfo;
	}

	public String getRescueLfo() {
		return rescueLfo;
	}

	public void setRescueLfo(String rescueLfo) {
		this.rescueLfo = rescueLfo;
	}

	public String getRescueChai() {
		return rescueChai;
	}

	public void setRescueChai(String rescueChai) {
		this.rescueChai = rescueChai;
	}

	public String getRescueBing() {
		return rescueBing;
	}

	public void setRescueBing(String rescueBing) {
		this.rescueBing = rescueBing;
	}

	public String getRescueDing() {
		return rescueDing;
	}

	public void setRescueDing(String rescueDing) {
		this.rescueDing = rescueDing;
	}

	public String getRescueTian() {
		return rescueTian;
	}

	public void setRescueTian(String rescueTian) {
		this.rescueTian = rescueTian;
	}

	public String getIceOther() {
		return iceOther;
	}

	public void setIceOther(String iceOther) {
		this.iceOther = iceOther;
	}

	public String getRescueOther() {
		return rescueOther;
	}

	public void setRescueOther(String rescueOther) {
		this.rescueOther = rescueOther;
	}

	public String getIceDate() {
		return iceDate;
	}

	public void setIceDate(String iceDate) {
		this.iceDate = iceDate;
	}

	public String getIceDistince() {
		return iceDistince;
	}

	public void setIceDistince(String iceDistince) {
		this.iceDistince = iceDistince;
	}

	public String getOiHfo1() {
		return oiHfo1;
	}

	public void setOiHfo1(String oiHfo1) {
		this.oiHfo1 = oiHfo1;
	}

	public String getOiHfo2() {
		return oiHfo2;
	}

	public void setOiHfo2(String oiHfo2) {
		this.oiHfo2 = oiHfo2;
	}

	public String getOiHfo3() {
		return oiHfo3;
	}

	public void setOiHfo3(String oiHfo3) {
		this.oiHfo3 = oiHfo3;
	}

	public String getOiLfo1() {
		return oiLfo1;
	}

	public void setOiLfo1(String oiLfo1) {
		this.oiLfo1 = oiLfo1;
	}

	public String getOiLfo2() {
		return oiLfo2;
	}

	public void setOiLfo2(String oiLfo2) {
		this.oiLfo2 = oiLfo2;
	}

	public String getOiLfo3() {
		return oiLfo3;
	}

	public void setOiLfo3(String oiLfo3) {
		this.oiLfo3 = oiLfo3;
	}

	public String getStopoiHfo1() {
		return stopoiHfo1;
	}

	public void setStopoiHfo1(String stopoiHfo1) {
		this.stopoiHfo1 = stopoiHfo1;
	}

	public String getStopoiHfo2() {
		return stopoiHfo2;
	}

	public void setStopoiHfo2(String stopoiHfo2) {
		this.stopoiHfo2 = stopoiHfo2;
	}

	public String getStopoiHfo3() {
		return stopoiHfo3;
	}

	public void setStopoiHfo3(String stopoiHfo3) {
		this.stopoiHfo3 = stopoiHfo3;
	}

	public String getStopoiLfo1() {
		return stopoiLfo1;
	}

	public void setStopoiLfo1(String stopoiLfo1) {
		this.stopoiLfo1 = stopoiLfo1;
	}

	public String getStopoiLfo2() {
		return stopoiLfo2;
	}

	public void setStopoiLfo2(String stopoiLfo2) {
		this.stopoiLfo2 = stopoiLfo2;
	}

	public String getStopoiLfo3() {
		return stopoiLfo3;
	}

	public void setStopoiLfo3(String stopoiLfo3) {
		this.stopoiLfo3 = stopoiLfo3;
	}

	public String getShorePower() {
		return shorePower;
	}

	public void setShorePower(String shorePower) {
		this.shorePower = shorePower;
	}

	@Override
	public String toString() {
		return "ImportVoyage [id=" + id + ", imono=" + imono + ", registerno=" + registerno + ", shipname=" + shipname + ", voyageyear=" + voyageyear + ", task=" + task + ", voyageno=" + voyageno
				+ ", vsportcode=" + vsportcode + ", vsportcn=" + vsportcn + ", vsporten=" + vsporten + ", vsportTime=" + vsportTime + ", veportcode=" + veportcode + ", veportcn=" + veportcn
				+ ", veporten=" + veporten + ", veportTime=" + veportTime + ", startportcode=" + startportcode + ", startportcn=" + startportcn + ", startporten=" + startporten + ", startTime="
				+ startTime + ", endportcode=" + endportcode + ", endportcn=" + endportcn + ", endporten=" + endporten + ", endTime=" + endTime + ", stopTime=" + stopTime + ", cargo=" + cargo
				+ ", ballast=" + ballast + ", allNum=" + allNum + ", heavyNum=" + heavyNum + ", peopleNum=" + peopleNum + ", carNum=" + carNum + ", distance=" + distance + ", oiHfo=" + oiHfo
				+ ", oiLfo=" + oiLfo + ", oiChai=" + oiChai + ", oiBing=" + oiBing + ", oiDing=" + oiDing + ", oiTian=" + oiTian + ", oiOther=" + oiOther + ", stopoiHfo=" + stopoiHfo + ", stopoiLfo="
				+ stopoiLfo + ", stopoiChai=" + stopoiChai + ", stopoiBing=" + stopoiBing + ", stopoiDing=" + stopoiDing + ", stopoiTian=" + stopoiTian + ", stopoiOther=" + stopoiOther
				+ ", huaoinum=" + huaoinum + ", lessAnergyWork=" + lessAnergyWork + ", explain=" + explain + ", storeType=" + storeType + ", speed=" + speed + ", windpower=" + windpower
				+ ", windpostion=" + windpostion + ", wavepower=" + wavepower + ", wavepostion=" + wavepostion + ", note=" + note + ", warning=" + warning + ", operator=" + operator + ", fileName="
				+ fileName + ", fileId=" + fileId + ", result=" + result + ", ipaddress=" + ipaddress + ", updateTime=" + updateTime + ", status=" + status + ", modifyTime=" + modifyTime
				+ ", voyageid=" + voyageid + ", shipId=" + shipId + ", shiptypeId=" + shiptypeId + ", startEurope=" + startEurope + ", endEurope=" + endEurope + ", hostOilSea=" + hostOilSea
				+ ", hostOilBerth=" + hostOilBerth + ", auxiliaryOilSea=" + auxiliaryOilSea + ", auxiliaryOilBerth=" + auxiliaryOilBerth + ", boilerOil=" + boilerOil + ", boilerUnloadGood="
				+ boilerUnloadGood + ", boilerWater=" + boilerWater + ", boilerOther=" + boilerOther + ", host_avgspeed=" + host_avgspeed + ", host_boiler=" + host_boiler + ", avg_speedwater="
				+ avg_speedwater + ", dao_water=" + dao_water + ", wei_water=" + wei_water + ", avg_water=" + avg_water + ", cha_water=" + cha_water + ", iceHfo=" + iceHfo + ", iceLfo=" + iceLfo
				+ ", iceChai=" + iceChai + ", iceBing=" + iceBing + ", iceDing=" + iceDing + ", iceTian=" + iceTian + ", iceOther=" + iceOther + ", iceDate=" + iceDate + ", iceDistince="
				+ iceDistince + ", rescueHfo=" + rescueHfo + ", rescueLfo=" + rescueLfo + ", rescueChai=" + rescueChai + ", rescueBing=" + rescueBing + ", rescueDing=" + rescueDing + ", rescueTian="
				+ rescueTian + ", rescueOther=" + rescueOther + ", oiHfo1=" + oiHfo1 + ", oiHfo2=" + oiHfo2 + ", oiHfo3=" + oiHfo3 + ", oiLfo1=" + oiLfo1 + ", oiLfo2=" + oiLfo2 + ", oiLfo3=" + oiLfo3
				+ ", stopoiHfo1=" + stopoiHfo1 + ", stopoiHfo2=" + stopoiHfo2 + ", stopoiHfo3=" + stopoiHfo3 + ", stopoiLfo1=" + stopoiLfo1 + ", stopoiLfo2=" + stopoiLfo2 + ", stopoiLfo3="
				+ stopoiLfo3 + ", shorePower=" + shorePower + ", msg=" + msg + "]";
	}

}