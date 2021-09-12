package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReaptBeginPeriodOilDto {
    private String oilId;
    private String oilName;
    /**
     * 期初油量
     */
    private BigDecimal beginTons;
}
