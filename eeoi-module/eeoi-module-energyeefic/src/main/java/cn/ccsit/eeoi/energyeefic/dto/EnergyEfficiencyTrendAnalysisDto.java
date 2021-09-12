package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import java.util.List;
@Data
public class EnergyEfficiencyTrendAnalysisDto {
    private String startYear;
    private String endYear;
    private String shipCompanyId;
    private String shipId;
    private String shipTypeCode;
    private String shipTypeSub;
    private String grossRange;
    private String shipAgeRange;
    /**
     * 滚动周期单位 月 季 年 航次 四个单位
     */
    private String rollingCycleUnit;
    /**
     * 滚动周期
     */
    private Integer rollingCycle;
    /**
     * 能效趋势分析指标项 1：eeoi 2：每海里油耗 3：每运输单位油耗 4：每海里co2排放
     * 5：每运输单位co2排放 6：降速比 7：载货量利用率
     */
    private List<String> indexItems;
    /**
     * 下载数据中英文类型 0：英文 1：中文
     */
    private String type;

}
