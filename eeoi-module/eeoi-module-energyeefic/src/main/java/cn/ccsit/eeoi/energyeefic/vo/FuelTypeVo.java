package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FuelTypeVo {
    private String shipId;
    private Date periodTime;
    private String fuelCode;
    private String fuelName;
    private BigDecimal fuelTons = new BigDecimal(0);
}
