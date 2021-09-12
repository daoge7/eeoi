package cn.ccsit.eeoi.ship.vo;

import cn.ccsit.eeoi.ship.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OiShipEquipmentVo {
    List<OiMainEngine> mainEngineIds;
    List<OiShipGe> shipGeIds;
    List<OiShipGenerator> shipGeneratorIds;
    List<OiShipBoiler> shipBoilerIds;
    List<OiIncinerator> incineratorIds;
    List<OiShipInert> shipInert;
    List<OiShipTurbine> shipTurbine;
    List<OiShipOther> OtherEquipment;
    List<OiShipBattery> shipBatteryIds;
    List<OiShipPropeller> oiShipPropellers;

}
