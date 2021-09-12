package cn.ccsit.eeoi.system.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.system.entity.SysDocNotice;
import cn.ccsit.eeoi.system.repository.SysFileRepository;
import cn.ccsit.eeoi.system.service.SysDocNoticeService;
import cn.ccsit.eeoi.system.utils.FtpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/sysDocNotice")
public class SysDocNoticeController extends CommonController {

    @Autowired
    SysDocNoticeService sysDocNoticeService;

    @Autowired
    FtpUtils ftpUtils;

    @GetMapping("/deleteSysDocNoticeById/{id}")
    public ResultVo GrossTonById(@PathVariable String id) {
        return sysDocNoticeService.deleteSysDocNoticeById(id);
    }

    @PostMapping("/saveOrUpdateSysDocNotice")
    public ResultVo saveOrUpdateSysDocNotice(@RequestBody SysDocNotice sysDocNotice) {
        return sysDocNoticeService.saveOrUpdateSysDocNotice(sysDocNotice);
    }

    @GetMapping("/findSysDocNoticeList")
    public ResultVo findDocGrossTonList(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "docTitle", required = false) String docTitle, @RequestParam(value = "docNumber", required = false) String docNumber, @RequestParam(value = "docType", required = false) String docType, @RequestParam(value = "recStatus", required = false) String recStatus) {
        return sysDocNoticeService.findAllSysDocNoticesByPage(pageSize, pageNum, docTitle, docNumber, docType, recStatus);
    }

    @GetMapping("/findSysDocNoticeById/{id}")
    public ResultVo findDocGrossTonList(@PathVariable String id) {
        return sysDocNoticeService.findSysDocNoticeById(id);
    }

    @GetMapping("/findSysFilsListByNoticeId/{id}")
    public ResultVo findSysFilsListByNoticeId(@PathVariable String id) {
        return sysDocNoticeService.findSysFilsListByNoticeId(id);
    }

    @GetMapping("/findAllSysDocNoticeListAndSysFiles")
    public ResultVo findAllSysDocNoticeListAndSysFiles(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum) {
        return sysDocNoticeService.findAllSysDocNoticeListAndSysFiles(pageSize, pageNum);
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(@RequestParam(value = "path", required = true) String path, @RequestParam(value = "fileName", required = true) String fileName) throws Exception {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        ftpUtils.downloadFile(path, fileName, response);
    }

    @GetMapping("/findPreviousThreeNotices")
    public ResultVo findPreviousThreeNotices() {
        return sysDocNoticeService.findPreviousThreeNotices();
    }

    @GetMapping("/findPreviousThreeSystemNotices")
    public ResultVo findPreviousThreeSystemNotices() {
        return sysDocNoticeService.findPreviousThreeSystemNotices();
    }

    @GetMapping("/getSoftWareDownLoad")
    public ResultVo getSoftWareDownLoad() {
        return sysDocNoticeService.getSoftWareDownLoad();
    }
}