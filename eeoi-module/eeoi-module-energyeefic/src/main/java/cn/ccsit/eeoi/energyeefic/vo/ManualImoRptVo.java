package cn.ccsit.eeoi.energyeefic.vo;

import cn.ccsit.eeoi.energyeefic.dto.SaveManuelDeviceDto;
import cn.ccsit.eeoi.energyeefic.entity.ImoOil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ManualImoRptVo {
    /**
     * 报告id
     */
    private String imoRptId;
    private String id;
    private String refCode;
    private String rptSource;
    private String recStatus;
    private Date startTm;
    private Date endTm;
    private String spType;
    private String countryFlag;
    private BigDecimal grossTons;
    private BigDecimal netTons;
    private BigDecimal dwTons;
    private BigDecimal eediVal;
    private String iceClass;
    private BigDecimal distOnsea;
    private BigDecimal hourOnsea;
    private List<SaveManuelDeviceDto> mainEngines;
    private List<SaveManuelDeviceDto> auxEngines;
    private List<ImoOil> imoOils;
}
