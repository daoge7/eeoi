package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class VoyagePortAddOilDto {
    private String id;
    @NotBlank(message = "油类型不能为空")
    private String oilId;
    private String oilName;
    @DecimalMin("0")
    private BigDecimal addTons;
    private String addBillNo;
    @Past(message = "请选择一个过去的日期")
    private Date addTm;
    /**
     * 硫含量
     */
    private BigDecimal sulfurPercent = new BigDecimal(0);
    /**
     *燃油牌号
     */
    private String isoBrand;
    /**
     * 粘度
     */
    private BigDecimal viscosity = new BigDecimal(0);

    public BigDecimal getAddTons() {
        return addTons;
    }

    public void setAddTons(BigDecimal addTons) {
        if(addTons == null){
            addTons = BigDecimal.ZERO;
        }
        this.addTons = addTons;
    }

    public BigDecimal getSulfurPercent() {
        return sulfurPercent;
    }

    public void setSulfurPercent(BigDecimal sulfurPercent) {
        if(sulfurPercent == null){
            sulfurPercent = BigDecimal.ZERO;
        }
        this.sulfurPercent = sulfurPercent;
    }

    public BigDecimal getViscosity() {
        return viscosity;
    }

    public void setViscosity(BigDecimal viscosity) {
        if(viscosity == null){
            viscosity = BigDecimal.ZERO;
        }
        this.viscosity = viscosity;
    }
}
