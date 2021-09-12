package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class VoyagePortOilDto {
    private String id;
    @NotBlank(message = "油类型不能为空")
    private String oilId;
    private String oilName;
    @DecimalMin(value = "0",message = "抵港油量不能小于0")
    private BigDecimal arrTons;
    @DecimalMin(value = "0",message = "离港油量不能小于0")
    private BigDecimal deptTons;
    @DecimalMin("0")
    private BigDecimal addTons;
    private String addBillNo;
    @Past(message = "请选择一个过去的日期")
    private Date addTm;
    private BigDecimal outTons;
    @Past(message = "请选择一个过去的日期")
    private Date outTm;
    private BigDecimal correctTons;
    private Date correctTm;
    private BigDecimal sulfurPercent;

    public void setSulfurPercent(BigDecimal sulfurPercent) {
        if(sulfurPercent == null){
            sulfurPercent = BigDecimal.ZERO;
        }
        this.sulfurPercent = sulfurPercent.divide(new BigDecimal(100));
    }

    public BigDecimal getArrTons() {
        return arrTons;
    }

    public void setArrTons(BigDecimal arrTons) {
        if(arrTons == null){
            arrTons = BigDecimal.ZERO;
        }
        this.arrTons = arrTons;
    }

    public BigDecimal getDeptTons() {
        return deptTons;
    }

    public void setDeptTons(BigDecimal deptTons) {
        if(deptTons == null){
            deptTons = BigDecimal.ZERO;
        }
        this.deptTons = deptTons;
    }

    public BigDecimal getCorrectTons() {
        return correctTons;
    }

    public void setCorrectTons(BigDecimal correctTons) {
        if(correctTons == null){
            correctTons = BigDecimal.ZERO;
        }
        this.correctTons = correctTons;
    }

    public BigDecimal getSulfurPercent() {
        return sulfurPercent;
    }
}
