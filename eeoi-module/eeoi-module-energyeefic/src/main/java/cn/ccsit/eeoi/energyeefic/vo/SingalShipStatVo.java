package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SingalShipStatVo {
    private BigDecimal oiHfo = new BigDecimal("0");
    private BigDecimal oiLfo = new BigDecimal("0");
    private BigDecimal oiChai = new BigDecimal("0");
    private BigDecimal oiBing = new BigDecimal("0");
    private BigDecimal oiDing = new BigDecimal("0");
    private BigDecimal oiTian = new BigDecimal("0");
    private BigDecimal oiHfoStop = new BigDecimal("0");
    private BigDecimal oiLfoStop = new BigDecimal("0");
    private BigDecimal oiChaiStop = new BigDecimal("0");
    private BigDecimal oiBingStop = new BigDecimal("0");
    private BigDecimal oiDingStop = new BigDecimal("0");
    private BigDecimal oiTianStop = new BigDecimal("0");
    private BigDecimal distance  = new BigDecimal(0);
    /**
     * 空载航程
     */
    private BigDecimal distanceEmpty  = new BigDecimal(0);
    /**
     * 周转量
     */
    private BigDecimal turnOver  = new BigDecimal(0);
    /**
     * 箱周转量
     */
    private BigDecimal turnOverBox  = new BigDecimal(0);
    /**
     * 载重量
     */
    private BigDecimal dwt  = new BigDecimal(0);

    private String year;
    private String shipName;
    private String comPanyName;
    private String shipType;
    private String startYear;
    private String endYear;
    private Integer xh;

    /**
     * eeoi平均值
     */
    private BigDecimal eeoi = new BigDecimal(0);
    /**
     * 载重量利用率
     */
    private BigDecimal loadrate = new BigDecimal(0);
    /**
     * 降速比平均值
     */
    private BigDecimal dropRatio = new BigDecimal(0);
    /**
     * 每海里二氧化碳排放
     */
    private BigDecimal co2PerMile = new BigDecimal(0);
    /**
     * 单位运输量二氧化碳排放
     */
    private BigDecimal co2PerCargo = new BigDecimal(0);
    /**
     * 单位周转量能耗
     */
    private BigDecimal oilPerTurnOver = new BigDecimal(0);
    /**
     * 单位运输Teu二氧化碳排放
     */
    private BigDecimal co2PerCargoTeu = new BigDecimal(0);
    /**
     * 单位周转量能耗
     */
    private BigDecimal oilPerTurnOverTeu = new BigDecimal(0);
    /**
     * 箱位利用率
     */
    private BigDecimal boxRadio = new BigDecimal(0);
    /**
     * 每teu二氧化碳排放
     */
    private BigDecimal co2PerTeu = new BigDecimal(0);



}
