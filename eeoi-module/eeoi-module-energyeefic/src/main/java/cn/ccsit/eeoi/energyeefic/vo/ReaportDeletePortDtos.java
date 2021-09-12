package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReaportDeletePortDtos {
    private String imoNo;
    private List<String> refCodes;
}
