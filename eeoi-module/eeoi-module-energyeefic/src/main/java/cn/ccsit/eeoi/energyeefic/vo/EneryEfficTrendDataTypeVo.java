package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EneryEfficTrendDataTypeVo {
    private String type;//是否是航次
    List<EneryEfficTrendDataVo> eneryEfficTrendDataVos;
}
