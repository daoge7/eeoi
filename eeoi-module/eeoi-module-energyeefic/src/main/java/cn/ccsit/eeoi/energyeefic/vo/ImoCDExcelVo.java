package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ImoCDExcelVo {
    private String btime;
    private String etime;
    private BigDecimal distance;
    private BigDecimal hourOnSea;
    private BigDecimal oiChai;
    private BigDecimal oiLfo;
    private BigDecimal oiHfo;
    private BigDecimal oiBing;
    private BigDecimal oiDing;
    private BigDecimal oiTian;
    private BigDecimal oiOther;
    private Date btimeDate;
}
