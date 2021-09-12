package cn.ccsit.eeoi.energyeefic.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MrvDataAssessmentVo {
    /**
     * 船舶名字
     */
    private String shipName;
    /**
     * 航段开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    /**
     * 航段结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    /**
     *每海里油耗（tons/nm）
     */
    private BigDecimal fuelPerMile;
    /**
     *每海里油耗（tons/nm）超出历史数据数值，如果数据没有异常为0
     */
    private BigDecimal fuelPerMileAbnormalData;
    /**
     * 每海里二氧化碳排放
     */
    private BigDecimal co2PerMile;
    /**
     * 每海里二氧化碳排放超出历史数据数值，如果数据没有异常为0
     */
    private BigDecimal co2AbnormalData;
    /**
     * 每吨海海里油耗(g/t*nm)
     */
    private BigDecimal fuelPerMileTon;
    /**
     * 每吨海海里油耗(g/t*nm)超出历史数据数值，如果数据没有异常为0
     */
    private BigDecimal fuelPerMileTonAbnormalData;
    /**
     *  每吨海海里CO2排放(g/t*nm)
     */
    private BigDecimal co2PerMileTons;
    /**
     *  每吨海海里CO2排放(g/t*nm)超出历史数据数值，如果数据没有异常为0
     */
    private BigDecimal co2PerMileTonsAbnormalData;
    /**
     * 每日油耗(tons)
     */
    private BigDecimal dayFuelTons;
    /**
     * 停港每日油耗(tons)
     */
    private BigDecimal dayFuelTonsStop;
    /**
     * 每日油耗(tons)超出历史数据数值，如果数据没有异常为0
     */
    private BigDecimal dayFuelTonsAbnormalData;
    /**
     * 平均航速
     */
    private BigDecimal avgSpeed;
    /**
     * 航段航程
     */
    private BigDecimal distance;
    /**
     *降速比
     */
    private BigDecimal dropRadio;
    /**
     * 载货量利用率
     */
    private BigDecimal loadUtilizationRate;
    /**
     * 平均航速超出历史数据数值，如果数据没有异常为0
     */
    private BigDecimal avgSpeedAbnormalData;
    private String startPort;
    private String endPort;
    private String taskNu;
    private String segmentNu;

}
