package cn.ccsit.eeoi.energyeefic.vo;

import cn.ccsit.eeoi.energyeefic.entity.OiShipTask;
import lombok.Data;

import java.util.List;

@Data
public class CmsaGetTaskVo {
    private List<CmsaTaskVo> cmsaTaskVos;
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
}
