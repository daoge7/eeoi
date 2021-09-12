package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BeginPeriodOilDto {
    private String id;
    private String fuelCode;
    private String fuelName;
    private BigDecimal fuelTons;
}
