package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InformationDto {
    /**
     * 船舶imo
     */
    private String shipImono;
    /**
     * 验证token
     */
    private String token;
    /**
     * 滚动周期
     */
    private Integer rollPeriod;
    /**
     * 核验时间
     */
    private String auditTime;


    /**
     *数据类型 0：船舶1：船公司
     */
    private Integer type;
}
