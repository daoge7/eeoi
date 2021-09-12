package cn.ccsit.eeoi.ship.vo;

import cn.ccsit.common.vo.page.PageVo;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipGeEntity;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipMainEngineEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SsmisOiShipInfoVo implements Serializable {
    private String id;
    private String imono;
    private String ccsno;
    private String port;
    private String docCode;
    private String docName;
    private String ownerCode;
    private String ownerName;
    private String operatorCode;
    private String operatorName;
    private String builderCode;
    private String builderName;
    private String spname;
    private String cspname;
    private String flag;
    private String shipClass;
    private BigDecimal dw;
    private String sptype;
    private Date finish;
    private BigDecimal gross;
    private BigDecimal net;
    private Integer teus;
    private BigDecimal speed;
    private BigDecimal draught1;
    private BigDecimal breadth;
    private BigDecimal length;
    private BigDecimal lenthLl;
    private BigDecimal eediValue;
    private List<SsmisOiShipMainEngineEntity> ssmisOiShipMainEngineEntities;
    private List<SsmisOiShipGeEntity> ssmisOiShipGeEntities;

}
