package cn.ccsit.eeoi.system.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.system.entity.SysPcappVersion;
import org.springframework.web.bind.annotation.RequestBody;

public interface SysPcappVersionService extends CommonService{

    ResultVo findSysPcappVersionById(String id);

    ResultVo findAllSysPcappVersionsByPage(int pageSize, int pageNum, String newVersion, String upType);

    ResultVo saveOrUpdateSysPcappVersion(@RequestBody SysPcappVersion sysPcappVersion);

    ResultVo deleteSysPcappVersionById(String id);

    ResultVo findSysFilsListByNoticeId(String id);

    ResultVo findAfterSysPcappsList(String version, String sysCode);

    void downloadFileByFileNumber(String filePath);
}
