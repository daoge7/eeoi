package cn.ccsit.eeoi.energyeefic.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class FuelTypeAddVoApp {
    private String id;
    private String oilId;
    private String oilName;
    private BigDecimal addTons;
    private String addBillNo;
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

    public FuelTypeAddVoApp(String oilId, String oilName) {
        this.oilId = oilId;
        this.oilName = oilName;
    }

    public FuelTypeAddVoApp() {
    }
}
