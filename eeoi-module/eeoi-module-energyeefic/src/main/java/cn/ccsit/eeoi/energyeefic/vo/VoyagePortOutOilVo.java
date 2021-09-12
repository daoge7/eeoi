package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class VoyagePortOutOilVo {
    private String id;
    private String oilId;
    private String oilName;
    private BigDecimal outTons;
    private Date outTm;
}
