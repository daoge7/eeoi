package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.util.List;
@Data
public class DownLoadDataVo {
    private List<MrvDataAssessmentVo> mrvDataAssessmentVos;
    private List<SegmentMonitoringVo> segmentMonitoringVos;
    private String imoRptId;
    private String type;
}
