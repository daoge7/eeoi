package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CmsaRptDto {
    /**
     * 是否更换船旗国
     */
    private Boolean isTurnToFlalg;
    /**
     * 是否更换经营人
     */
    private Boolean isTurnToDoc;
    /**
     * 是不适用
     */
    private Boolean notUse;
    /**
     * 航次、报
     */
    private Boolean voyageRpt;
    /**
     * 年报月报
     */
    private Boolean yearRpt;
    /**
     * 报告开始日期
     */
    private Date startTime;
    /**
     * 报告结束日期
     */
    private Date endTime;

    private String taskId;
    private String shipId;
    private String id;
    /**
     * 状态标记id集合
     */
    private List<String> cmsaRptIds;

}
