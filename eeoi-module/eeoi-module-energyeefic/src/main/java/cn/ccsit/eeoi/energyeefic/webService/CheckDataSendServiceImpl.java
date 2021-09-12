package cn.ccsit.eeoi.energyeefic.webService;

import cn.ccsit.eeoi.energyeefic.dto.ImportVoyage;

import javax.jws.WebService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@WebService
public class CheckDataSendServiceImpl implements CheckDataSendService {

    @Override
    public List<ImportVoyage> CheckDataList(List<ImportVoyage> list, String T, String S) {
        List<ImportVoyage> importVoyages = new ArrayList<>();
        ImportVoyage importVoyage = new ImportVoyage();
        importVoyage.setMsg("请安装最新版本船端软件");
        importVoyages.add(importVoyage);
        return importVoyages;
    }
}
