package cn.ccsit.eeoi.energyeefic.dto;

import cn.ccsit.common.vo.page.PageVo;
import lombok.Data;

@Data
public class MrvDataAssessmentDto extends PageVo {
    private String shipComPanyName;
    private String shipName;
    private String year;
    /**
     * 是否是欧盟港口
     */
    private Integer isEu;
    /**
     * 是否是异常数据
     */
    private Boolean isAbnormalData;
    /**
     * 航次号
     */
    private String taskNu;
}
