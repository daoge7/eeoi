package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateVoyageRecStatusDto {
    @NotBlank(message = "航次号id不能为空")
    private String volageId;
    @NotBlank(message = "数据状态不能为空")
    private String recStatus;
}
