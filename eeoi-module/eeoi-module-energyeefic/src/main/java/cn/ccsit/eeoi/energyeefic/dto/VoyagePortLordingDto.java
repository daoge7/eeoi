package cn.ccsit.eeoi.energyeefic.dto;

import cn.ccsit.common.vo.page.PageVo;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class VoyagePortLordingDto extends PageVo {
    private String id;
    @NotBlank(message = "载荷类型为空")
    private String loadingType;
    @DecimalMin("0")
    private BigDecimal cargoTons;
    @DecimalMin("0")
    private BigDecimal ballastTons;
    @Min(0)
    private Integer allBoxNum;
    @Min(0)
    private Integer heavyBoxNum;
    @Min(0)
    private Integer peopleNum;
    @Min(0)
    private Integer carsNum;

    public BigDecimal getCargoTons() {
        return cargoTons;
    }

    public void setCargoTons(BigDecimal cargoTons) {
        if(cargoTons == null){
            cargoTons = BigDecimal.ZERO;
        }
        this.cargoTons = cargoTons;
    }

    public BigDecimal getBallastTons() {
        return ballastTons;
    }

    public void setBallastTons(BigDecimal ballastTons) {
        if(ballastTons == null){
            ballastTons = BigDecimal.ZERO;
        }
        this.ballastTons = ballastTons;
    }

    public Integer getAllBoxNum() {
        return allBoxNum;
    }

    public void setAllBoxNum(Integer allBoxNum) {
        if(allBoxNum == null){
            allBoxNum = 0;
        }
        this.allBoxNum = allBoxNum;
    }

    public Integer getHeavyBoxNum() {
        return heavyBoxNum;
    }

    public void setHeavyBoxNum(Integer heavyBoxNum) {
        if(heavyBoxNum == null){
            heavyBoxNum = 0;
        }
        this.heavyBoxNum = heavyBoxNum;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        if(peopleNum == null){
            peopleNum = 0;
        }
        this.peopleNum = peopleNum;
    }

    public Integer getCarsNum() {
        return carsNum;
    }

    public void setCarsNum(Integer carsNum) {
        if(carsNum == null){
            carsNum = 0;
        }
        this.carsNum = carsNum;
    }
}
