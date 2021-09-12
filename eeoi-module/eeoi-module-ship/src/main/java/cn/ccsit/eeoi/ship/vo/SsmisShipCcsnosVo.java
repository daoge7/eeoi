package cn.ccsit.eeoi.ship.vo;

import cn.ccsit.eeoi.ship.dto.SsmisOiShipInfoDto;
import lombok.Data;

import java.util.List;

@Data
public class SsmisShipCcsnosVo {
    private List<SsmisOiShipInfoDto> ccsnos;
}
