package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ImoOilVo {
    private BigDecimal quantity;
    private String type;
    private String dataCollectionMethod;
    private String oilId;
}
