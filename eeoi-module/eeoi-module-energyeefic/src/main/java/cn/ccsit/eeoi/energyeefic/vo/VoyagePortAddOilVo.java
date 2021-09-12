package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class VoyagePortAddOilVo {
    private String id;
    private String oilId;
    private String oilName;
    private BigDecimal addTons;
    private BigDecimal sulfurPercent;
    private BigDecimal viscosity;
    private String isoBrand;
    private String addBillNo;
    private Date addTm;
}
