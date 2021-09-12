package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class VoyagePortVo {
    private String id;
    private Integer recodeType;
    private String recordType;
    private BigDecimal arrZone;
    private BigDecimal deptZone;
    private Integer isEu;
    private BigDecimal shorePower;
    private String firstVolageCode;
    private String secondVolageCode;
    private String shipId;
    private String portId;
    private String shipName;
    private String shipNameEn;
    private String portName;
    private String portNameCn;
    private Boolean inPort;
    private Date arrivalTime;
    private Date formThePortTime;
    private Date localArrivalTime;
    private Date localFormThePortTime;
    private String recStatus;
    private List<String> portWork;
    private BigDecimal distance;
    private BigDecimal arrivalLoad;
    private BigDecimal cargoOutOfPort;
    private List<VoyagePortOilVo> voyagePortOilVos;
    private List<VoyagePortLordingVo> voyagePortLordingVos;
    private List<VoyagePortAddOilVo> voyagePortAddOilVos;
    private List<VoyagePortOutOilVo> voyagePortOutOilVos;
    private List<VoyagePortOutSulageOilVo> voyagePortOutSulageOilVos;
}
