package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CmsaFuelTypeVo {
    private String oilId;
    private String oilName;
    private BigDecimal fuelTons = new BigDecimal(0);
    private String method;
    private String oilNameEn;
}
