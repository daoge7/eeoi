package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SegmentMonitoringVo {
    /**
     * 船舶名字
     */
    private String shipName;
    /**
     * 开始港口
     */
    private String startPort;
    /**
     * 结束港口
     */
    private String endPort;
    /**
     * 开始港离港时间
     */
    private Date satrtPortDeptTime;
    /**
     * 结束港离港时间
     */
    private Date endPortDeptTime;
    /**
     * 航程
     */
    private BigDecimal distance;
    /**
     * 载货量
     */
    private BigDecimal cargo;
    /**
     * 航行时间
     */
    private BigDecimal sailTime;
    /**
     * 停泊时间
     */
    private BigDecimal stopTime;
    /**
     * 重油航行油耗
     */
    private BigDecimal oilHfo;
    /**
     * 轻油航行油耗
     */
    private BigDecimal oilLfo;
    /**
     * 柴油汽油航行油耗
     */
    private BigDecimal oilChaiQi;
    /**
     * 重油停港油耗
     */
    private BigDecimal stopOilHfo;
    /**
     * 轻油停港航行油耗
     */
    private BigDecimal stopOilLfo;
    /**
     * 柴油汽油停港油耗
     */
    private BigDecimal stopOilChaiQi;

    private String taskNu;
    private String segmentNu;

}
