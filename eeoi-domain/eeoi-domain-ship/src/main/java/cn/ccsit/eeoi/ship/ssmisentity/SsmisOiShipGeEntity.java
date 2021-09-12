package cn.ccsit.eeoi.ship.ssmisentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "SSMISHOST.VIEW_SM_PRIME_MOVER")
public class SsmisOiShipGeEntity implements Serializable {
    @Id
    @Column(name = "id")
    private Long  id;
    private String ccsno;
    @Column(name = "TYPE")
    private String gemodel;
    private String manufacturer;
    @Column(name = "RATEDOUTPUT")
    private BigDecimal ratePower;
    private BigDecimal ratedSpeed;
    @Column(name = "DATEMANUFACTURE")
    private String dateManufacturer;
    private String serialno;
    @Column(name = "SETNO")
    private String setno;
    @Column(name = "IS_DELETE")
    private Integer isDelete;
    @Column(name = "TREE_ID")
    private String treeId;
}
