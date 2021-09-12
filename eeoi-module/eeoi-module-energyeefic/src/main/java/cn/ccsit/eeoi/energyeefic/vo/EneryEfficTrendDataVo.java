package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EneryEfficTrendDataVo {
    private BigDecimal eeoi = new BigDecimal(0);
    private BigDecimal fuelPerMile= new BigDecimal(0);
    private BigDecimal fuelPerT= new BigDecimal(0);
    private BigDecimal co2PerMile= new BigDecimal(0);
    private BigDecimal co2PerT= new BigDecimal(0);
    private BigDecimal dropRadio= new BigDecimal(0);
    private BigDecimal userRate= new BigDecimal(0);
    private String time;
    private String condition;
    private String name;
}
