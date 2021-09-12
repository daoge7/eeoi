package cn.ccsit.eeoi.energyeefic.dto;

import cn.ccsit.eeoi.ship.entity.OiMainEngine;
import cn.ccsit.eeoi.ship.entity.OiShipGe;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SaveManuelDcsDto {
    /**
     * bdn文件路径
     */
    private String bdnFileDir;
    /**
     * bdn文件名字
     */
    private String bdnFileName;
    /**
     * cd文件路径
     */
    private String cdFileDir;
    /**
     * cd文件名字
     */
    private String cdFileName;
    /**
     * 报告id
     */
    private String imoRptId;
    /**
     * 报告开始时间
     */
    private Date startTime;
    /**
     * 报告结束时间
     */
    private Date endTime;
    /**
     * 船舶imo号
     */
    private String imoNu;
    /**
     * 船舶型号
     */
    private String shipType;
    /**
     * 船旗国
     */
    private String flag;
    /**
     * 船旗国代码
     */
    private String flagCode;
    /**
     *总吨
     */
    private BigDecimal gross;
    /**
     *净吨
     */
    private BigDecimal net;
    /**
     *载重吨
     */
    private BigDecimal dwt;
    /**
     * 主机功率
     */
    private List<SaveManuelDeviceDto> oiMainEngines;
    /**
     * 副机功率
     */
    private List<SaveManuelDeviceDto> oiShipGes;
    /**
     * eedi值
     */
    private BigDecimal eedi;
    /**
     * 冰级
     */
    private String ice;

    /**
     * 航程
     */
    private BigDecimal distance;
    /**
     * 航行小时数
     */
    private BigDecimal distanceHour;
    /**
     * 燃油消耗
     */
    private List<ManualOilDto> manualOilDtos;
    /**
     * 船舶id
     */
    private String shipId;
}
