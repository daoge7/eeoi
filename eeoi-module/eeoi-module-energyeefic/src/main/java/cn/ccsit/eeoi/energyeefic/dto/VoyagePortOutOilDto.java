package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class VoyagePortOutOilDto {
    private String id;
    @NotBlank(message = "油类型不能为空")
    private String oilId;
    private String oilName;
    private BigDecimal outTons;
    @Past(message = "请选择一个过去的日期")
    private Date outTm;

    public BigDecimal getOutTons() {
        return outTons;
    }

    public void setOutTons(BigDecimal outTons) {
        if(outTons == null){
            outTons = BigDecimal.ZERO;
        }
        this.outTons = outTons;
    }
}
