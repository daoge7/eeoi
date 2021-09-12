package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculateOilVo {
    private BigDecimal oiHfo = new BigDecimal("0");
    private BigDecimal oiLfo = new BigDecimal("0");
    private BigDecimal oiChai = new BigDecimal("0");
    private BigDecimal oiBing = new BigDecimal("0");
    private BigDecimal oiDing = new BigDecimal("0");
    private BigDecimal oiTian = new BigDecimal("0");
    private BigDecimal oiOther = new BigDecimal("0");
    private BigDecimal oiethanol = new BigDecimal("0");
}
