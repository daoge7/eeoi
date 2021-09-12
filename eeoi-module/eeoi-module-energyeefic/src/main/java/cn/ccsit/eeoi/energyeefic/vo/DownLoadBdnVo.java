package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DownLoadBdnVo {
    /**
     * 柴油
     */
    private BigDecimal chaiFo;
    /**
     * 重燃油
     */
    private BigDecimal hfo;
    /**
     * 轻燃油
     */
    private BigDecimal lfo;
    /**
     * 丙烷
     */
    private BigDecimal propane;
    /**
     * 丁烷
     */
    private BigDecimal butane;
    /**
     * 液化天然气
     */
    private BigDecimal lng;

    /**
     * 甲醇
     */
    private BigDecimal other;
    /**
     * 加油时间
     */
    private String operatorTime;
    private Date operatorTimedate;

    public DownLoadBdnVo(BigDecimal chaiFo, BigDecimal hfo, BigDecimal lfo, BigDecimal propane, BigDecimal butane, BigDecimal lng, BigDecimal other) {
        this.chaiFo = chaiFo;
        this.hfo = hfo;
        this.lfo = lfo;
        this.propane = propane;
        this.butane = butane;
        this.lng = lng;
        this.other = other;
    }

    public DownLoadBdnVo() {
    }
}
