package cn.ccsit.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class CrmUserInfo {
    private String fID;
    private String fUserType;
    private String fUserName;
    private String fCompanyName;
    private String fAddress;
    private String fEMail;
    private String fLinkMan;
    private String fCountryName;
    private String fProvince;
    private String fCity;
    private String fPostCode;
    private String fTelePhone;
    private String fFax;
    private String fValidDate;
    private String fState;
    private String fCode;
    private String iacsCode;
    private List<CrmRelaCompanise> relaCompanise;
}
