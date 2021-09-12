package cn.ccsit.eeoi.system.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.system.entity.SysPcappVersion;
import cn.ccsit.eeoi.system.service.SysPcappVersionService;
import cn.ccsit.eeoi.system.utils.FtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/sysPcappVersion")
public class SysPcappVersionController extends CommonController {

    @Autowired
    SysPcappVersionService sysPcappVersionService;

    @Autowired
    FtpUtils ftpUtils;

    @GetMapping("/deleteSysPcappVersionById/{id}")
    public ResultVo deleteSysPcappVersionById(@PathVariable String id) {
        return sysPcappVersionService.deleteSysPcappVersionById(id);
    }

    @PostMapping("/saveOrUpdateSysPcappVersion")
    public ResultVo saveOrUpdateSysPcappVersion(@RequestBody SysPcappVersion sysPcappVersion) {
        return sysPcappVersionService.saveOrUpdateSysPcappVersion(sysPcappVersion);
    }

    @GetMapping("/findSysPcappVersionList")
    public ResultVo findSysPcappVersionList(@RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "newVersion", required = false) String newVersion, @RequestParam(value = "upType", required = false) String upType) {
        return sysPcappVersionService.findAllSysPcappVersionsByPage(pageSize, pageNum, newVersion, upType);
    }

    @GetMapping("/findSysPcappVersionById/{id}")
    public ResultVo findSysPcappVersionById(@PathVariable String id) {
        return sysPcappVersionService.findSysPcappVersionById(id);
    }

    @GetMapping("/findSysFilsListByNoticeId/{id}")
    public ResultVo findSysFilsListByNoticeId(@PathVariable String id) {
        return sysPcappVersionService.findSysFilsListByNoticeId(id);
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(@RequestParam(value = "path", required = true) String path, @RequestParam(value = "fileName", required = true) String fileName) throws Exception {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        ftpUtils.downloadFile(path, fileName, response);
    }

    @RequestMapping("/downloadFileByNumber")
    public void downloadFileByFileNumber(@RequestParam(value = "fileNumber", required = true) String fileNumber) throws Exception {
        sysPcappVersionService.downloadFileByFileNumber(fileNumber);
    }

    @GetMapping("/findAfterSysPcappsList")
    public ResultVo findSysFilsListByNoticeId(@RequestParam(value = "currentVersion", required = true) String currentVersion, @RequestParam(value = "sysCode", required = true) String sysCode) {
        return sysPcappVersionService.findAfterSysPcappsList(currentVersion, sysCode);
    }
}