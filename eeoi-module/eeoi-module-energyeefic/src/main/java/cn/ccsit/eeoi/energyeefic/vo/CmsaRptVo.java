package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class CmsaRptVo {
   private String id;
   private String imono;
   private String shipName;
   private Date startTm;
   private Date endTm;
   private String rptOrg;
   private String verifierName;
   private String countryFlag;
   private String docIacs;
   private String docName;
   private String spCate;
   private String spType;
   private BigDecimal grossTons;
   private BigDecimal netTons;
   private BigDecimal dwTons;
   private Integer teu;
   private BigDecimal eediVal;
   private String iceClass;
   private BigDecimal designSpeed;
   private BigDecimal mePower;
   private BigDecimal gePower;
   private BigDecimal boPower;
   private BigDecimal tonsNm;
   private BigDecimal teuNms;
   private BigDecimal pesNms;
   private BigDecimal distOnsea;
   private BigDecimal hourOnsea;
   private BigDecimal hourOpera;
   private BigDecimal landPowerKwh;
   private String captain;
   private String statPerson;
   private String fillPerson;
   private String contactPh;
   /**
    *
    * 是否是航次报
    */
   private Boolean voyageRpt;
   /**
    * 上一港口
    */
   private String prePort;
   /**
    * 上一港口靠泊时间
    */
   private Date prePortArrTime;
   /**
    * 下一港口
    */
   private String nextPort;
   /**
    * 下一港口靠泊时间
    */
   private Date nextPortArrTime;
   /**
    *是否年报月报
    */
   private Boolean yearRpt;

   /**
    * 是否更换船旗国
    */
   private Boolean isTurnToFlalg;
   /**
    * 是否更换经营人
    */
   private Boolean isTurnToDoc;
   /**
    * 是不适用
    */
   private Boolean notUse;
   /**
    * 建造时间
    */
   private Date builderTime;
   private List<CmsaFuelTypeVo> cmsaFuelTypeVos;
}
