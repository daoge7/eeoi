package cn.ccsit.eeoi.energyeefic.vo;

import cn.ccsit.eeoi.energyeefic.entity.RawVoyagePort;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SegmentsVo {
    private String id;
    private String volageCode;
    private String imo;
    private String registerNo;
    private String shipNameCn;
    private String shipNameEn;
    private Date startTime;
    private Date endTime;
    private BigDecimal eeoiValue;
    private BigDecimal carbonDioxideEmission;
    private BigDecimal dropRatio;
    private BigDecimal capacityUtilization;
    private String recStatus;
    private Integer voyType;
    private Integer volageAndSegmentType;
    private String startPort;
    private String endPort;
    private String startPortEn;
    private String endPortEn;
    private BigDecimal cargo;
    private BigDecimal ballest;
    private BigDecimal hfo;
    private BigDecimal chaiOil;
    private BigDecimal distance;
    private BigDecimal distanceTime;
    private BigDecimal stopTime;
    private Integer voyageFlag;
    private List<VoyagePortVo> voyagePortVos;
    private List<VoyageSpecVo> voyageSpecVos = new ArrayList<>();
}
