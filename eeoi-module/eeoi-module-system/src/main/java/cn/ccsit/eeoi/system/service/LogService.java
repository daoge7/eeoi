package cn.ccsit.eeoi.system.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.system.vo.LogVo;


public interface LogService extends CommonService {

    ResultVo findAllLogFiles(String logFilePath,int pageNum, int pageSize);

}
