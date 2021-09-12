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
@Table(name = "ssmishost.view_sm_main_diesel_engine")
public class SsmisOiShipMainEngineEntity implements Serializable {
    @Id
    private String ccsno;
    @Column(name = "ENGINETYPE")
    private String enginetype;
    private String manufacturer;
    @Column(name = "RATEDOUTPUT")
    private BigDecimal ratePower;
    private BigDecimal ratedSpeed;
    @Column(name = "DATEMANUFACTURE")
    private String dateManufacturer;
    private String serialno;
    private String position;
}
