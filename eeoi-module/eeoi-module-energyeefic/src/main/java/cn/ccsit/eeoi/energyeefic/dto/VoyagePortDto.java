package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class VoyagePortDto {
    private String id;
    private String portid;
    private String porten;
    private String portcn;
    private Boolean isEu;
    private String imoNo;
//    private String recStatus;
    @NotBlank(message = "船舶不能为空")
    private String shipId;
    @NotBlank(message = "航次号不能为空")
    private String firstVolageCode;
    private String secondVolageCode;
    @Past(message = "抵港时间请选择一个过去的日期")
    private Date arrTm;
    @Past(message = "离港时间请选择一个过去的日期")
    private Date deptTm;
    private BigDecimal arrZone;
    private BigDecimal deptZone;
    private Boolean inPort;
    @DecimalMin(value = "0", message = "航程必须大于等于0")
    private BigDecimal distance;
    @DecimalMin(value = "0", message = "岸电使用量必须大于等于0")
    private BigDecimal shorePower;
    private List<String> anchorPurpose;
    @NotBlank(message = "记录类型不能为空")
    private String recordType;

    private String refCode;
    /**
     * 港口油耗
     */
    private List<VoyagePortOilDto> portOilInfos;
    /**
     * 港口载荷
     */
    private List<VoyagePortLordingDto> volagePortLoadingInfos;
    /**
     * 港口加油量
     */
    private List<VoyagePortAddOilDto> voyagePortAddOilDtos;
    /**
     * 港口驳出量
     */
    private List<VoyagePortOutOilDto> voyagePortOutOilDtos;
    /**
     * 港口油渣驳出量
     */
    private List<VoyagePortOutSulageOilDto> voyagePortOutSulageOilDtos;

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        if (distance == null) {
            distance = BigDecimal.ZERO;
        }
        this.distance = distance;
    }

    public BigDecimal getShorePower() {
        return shorePower;
    }

    public void setShorePower(BigDecimal shorePower) {
        if (shorePower == null) {
            shorePower = BigDecimal.ZERO;
        }
        this.shorePower = shorePower;
    }
}
