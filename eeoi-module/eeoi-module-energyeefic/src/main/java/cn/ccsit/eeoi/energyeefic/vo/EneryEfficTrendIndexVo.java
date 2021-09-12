package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EneryEfficTrendIndexVo {
    private String nameEn;
    private String name;
    private String type;
    private Integer yAxisIndex;
    private String id;
    private List<EneryEfficTrendIndexDataVo> eneryEfficTrendIndexDataVos;
    private List<BigDecimal> yData;//纵坐标数值
    private List<String> xData;
}
