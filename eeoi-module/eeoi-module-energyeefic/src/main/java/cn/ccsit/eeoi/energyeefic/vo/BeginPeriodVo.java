package cn.ccsit.eeoi.energyeefic.vo;

import cn.ccsit.eeoi.energyeefic.dto.BeginPeriodOilDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BeginPeriodVo {
    private String periodId;
    private String shipId;
    private Date periodTime;
    private String shipName;
    private String recStatus;
    private List<BeginPeriodOilDto> beginPeriodOilDtos;

}
