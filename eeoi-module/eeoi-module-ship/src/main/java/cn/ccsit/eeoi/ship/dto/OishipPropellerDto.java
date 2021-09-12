package cn.ccsit.eeoi.ship.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OishipPropellerDto {
    private String id;
    private String shipId;
    private String shipName;
    private String xh;
    private String ice;
    private String builder;
    private String turnTo;//转向
    private Integer leafNum;
    private BigDecimal pitch;//螺距
    private BigDecimal weight;
    private String material;//材料
    private String certNu;
    private Integer count;
    private String type;
    private Integer diameter;//直径
//    private String datatype;
//    private String dataSource;
//    private String creator;
//    private Time createTm;
//    private String opuser;
//    private Time opdate;
//    private Integer isDelete;
}
