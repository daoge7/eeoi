package cn.ccsit.eeoi.ship.dto;

import cn.ccsit.eeoi.ship.entity.ShipEquipmentFuelMap;
import cn.ccsit.eeoi.ship.vo.ShipEquipmentFuelMapVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
public class OiIncineratorDto {
    private String id;
    @NotEmpty(message = "船舶id为空")
    private String shipId;
    @NotEmpty(message = "焚烧炉类型为空")
    private String model;
    private String mfrsName;
    private Date produceTime;
    @NotBlank
    private String energyId;
    private String xh;
    private List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps;
}
