package cn.ccsit.eeoi.ship.query;

import cn.ccsit.common.vo.page.PageVo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class OiShipInfoSsmisCriteria extends PageVo{
    private String docId;
    private String imo;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = trimparam(docId);
    }

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = trimparam(imo);
    }
    private String trimparam(String param){
        String trim = param.trim();
        return trim;
    }
}
