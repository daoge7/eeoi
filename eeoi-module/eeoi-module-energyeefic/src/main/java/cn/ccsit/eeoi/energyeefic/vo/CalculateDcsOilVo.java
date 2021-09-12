package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculateDcsOilVo {
    private BigDecimal hfoHight = new BigDecimal(0);
    private BigDecimal hfoLow = new BigDecimal(0);
    private BigDecimal hfoSuperLow = new BigDecimal(0);
    private BigDecimal lfoHight = new BigDecimal(0);
    private BigDecimal lfoLow = new BigDecimal(0);
    private BigDecimal lfoSuperLow = new BigDecimal(0);
    private BigDecimal chaiFo = new BigDecimal(0);
    private BigDecimal propane = new BigDecimal(0);
    private BigDecimal butane = new BigDecimal(0);
    private BigDecimal lng = new BigDecimal(0);
    private BigDecimal methanol = new BigDecimal(0);
    private BigDecimal Ethanol = new BigDecimal(0);
    private BigDecimal other = new BigDecimal(0);
}
