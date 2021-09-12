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
public class OiShipGeneratorDto {
    private String id;
    private String axisstatus;
    @NotBlank
    private String shipId;
    @NotBlank
    private String engineType;
    @NotNull
    private BigDecimal ratePower;
    @NotNull
    private BigDecimal rateVoltage;
    private String mfrsName;
    private Date produceTime;
    private String xh;
    private List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps;
}
