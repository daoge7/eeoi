package cn.ccsit.eeoi.energyeefic.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class ImoRptTimeVo {
    /**
     * dcs报告时间区间开始日期
     */
    private Date startTime;
    /**
     * dcs报告时间区间结束日期
     */
    private Date endTime;
    /**
     * dcs报告生成状态 0:可生成 1:不可生成 2:已生成
     */
    private Integer imoRptStatus;
    /**
     * imo报告id
     */
    private String imoRptId;
    /**
     * 船舶id
     */
    private String shipId;
}
