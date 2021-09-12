package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class VoyagePortOutSulageOilDto {
    private String id;
    @NotBlank(message = "油类型不能为空")
    private String oilId;
    private String oilName;
    @Past(message = "请选择一个过去的日期")
    private Date sludgeOutTm;

    private Date outTm;//驳出时间
    /**
     * 油渣量
     */
    private BigDecimal sludgeTons;

    public BigDecimal getSludgeTons() {
        return sludgeTons;
    }

    public void setSludgeTons(BigDecimal sludgeTons) {
        if(sludgeTons == null){
            sludgeTons = BigDecimal.ZERO;
        }
        this.sludgeTons = sludgeTons;
    }
}
