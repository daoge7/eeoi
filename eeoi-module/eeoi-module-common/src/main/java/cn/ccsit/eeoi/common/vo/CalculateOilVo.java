package cn.ccsit.eeoi.common.vo;

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
    private BigDecimal oiHfoCorrect = new BigDecimal("0");
    private BigDecimal oiLfoCorrect = new BigDecimal("0");
    private BigDecimal oiChaiCorrect = new BigDecimal("0");
    private BigDecimal oiBingCorrect = new BigDecimal("0");
    private BigDecimal oiDingCorrect = new BigDecimal("0");
    private BigDecimal oiTianCorrect = new BigDecimal("0");
    private BigDecimal oiOtherCorrect = new BigDecimal("0");
    private BigDecimal oiethanolCorrect = new BigDecimal("0");
}
