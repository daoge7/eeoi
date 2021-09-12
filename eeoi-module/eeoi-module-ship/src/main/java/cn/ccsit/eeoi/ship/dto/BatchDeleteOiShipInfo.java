package cn.ccsit.eeoi.ship.dto;

import lombok.Data;

import java.util.List;

@Data
public class BatchDeleteOiShipInfo {
    private List<OiShipInfoDto> shipIds;
}
