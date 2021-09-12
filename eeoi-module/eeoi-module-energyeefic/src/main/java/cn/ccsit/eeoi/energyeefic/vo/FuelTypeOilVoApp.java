package cn.ccsit.eeoi.energyeefic.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelTypeOilVoApp {
    private String id;
    private String oilId;
    private String oilName;
    private BigDecimal arrTons;
    private BigDecimal deptTons;
    private BigDecimal addTons;
    private BigDecimal correctTons;
}
