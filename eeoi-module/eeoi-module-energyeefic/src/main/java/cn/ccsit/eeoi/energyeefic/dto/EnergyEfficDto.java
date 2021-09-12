package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

@Data
public class EnergyEfficDto {
    /**
     * 船公司
     */
    private String comPany;

    private String shipName;

    private String spTypeCode;
    private String startYear;
    private String endYear;
    /**
     * 导出中英文类型 0:英文类型 1:中文类型
     */
    private String exportType;
    /**
     * 导出的数据类型 0：能效数据 1：能效指标
     */
    private String dataType;
}
