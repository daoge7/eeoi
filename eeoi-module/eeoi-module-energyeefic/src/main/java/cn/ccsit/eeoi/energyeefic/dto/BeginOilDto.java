package cn.ccsit.eeoi.energyeefic.dto;

import cn.ccsit.common.vo.page.PageVo;
import cn.ccsit.eeoi.energyeefic.entity.BeginPeriodOil;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class BeginOilDto extends PageVo {
    /**
     * 查询关键字
     */
    private String keyWord;
    /**
     * 查询年份
     */
    private String year;
    /**
     * 船舶期初时间id
     */
    private String periodId;
    /**
     * 期初时间
     */
    private Date periodTime;
    /**
     * 船舶id
     */
    private String shipId;
    private List<BeginPeriodOilDto> beginPeriodOils;
}
