package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class VoyageDetailInfo {
    private String taskNu;
    private String voyageNu;
    private String startPort;
    private String endPort;
    private Date startTime;
    private Date endTime;
    private BigDecimal distance;
    private BigDecimal stopTime;
    private BigDecimal avgSpeed;
    private BigDecimal cargo;
    private BigDecimal ballest;
    private Integer allBox;
    private Integer heavyBox;
    private Integer cars;
    private Integer peoples;
    private List<VoyageDeitailInfoOilVo> voyageDeitailInfoOilVos;

}
