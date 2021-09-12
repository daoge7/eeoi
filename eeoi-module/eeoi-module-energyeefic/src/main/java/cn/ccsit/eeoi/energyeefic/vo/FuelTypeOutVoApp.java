package cn.ccsit.eeoi.energyeefic.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelTypeOutVoApp {
    private String id;
    private String oilId;
    private String oilName;
    private BigDecimal outTons;
    private Date outTm;
}
