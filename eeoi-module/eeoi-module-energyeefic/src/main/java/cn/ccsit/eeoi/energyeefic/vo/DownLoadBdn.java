package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.util.List;
@Data
public class DownLoadBdn {
    private List<DownLoadBdnVo> downLoadBdnVos;
    private DownLoadBdnVo downLoadBdnVo;
}
