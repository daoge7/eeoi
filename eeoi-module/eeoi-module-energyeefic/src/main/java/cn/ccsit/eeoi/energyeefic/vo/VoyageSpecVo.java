package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class VoyageSpecVo {
    private String id;
    private String shipId;
    private String shipName;
    private String shipNameEn;
    private String recodeType;
    private String voyageCode;
    private Date iceStartTime;
    private Date iceEndTime;
    private BigDecimal beginZone;
    private BigDecimal distance;
    private BigDecimal hfo;
    private BigDecimal lfo;
    private BigDecimal diesel;
    private BigDecimal otherOil;
    private List<RawVoyageSpecConsVo> rawVoyageSpecConsVos;
}
