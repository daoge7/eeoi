package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManualOilDto {
    private String oilId;
    /**
     * 油耗量
     */
    private BigDecimal fuelTons;
    /**
     * 燃油测量方法
     */
    private String useMethod;
    private String oilName;
}
