package cn.ccsit.eeoi.system.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.system.entity.SysDocNotice;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;


public interface SysDocNoticeService extends CommonService {

    public ResultVo findAllSysDocNoticesByPage(int pageSize, int pageNum, String docTitle, String fileIds,String docType,String recStatus);

    public ResultVo deleteSysDocNoticeById(String id);

    public ResultVo saveOrUpdateSysDocNotice(@RequestBody SysDocNotice sysDocNotice);

    public ResultVo findSysDocNoticeById(String id);

    public ResultVo findSysFilsListByNoticeId(String id);

    public void downloadFile(String ftpPath, String fileName, HttpServletResponse response);

    public ResultVo findPreviousThreeNotices();

    ResultVo findPreviousThreeSystemNotices();

    ResultVo findAllSysDocNoticeListAndSysFiles(int pageSize, int pageNum);

    /**
     * 获取软件
     * @return
     */
    ResultVo getSoftWareDownLoad();
}
