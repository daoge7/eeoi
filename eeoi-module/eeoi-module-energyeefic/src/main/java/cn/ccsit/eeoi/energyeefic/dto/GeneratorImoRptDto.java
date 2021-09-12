package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class GeneratorImoRptDto {
    /**
     * 船舶id
     */
    private String shipId;
    /**
     *imo报告开始时间
     */
    private Date startTime;
    /**
     *imo报告结束时间
     */
    private Date endTime;
    /**
     *imo报告来源
     */
    private String rptResource;
    /**
     *imo报告id
     */
    private String imoRptId;
    /**
     * 生成报告的年份
     */
    private Integer year;
}
