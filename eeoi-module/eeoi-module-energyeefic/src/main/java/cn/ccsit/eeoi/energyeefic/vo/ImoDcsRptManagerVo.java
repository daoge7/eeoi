package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.util.Date;
@Data
public class ImoDcsRptManagerVo {
    private String id;
    /**
     * imo号
     */
    private String imoNu;
    /**
     * 管理公司
     */
    private String docManager;
    /**
     * 船旗国
     */
    private String flag;
    /**
     * 船舶类型
     */
    private String shipType;
    /**
     * 报告开始日期
     */
    private Date rptStartTime;
    /**
     * 报告结束日期
     */
    private Date rptEndTime;
    /**
     * 报告状态
     */
    private String rptStatus;

    private String reaportSource;

    private String shipName;

    private String shipId;
    private String euMrvYear;
    private String rptType;
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
     * 如果是航次报 航次id有值
     */
    private String taskId;
}
