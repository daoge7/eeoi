package cn.ccsit.eeoi.energyeefic.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "ship.fuel.method")
@Data
public class FuelConsMethodProperties {
    private Map<String,String> mapEn;
    private Map<String,String> mapCn;
    private Map<String,String> shipMap;
}
