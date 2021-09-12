package cn.ccsit.eeoi.ship.dto;

import cn.ccsit.eeoi.ship.entity.ShipEquipmentFuelMap;
import cn.ccsit.eeoi.ship.vo.ShipEquipmentFuelMapVo;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OiMainEngineDto {
    private String id;
    @NotBlank
    private String shipId;
    @NotBlank
    private String engineType;
    @NotNull
    private BigDecimal ratePower;
    private BigDecimal ratedSpeed;
    @NotNull
    private BigDecimal consumpRate;
    @NotBlank
    private String energyId;
    private String mfrsName;
    private Date produceTime;
    private String xh;
    private String serialNo;
    private List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps;

    public BigDecimal getRatePower() {
        return ratePower;
    }

    public void setRatePower(BigDecimal ratePower) {
        if(ratePower == null){
            ratePower = BigDecimal.ZERO;
        }
        this.ratePower = ratePower;
    }

    public BigDecimal getRatedSpeed() {
        return ratedSpeed;
    }

    public void setRatedSpeed(BigDecimal ratedSpeed) {
        if(ratedSpeed == null){
            ratedSpeed = BigDecimal.ZERO;
        }
        this.ratedSpeed = ratedSpeed;
    }

    public BigDecimal getConsumpRate() {
        return consumpRate;
    }

    public void setConsumpRate(BigDecimal consumpRate) {
        if(consumpRate == null){
            consumpRate = BigDecimal.ZERO;
        }
        this.consumpRate = consumpRate;
    }
}
