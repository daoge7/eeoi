package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LibyaDownLoadVo {
    /**
     * libya下载类型 0：下载选中 1：下载时间段内的全部
     */
    private String downLoadType;
    private Date startTime;
    private Date endTime;
    private List<String> imoRptIds;
}
