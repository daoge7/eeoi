package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class VoyagePortOilVo {
    private String id;
    private String oilId;
    private String oilName;
    private BigDecimal arrTons;
    private BigDecimal deptTons;
    private BigDecimal correctTons;
    /**
     * 加油量汇总
     */
    private BigDecimal addTons;
    /**
     * 驳油量汇总
     */
    private BigDecimal outTons;
}
