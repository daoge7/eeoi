package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.util.List;

@Data
public class CompareAnalysisDataVo {
    /**
     * 船公司名字或者传播名字
     */
    private String name;
    /**
     *
     * 类型 0：船公司1:船舶
     */
    private String type;
    List<EneryEfficTrendDataVo> eneryEfficTrendDataVos;
}
