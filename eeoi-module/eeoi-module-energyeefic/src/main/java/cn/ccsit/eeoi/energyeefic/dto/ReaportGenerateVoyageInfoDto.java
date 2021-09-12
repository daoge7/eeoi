package cn.ccsit.eeoi.energyeefic.dto;

import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import lombok.Data;

@Data
public class ReaportGenerateVoyageInfoDto {
    private Integer year;
    private String taskNu;
    private OiShipInfo oiShipInfo;

}
