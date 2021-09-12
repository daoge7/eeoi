package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import java.util.List;

@Data
public class ImoStdRptStatusTagDto {
    private List<String> imoStdRptIds;
    private String imoStdId;
    private String rptSource;
}
