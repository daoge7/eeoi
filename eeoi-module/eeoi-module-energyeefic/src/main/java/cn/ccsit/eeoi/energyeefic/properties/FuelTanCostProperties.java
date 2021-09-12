package cn.ccsit.eeoi.energyeefic.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConfigurationProperties(prefix = "ship.fuel.tan")
@Data
public class FuelTanCostProperties {
    /**
     * 重燃油
     */
    private BigDecimal hfo;
    /**
     * 轻燃油
     */
    private BigDecimal lfo;
    /**
     * 液化天然气
     */
    private BigDecimal lng;
    /**
     * 柴油和汽油
     */
    private BigDecimal oiChaiOrOiQi;
    /**
     * 丁烷
     */
    private BigDecimal butane;
    /**
     * 丙烷
     */
    private BigDecimal propane;
}
