package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ImoRptVo {
    /**
     * 报告id
     */
    private String imoRptId;
    private Date startTime;
    private Date endTime;
    private String recStatus;
    private String imoNu;
    private String shipType;
    private BigDecimal gross;
    private BigDecimal net;
    private BigDecimal dwt;
    private String eedi;
    private String ice;
    private String mainEngines;
    private String auxEngines;
    private BigDecimal distance;
    private BigDecimal distanceHour;
    /**
     * 航程修正量
     */
    private BigDecimal correctionDistance = new BigDecimal(0);
    /**
     * 航行小时数修正量
     */
    private BigDecimal correctionDistanceHour = new BigDecimal(0);
    /**
     * 柴油
     */
    private BigDecimal chaiFo;
    /**
     * 重燃油
     */
    private BigDecimal hfo;
    /**
     * 轻燃油
     */
    private BigDecimal lfo;
    /**
     * 丙烷
     */
    private BigDecimal propane;
    /**
     * 丁烷
     */
    private BigDecimal butane;
    /**
     * 液化天然气
     */
    private BigDecimal lng;
    /**
     * 甲醇
     */
    private BigDecimal methanol;
    /**
     * 乙醇
     */
    private BigDecimal Ethanol;
    /**
     * 其他
     */
    private BigDecimal other;
    /**
     * 柴油修正量
     */
    private BigDecimal correctionChaiFo = new BigDecimal(0);
    /**
     * 重燃油修正量
     */
    private BigDecimal correctionHfo = new BigDecimal(0);
    /**
     * 轻燃油修正量
     */
    private BigDecimal correctionLfo = new BigDecimal(0);
    /**
     * 丙烷修正量
     */
    private BigDecimal correctionPropane = new BigDecimal(0);
    /**
     * 丁烷修正量
     */
    private BigDecimal correctionButane = new BigDecimal(0);
    /**
     * 液化天然气修正量
     */
    private BigDecimal correctionLng = new BigDecimal(0);
    /**
     * 甲醇修正量
     */
    private BigDecimal correctionMethanol = new BigDecimal(0);
    /**
     * 乙醇修正量
     */
    private BigDecimal correctionEthanol = new BigDecimal(0);
    /**
     * 其他修正量
     */
    private BigDecimal correctionOther = new BigDecimal(0);
    /**
     * 燃油测量方法
     */
    private String methodUse;

}
