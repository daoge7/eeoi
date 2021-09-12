package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class



RawVoyageSpecConsDto {
    private String id;
    @NotBlank(message = "油耗种类不能为空")
    private String oilId;
    private String oilName;
    @DecimalMin("0")
    private BigDecimal consTons = new BigDecimal(0);
    @DecimalMin("0")
    private BigDecimal outTons = new BigDecimal(0);
    private Date outTm;

    public BigDecimal getConsTons() {
        return consTons;
    }

    public void setConsTons(BigDecimal consTons) {
        if(consTons == null){
            consTons = BigDecimal.ZERO;
        }
        this.consTons = consTons;
    }

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
