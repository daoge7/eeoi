package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class VoyageDetailInfoApp {
    private String startPort;
    private String endPort;
    private String startPortCn;
    private String endPortCn;
    private Date startTime;
    private Date endTime;
    private BigDecimal distance;
    private BigDecimal distanceHour;
    private BigDecimal cargo;
    private BigDecimal ballest;
    private BigDecimal hfo;
    private BigDecimal lfo;
    private BigDecimal chai;
    private BigDecimal stopTime;
}
