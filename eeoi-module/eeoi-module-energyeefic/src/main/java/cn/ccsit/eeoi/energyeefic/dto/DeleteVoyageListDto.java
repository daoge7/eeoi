package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class DeleteVoyageListDto {
    @NotEmpty(message = "航次id不能为空")
    private List<String> volageIdList;
    @NotEmpty(message = "港口id不能为空")
    private List<String> volagePortIdList;
    @NotEmpty(message = "冰区航行和救援id不能为空")
    private List<String> volageIceAndRescueIdList;
    private List<GeneratorVoyageDto> generatorVoyageDtos;
}
