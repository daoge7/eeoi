package cn.ccsit.eeoi.energyeefic.vo;

import cn.ccsit.eeoi.energyeefic.entity.ImoOil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ReaportImoRptVo {
    private String startTime;
    private String endTime;
    private String recStatus;
    private String imoNu;
    private String shipType;
    private String shipTypeOther;
    private BigDecimal gross;
    private BigDecimal net;
    private BigDecimal dwt;
    private BigDecimal eedi;
    private String ice;
    private List<String> mainEngines;
    private List<String> auxEngines;
    private BigDecimal distance;
    private BigDecimal distanceHour;
    private String refCode;
    private String flag;
    private List<ImoOilVo> imoOils;
    private String id;

}
