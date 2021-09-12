package cn.ccsit.eeoi.energyeefic.dto;

import cn.ccsit.common.vo.page.PageVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class VoyageListDto extends PageVo {
    private String shipNameOrImoOrRegisterNo;
    private String shipComPanyName;
    private String volageCode;
    private Date startTime;
    private Date endTime;
}
