package cn.ccsit.eeoi.energyeefic.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConfigurationProperties(prefix = "ship.fuel.so2")
@Data
public class FuelSo2CostProperties {
    /**
     * 高硫
     */
    private BigDecimal HSHFO;
    /**
     * 低硫
     */
    private BigDecimal LSHFO;
    /**
     * 超低硫
     */
    private BigDecimal SLSHFO;
}
