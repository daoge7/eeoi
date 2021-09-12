package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ReaportVoyagePortDto {
    private String id;
    private String portid;
    private String porten;
    private String portcn;
    private String isEu;
    private String imoNo;
    private String shipId;
    private String firstVoyageCode;
    private String secondVoyageCode;
    private Date arrTm;
    private Date deptTm;
    private BigDecimal arrZone;
    private BigDecimal deptZone;
    private String inPort;
    private BigDecimal distance;
    private BigDecimal shorePower;
    private String portWork;
    private String recordType;
    /**
     *
     */
    private String refCode;
    /**
     * 港口油耗
     */
    private List<VoyagePortOilDto> voyagePortoils;
    /**
     * 港口载荷
     */
    private List<VoyagePortLordingDto> voyagePortloadings;
    /**
     * 港口加油量
     */
    private List<VoyagePortAddOilDto> voyageAddoils;
    /**
     * 港口驳出量
     */
    private List<VoyagePortOutOilDto> voyageOutoils;
    /**
     * 港口油渣驳出量
     */
    private List<VoyagePortOutSulageOilDto> voyageSludges;
}
