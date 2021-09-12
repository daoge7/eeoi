package cn.ccsit.eeoi.ship.dto;

import cn.ccsit.eeoi.ship.entity.ShipEquipmentFuelMap;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OiShipBatteryDto {
    private String id;
    @NotBlank(message = "船舶id为空")
    private String shipId;
    private String cellModel;
    private BigDecimal cellParam;
    private BigDecimal voltage;
    private BigDecimal capacity;
    private BigDecimal weight;
    private String batteryModel;
    private String serialNo;
    private Integer unitNum;
    private String mfrsName;
    private Date productDate;
    private String xh;
    private String memo;
}
