package cn.ccsit.eeoi.ship.dto;

import cn.ccsit.eeoi.ship.entity.ShipEquipmentFuelMap;
import cn.ccsit.eeoi.ship.vo.ShipEquipmentFuelMapVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class OiShipBoilerDto {

    private String id;
    private String shipId;
    @NotBlank(message = "锅炉型号为空")
    private String boilerType;
    @NotBlank(message = "能源类型我空")
    private String energyId;
    private String  mfrsName;
    private Date produceTime;
    private String xh;
    private List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps;


}
