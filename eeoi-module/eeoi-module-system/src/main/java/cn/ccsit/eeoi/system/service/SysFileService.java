package cn.ccsit.eeoi.system.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.system.entity.SysDocNotice;
import cn.ccsit.eeoi.system.entity.SysFile;
import org.springframework.web.bind.annotation.RequestBody;


public interface SysFileService extends CommonService {

    public ResultVo findAllSysFilesByPage(int pageSize, int pageNum);

    public ResultVo deleteSysFileById(String id);

    public ResultVo saveOrUpdateSysFile(@RequestBody SysFile sysFile);

    public ResultVo findSysFileById(String id);

}
