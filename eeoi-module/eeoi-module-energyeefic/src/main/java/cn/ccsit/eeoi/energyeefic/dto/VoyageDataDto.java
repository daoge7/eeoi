package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import java.util.List;
@Data
public class VoyageDataDto {
    private List<ReaportVoyagePortDto> voyagePorts;
    private List<RawVoyageSpecDto> voyageSpecs;
    /**
     * 期初油量集合
     */
    private List<ReaptPeriodOilsDto> periods;
}
