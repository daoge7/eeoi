package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EuRptVo {
    /**
     * 文件目录
     */
    private String fileDir;
    /**
     * 文件名称
     */
    private String fileName;
    private String id;
    /**
     * 船舶id
     */
    private String shipId;
    private String imono;
    private Date startTm;
    private Date endTm;
    /**
     * 报告来源
     */
    private String rptSource;
    private String shipName;
    private String homePortCode;
    private String homePortName;
    private String countryFlag;
    private String spTypeCode;
    private String spType;
    private String iceClass;
    private BigDecimal eediVal;
    private String ownerName;
    private String ownerRegAddr;
    private String ownerAddr;
    private String docName;
    private String docRegAddr;
    private String docAddr;
    private String mrvpTitle;
    private String mrvpFname;
    private String mrvpSname;
    private String mrvpJobtitle;
    private String mrvpTele;
    private String mrvpEmail;
    private String verifierName;
    private String verifierAddr;
    private String accrNum;
    private String verifierState;
    private String emmSource;
    private String emmMeMethod;
    private String emmMeUncer;
    private String emmGeMethod;
    private String emmGeUncer;
    private String emmBoMethod2;
    private String emmBoUncer2;
    private BigDecimal hfoTons;
    private BigDecimal lfoTons;
    private BigDecimal dgoTons;
    private BigDecimal euCo2Tons;
    private BigDecimal euInnerCo2Tons;
    private BigDecimal euToCo2Tons;
    private BigDecimal euFrCo2Tons;
    private BigDecimal euBerthCo2Tons;
    private String euLadenCons;
    private String euCargoheatCons;
    private BigDecimal distOnsea;
    private BigDecimal distIce;
    private BigDecimal hourOnsea;
    private BigDecimal hourIce;
    private BigDecimal transportWork;
    private BigDecimal fuelConsPerMile;
    private BigDecimal fuelConsPerWork;
    private BigDecimal co2PerMile2;
    private BigDecimal co2PerWork2;
    private String recStatus;
    private String fileId;
    private BigDecimal eivVal;
    /**
     * 重油排放因子
     */
    private BigDecimal hfoFactorr;
    /**
     * 轻油排放因子
     */
    private BigDecimal lfoFactorr;
    /**
     * 柴油排放因子
     */
    private BigDecimal chaiFactorr;

}
