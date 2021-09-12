package cn.ccsit.eeoi.ship.ssmisentity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "SSMISHOST.SM_SHIP_EQUIP_ATTR")
public class SsmisShipEquipAttrEntity implements Serializable {
    @Id
    private String id;
    private BigDecimal attrvalue;
    @Column(name = "CCSNO")
    private String ccsno;
}
