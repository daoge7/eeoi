package cn.ccsit.eeoi.ship.ssmisentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "SSMISHOST.SM_SHIP")
public class SsmisOiShipInfoEntity implements Serializable {
    @Id
    private String id;
    private String imono;
    private String ccsno;
    private String port;
    @Column(name = "DOC_CODE")
    private String docCode;
    @Column(name = "DOC_NAME")
    private String docName;
    @Column(name = "OWNER_CODE")
    private String ownerCode;
    @Column(name = "OWNER_NAME")
    private String ownerName;
    @Column(name = "OPERATOR_CODE")
    private String operatorCode;
    @Column(name = "OPERATOR_NAME")
    private String operatorName;
    @Column(name = "BUILDER_CODE")
    private String builderCode;
    @Column(name = "BUILDER_NAME")
    private String builderName;
    private String spname;
    private String cspname;
    private String flag;
    @Column(name = "CLASS")
    private String shipClass;
    private BigDecimal dw;
    private String sptype;
    private Date finish;
    private BigDecimal gross;
    private BigDecimal net;
    private Integer teus;
    private BigDecimal speed;
    @Column(name = "DRAUGHT1")
    private BigDecimal draught1;
    private BigDecimal breadth;
    private BigDecimal length;
    @Column(name = "LENGTH_LL")
    private BigDecimal lenthLl;
//
// private List<SsmisOiShipMainEngineEntity> ssmisOiShipMainEngineEntities;
// private List<SsmisOiShipGeEntity> ssmisOiShipGeEntities;


}
