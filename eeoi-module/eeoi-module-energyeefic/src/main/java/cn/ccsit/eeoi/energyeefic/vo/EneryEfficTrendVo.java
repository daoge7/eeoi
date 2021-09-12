package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;
import org.apache.maven.lifecycle.internal.LifecycleStarter;

import java.util.List;

@Data
public class EneryEfficTrendVo {
    private String shipName;
    private String shipComPanyName;
    private List<EneryEfficTrendIndexVo> eneryEfficTrendIndexVos;
}
