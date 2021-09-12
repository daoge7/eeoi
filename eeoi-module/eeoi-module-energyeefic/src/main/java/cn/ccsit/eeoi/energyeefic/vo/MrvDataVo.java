package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

@Data
public class MrvDataVo {
    private String imoNo;
    private String firstVoyageCode;
    private String secondVoyageCode;
    private String timestamp;
    private String respcode;
    private String respMsg;

    public MrvDataVo(String imoNo, String firstVoyageCode, String secondVoyageCode, String timestamp) {
        this.imoNo = imoNo;
        this.firstVoyageCode = firstVoyageCode;
        this.secondVoyageCode = secondVoyageCode;
        this.timestamp = timestamp;
    }

    public MrvDataVo(String timestamp, String respcode, String respMsg) {
        this.timestamp = timestamp;
        this.respcode = respcode;
        this.respMsg = respMsg;
    }

    public MrvDataVo() {
    }
}
