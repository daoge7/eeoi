package cn.ccsit.eeoi.energyeefic.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoyageDeitailInfoOilVo {
    private String oilId;
    private String oilName;
    private BigDecimal distanceOil;
    private BigDecimal stopOil;
    private BigDecimal iceOil;
    private BigDecimal rescueOil;
}
