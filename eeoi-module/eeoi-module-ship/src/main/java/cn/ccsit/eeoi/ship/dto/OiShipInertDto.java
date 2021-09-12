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
public class OiShipInertDto {
    private String id;
    @NotBlank(message = "船舶id为空")
    private String shipId;
    @NotBlank(message = "能源类型我空")
    private String energyid;
    private String mfrsName;
    private Date produceTime;
    @NotNull(message = "功率为空")
    private BigDecimal power;
    private String inertOiltype;
    private String xh;
    private List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps;

}
