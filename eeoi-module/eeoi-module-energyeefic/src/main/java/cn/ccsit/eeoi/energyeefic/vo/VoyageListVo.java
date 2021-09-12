package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
public class VoyageListVo {
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
    /**
     * 航次的运输功
     */
    private BigDecimal eeoiTd;
    private BigDecimal dropRatio;
    private BigDecimal capacityUtilization;
    private String recStatus;
    private List<SegmentsVo> children;
    private List<VoyagePortVo> voyagePortVos;
    private List<VoyageSpecVo> voyageSpecVos;
    private Boolean hasChildren;
    private Integer volageAndSegmentType;
    private String shipId;
    private String startPortEn;
    private String endPortEn;
    private String startPortCn;
    private String endPortCn;
    private BigDecimal distance;
    private BigDecimal distanceHour;
    private Integer voyType;
    private Integer voyageFlag; //0:航次 1：航段

}
