package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReaptPeriodOilsDto {
    private String imoNo;
    private Date periodTime;
    private List<ReaptBeginPeriodOilDto> beginPeriodOils;
}
