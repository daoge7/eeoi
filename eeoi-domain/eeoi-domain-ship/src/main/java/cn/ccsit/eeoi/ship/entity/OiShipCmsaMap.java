package cn.ccsit.eeoi.ship.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "OI_SHIP_CMSA_MAP")
public class OiShipCmsaMap implements Serializable {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator = "idGenerator")
    private String id;
    @Column(name = "SHIP_ID")
    private String shipId;
    @Column(name = "CLIENT_ID")
    private String clientId;
    @Column(name = "CMSA_BRANCH")
    private String cmsaBranch;
    @Column(name = "CMSA_NAME")
    private String cmsaName;
    private String registerno;
    private String creator;
    @Column(name = "CREATE_TM")
    private Date createTm;
    private String opuser;
    private Date opdate;
    @Column(name = "IS_DELETE")
    private Integer isDelete;
}
