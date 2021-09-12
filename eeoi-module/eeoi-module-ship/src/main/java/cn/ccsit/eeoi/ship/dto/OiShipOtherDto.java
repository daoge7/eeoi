package cn.ccsit.eeoi.ship.dto;

import cn.ccsit.eeoi.ship.entity.ShipEquipmentFuelMap;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OiShipOtherDto {
    private String id;
    @NotBlank(message = "船舶id不为空")
    private String shipId;
    private String type;
    private BigDecimal powner;
    private String equipmentName;
    private String xh;
}
