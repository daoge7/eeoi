package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RawVoyageSpecDto {
    private String id;
    @NotBlank(message = "船舶不能为空")
    private String shipId;
    @NotBlank(message = "航次号不能为空")
    private String voyageCode;
    @NotBlank(message = "类型不能为空")
    private String recordType;
    @Past(message = "请选择一个过去的日期")
    private Date beginTm;
    private BigDecimal beginZone;
    @Past(message = "请选择一个过去的日期")
    private Date endTm;
    private BigDecimal endZone;
    @DecimalMin("0")
    private BigDecimal distance;
    private List<RawVoyageSpecConsDto> volageSpecCons;
    private String firstVoyageCode;

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        if(distance == null){
            distance = BigDecimal.ZERO;
        }
        this.distance = distance;
    }
}
