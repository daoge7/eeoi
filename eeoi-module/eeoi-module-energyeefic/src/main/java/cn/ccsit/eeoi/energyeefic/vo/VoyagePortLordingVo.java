package cn.ccsit.eeoi.energyeefic.vo;

import cn.ccsit.common.vo.page.PageVo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VoyagePortLordingVo {
    private String id;
    private String loadingType;
    private BigDecimal cargoTons;
    private BigDecimal ballastTons;
    private Integer allBoxNum;
    private Integer heavyBoxNum;
    private Integer peopleNum;
    private Integer carsNum;
}
