package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EneryEfficTrendIndexDataVo {
    /**
     * 时间或者航次
     */
    private String timeOrVoyage;
    /**
     * 指标数值
     */
    private BigDecimal data = new BigDecimal(0);

    private String name;
    /**
     * 指标标识id
     */
    private String indexId;
}
