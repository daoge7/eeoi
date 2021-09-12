package cn.ccsit.eeoi.energyeefic.dto;

import cn.ccsit.common.vo.page.PageVo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class VoyagePortListDto extends PageVo {
    private String shipNameOrImoOrRegisterNo;
    private String volageCode;
    private Date startTime;
    private Date endTime;
}
