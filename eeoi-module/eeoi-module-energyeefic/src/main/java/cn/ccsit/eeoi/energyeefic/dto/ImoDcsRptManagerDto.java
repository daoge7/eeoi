package cn.ccsit.eeoi.energyeefic.dto;

import cn.ccsit.common.vo.page.PageVo;
import lombok.Data;

import java.util.Date;
@Data
public class ImoDcsRptManagerDto extends PageVo {
    /**
     * 船名、imo、船公司名称
     */
    private String keyword;
    /**
     * 船旗国
     */
    private String flag;
    /**
     * 报告开始时间
     */
    private Date startTime;
    /**
     * 报告结束时间
     */
    private Date endTime;

    private String imoRptStatus;
    private String id;
}
