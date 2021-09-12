package cn.ccsit.eeoi.energyeefic.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompareAnalysisDataCondition {
    private String shipCompanyId;
    private String shipId;
    private String shipTypeCode;
    private String shipTypeSub;
    private String grossRange;
    private String shipAgeRange;
}
