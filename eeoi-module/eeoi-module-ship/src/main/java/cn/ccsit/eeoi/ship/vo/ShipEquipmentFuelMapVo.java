package cn.ccsit.eeoi.ship.vo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
@Data
public class ShipEquipmentFuelMapVo {
    private String consMethod;
    private String fuelId;
    private String fuelName;
}
