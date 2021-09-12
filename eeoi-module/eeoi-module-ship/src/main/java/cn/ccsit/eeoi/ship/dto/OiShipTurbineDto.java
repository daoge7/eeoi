package cn.ccsit.eeoi.ship.dto;

import cn.ccsit.eeoi.ship.vo.ShipEquipmentFuelMapVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OiShipTurbineDto {
    private String id;
    @NotBlank(message = "船舶id为空")
    private String shipId;
    @NotBlank(message = "涡轮机类型为空")
    private String engineType;
    @NotNull(message = "额定功率为空")
    private BigDecimal ratePower;
    @NotBlank(message = "能源类型为空")
    private String energyid;
    private String xh;
    private String serialNo;
    private List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps;

}
