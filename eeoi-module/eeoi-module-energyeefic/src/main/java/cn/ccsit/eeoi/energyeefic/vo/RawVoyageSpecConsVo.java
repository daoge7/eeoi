package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class RawVoyageSpecConsVo {
    private String id;
    private String oilId;
    private String oilName;
    private BigDecimal consTons;
    private BigDecimal outTons;
    private Date outTm;
}
