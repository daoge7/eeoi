package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
@Data
public class SaveGeneratorDcsDto {
    @NotBlank(message = "报告id不能为空")
    private String imoRptId;
    /**
     * 柴油
     */
    private BigDecimal chaiFo = new BigDecimal(0);
    /**
     * 重燃油
     */
    private BigDecimal hfo = new BigDecimal(0);
    /**
     * 轻燃油
     */
    private BigDecimal lfo = new BigDecimal(0);
    /**
     * 丙烷
     */
    private BigDecimal propane = new BigDecimal(0);
    /**
     * 丁烷
     */
    private BigDecimal butane = new BigDecimal(0);
    /**
     * 液化天然气
     */
    private BigDecimal lng = new BigDecimal(0);
    /**
     * 甲醇
     */
    private BigDecimal methanol = new BigDecimal(0);
    /**
     * 乙醇
     */
    private BigDecimal Ethanol = new BigDecimal(0);
//    /**
//     * 其他
//     */
//    private BigDecimal other;
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
//    /**
//     * 其他修正量
//     */
//    private BigDecimal correctionOther;
    /**
     * 航程
     */
    private BigDecimal distance = new BigDecimal(0);
    /**
     * 航行小时数
     */
    private BigDecimal distanceHour = new BigDecimal(0);
    /**
     * 航程修正量
     */
    private BigDecimal correctionDistance = new BigDecimal(0);
    /**
     * 航行小时数修正量
     */
    private BigDecimal correctionDistanceHour = new BigDecimal(0);
}
